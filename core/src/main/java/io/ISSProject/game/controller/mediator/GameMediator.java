package io.ISSProject.game.controller.mediator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ISSProject.game.view.ClueNotification;
import io.ISSProject.game.controller.GameInitializer;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.gamePlayController.PauseMenuController;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.gameState.*;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.puzzles.BasePuzzleController;
import io.ISSProject.game.controller.puzzles.SequenceButtonPuzzleController;
import io.ISSProject.game.controller.puzzles.TextPuzzleController;
import io.ISSProject.game.controller.saveMenu.SaveController;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.CluePaper;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.puzzles.PuzzleStrategy;
import io.ISSProject.game.model.puzzles.ReverseTextPuzzle;
import io.ISSProject.game.model.puzzles.SequenceButtonPuzzle;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.view.UI.UnregisteredUI;
import io.ISSProject.game.view.puzzles.AbstractPuzzleView;
import io.ISSProject.game.view.puzzles.SequencePuzzleView;
import io.ISSProject.game.view.puzzles.TextPuzzleView;

import java.util.List;


import static io.ISSProject.game.view.ClueNotification.showIncompleteSceneNotification;

public class GameMediator {
    private MainMenuController2 menuController;
    private UserManager userManager;
    private GameContext gameContext;
    private ScreenController screenController;
    private Game game;
    private GameplayController gameplayController;
    private List<Clue> clues;

    public GameMediator(Game game) {
        this.game = game;
    }

    public void registerComponents(
        MainMenuController2 menuController,
        UserManager userManager,
        GameContext gameContext,
        ScreenController screenController,
        GameplayController gameplayController) {
        this.menuController = menuController;
        this.userManager = userManager;
        this.gameContext = gameContext;
        this.screenController = screenController;
        this.gameplayController = gameplayController;
    }
    public void registerComponents2(
        MainMenuController2 mainMenuController2,
        GameplayController gameplayController) {
        this.gameContext = GameContext.getInstance();
        this.menuController = mainMenuController2;
        this.gameplayController = gameplayController;
    }

    public void notify(GameComponent sender, String event, Object... data) {
        switch (event) {
            case "SHOW_UNREGISTERED_SCREEN":
                game.setScreen(new UnregisteredUI(screenController));
                break;

            case "USER_LOGIN_REQUESTED":
                userManager.selectLoginPath();
                break;

            case "LOGIN_ERROR":
                screenController.showErrorNotification((String) data[0]);
                break;

            case "USER_REGISTRATION_REQUESTED":
                userManager.selectRegistrationPath();
                break;

            case "USER_LOGGED_IN":
                gameContext.changeState(new MainMenuState(gameContext));
                game.setScreen(menuController.getScreen());
                break;

            case "START_NEW_GAME":
                gameContext.fullReset();//

                //GameplayController.resetInstance();//
                //gameplayController = GameplayController.getInstance();//
                GameInitializer.initializeGame(gameContext);//

                Scene initialScene = gameContext.getCurrentScene();
                if (initialScene == null) {
                    initialScene = new Scene("Intro", 0);
                    //GameInitializer.initializeGame(gameContext);//
                    gameContext.setCurrentScene(initialScene);
                }

                gameplayController.updateViewForScene(initialScene);//

                gameContext.changeState(new GameplayState(gameContext, initialScene));
                game.setScreen(gameplayController.getScreen());
                Gdx.input.setInputProcessor(gameplayController.getScreen().getStage());//

                break;

            case "LOAD_GAME":
                String username = gameContext.getUsername();
                if (username == null || username.isEmpty()) {
                    System.out.println("Errore: nessun utente loggato.");
                    return;
                }
                SaveController saveController = new SaveController();
                saveController.setMediator(this);
                game.setScreen(saveController.getScreen());
                break;
            case "RESTART_GAME":
                gameContext.fullReset();

                Scene initialScene2 = gameContext.loadSceneByName("Intro");
                gameContext.setCurrentScene(initialScene2);
                String previousUser = gameContext.getUsername();
                GameContext.resetInstance(); // Distrugge l'istanza singleton
                GameplayController.resetInstance();

                gameplayController = GameplayController.getInstance(); // Usa getInstance() invece di new
                gameplayController.setMediator(this);

                gameContext = GameContext.getInstance(); // Crea nuovo contesto
                gameContext.setUsername(previousUser);
                //gameplayController = new GameplayController();
                menuController = new MainMenuController2();
                //gameplayController.setMediator(this);
                menuController.setMediator(this);
                //GameInitializer.initializeGame(gameContext);
                registerComponents(menuController, userManager, gameContext, screenController, gameplayController);
                gameContext.changeState(new MainMenuState(gameContext));
                game.setScreen(menuController.getScreen());
                //Gdx.input.setInputProcessor(initialScene.getScreen().getStage());

                break;

            case "SHOW_SETTINGS_MENU":
                SettingsController settingsController = new SettingsController();
                settingsController.setMediator(this);
                game.setScreen(settingsController.getScreen());
                break;

            case "SHOW_EXIT_MENU":
                ExitMenuController2 exitMenuController = new ExitMenuController2();
                exitMenuController.setMediator(this);
                game.setScreen(exitMenuController.getScreen());
                break;

            case "RETURN_TO_MAIN_MENU":
                if (menuController != null) {
                    gameContext.changeState(new MainMenuState(gameContext));
                    game.setScreen(menuController.getScreen());
                }
                break;

            case "OPEN_PAUSE_MENU":
                gameContext.saveCurrentScene();
                PauseMenuController pauseMenuController = new PauseMenuController(gameContext);
                pauseMenuController.setMediator(this);
                gameContext.changeState(new PauseMenuState(gameContext));
                game.setScreen(pauseMenuController.getScreen());
                break;

            case "RETURN_TO_PAUSE_MENU":
                pauseMenuController = new PauseMenuController(gameContext);
                gameContext.changeState(new PauseMenuState(gameContext));
                pauseMenuController.setMediator(this);
                game.setScreen(pauseMenuController.getScreen());
                Gdx.input.setInputProcessor(pauseMenuController.getScreen().getStage());
                break;

            case "CLUE_FOUND":
                Clue clue = (Clue) data[0];
                String clueTitle = clue.getTooltipText();
                Scene currentScene = gameContext.getCurrentScene();
                String content = clue.getContent();

                // Controlla se l'indizio è già stato trovato
                if (currentScene.isClueAlreadyFound(clueTitle)) {
                    System.out.println("Hai già trovato questo indizio.");

                    // Mostra la notifica dell'indizio già trovato
                    Stage currentStage = gameplayController.getScreen().getStage();
                    Skin skin = gameplayController.getScreen().getSkin();
                    new ClueNotification(clueTitle, skin, currentStage, false);
                    return; // Esce senza fare nulla
                }
                // Segna l'indizio come trovato
                clue.setFound(true);
                if (clue instanceof CluePaper) {
                    // Mostra la notifica del nuovo indizio
                    Stage currentStage = gameplayController.getScreen().getStage();
                    Skin skin = gameplayController.getScreen().getSkin();
                    new ClueNotification(content, skin, currentStage);
                }

                // Aggiungi l'indizio alla scena solo se non è già presente
                if (!currentScene.getInteractiveObjects().contains(clue)) {
                    currentScene.addInteractiveObject(clue);
                }
                break;

            case "GO_TO_NEXT_SCENE":
                System.out.println("Scena attuale: " + gameContext.getCurrentScene().getName());
                Scene nextScene = gameContext.getCurrentScene().getNextScene();
                if (nextScene != null) {
                    System.out.println("Passando alla prossima scena: " + nextScene.getName());
                    // Cambia lo stato nel GameContext per la nuova scena
                    gameContext.setCurrentScene(nextScene);
                    gameContext.changeState(new GameplayState(gameContext, nextScene));

                    // Cambia anche la view, utilizzando il metodo del controller
                    gameplayController.updateViewForScene(nextScene);

                    // Imposta la schermata corrente
                    game.setScreen(gameplayController.getScreen());
                } else {
                    System.out.println("Non ci sono altre scene.");
                }
                break;

            case "RETURN_TO_GAME":
                // Ripristina la scena salvata se necessario
                if (gameContext.getCurrentScene() == null) {
                    gameContext.restorePreviousState(); // Assicurati di ripristinare la scena salvata
                }
                // Ripristina la schermata di gioco, mantenendo gli indizi trovati
                game.setScreen(gameplayController.getScreen());
                Gdx.input.setInputProcessor(gameplayController.getScreen().getStage());
                break;
            case "SCENE_NOT_COMPLETED":
                Stage stage = gameplayController.getScreen().getStage();
                Skin skin = gameplayController.getScreen().getSkin();
                showIncompleteSceneNotification(skin, stage);
                break;
            case "SHOW_PUZZLE":
                PuzzleStrategy puzzle = (PuzzleStrategy) data[0];
                PuzzleObject puzzleObj = (PuzzleObject) sender;
                puzzleObj.setMediator(this);

                AbstractPuzzleView puzzleView = null;
                BasePuzzleController puzzleController = null;
                Stage puzzleStage = gameplayController.getScreen().getStage();
                Skin puzzleSkin = gameplayController.getScreen().getSkin();
                if (puzzle instanceof ReverseTextPuzzle textPuzzle) {
                    puzzleController = new TextPuzzleController(textPuzzle, this, puzzleObj);
                    puzzleView = new TextPuzzleView("Enigma testuale", puzzleSkin, puzzleController);
                } else if (puzzle instanceof SequenceButtonPuzzle sequencePuzzle) {
                    puzzleController = new SequenceButtonPuzzleController(sequencePuzzle, this, puzzleObj);
                    puzzleView = new SequencePuzzleView("Enigma sequenza", puzzleSkin, puzzleController);

                }
                if (puzzleView == null){
                    throw new IllegalArgumentException("Tipo puzzle non supportato"+puzzle.getClass().getSimpleName());
                }
                puzzleView.show(puzzleStage);
                break;

            case "PUZZLE_SOLVED":
                PuzzleObject solvedPuzzle = (PuzzleObject) data[0];
                /*
                // Tratta il puzzle come un indizio trovato
                Clue newClue = new Clue(solvedPuzzle.getTooltipText(), solvedPuzzle.getDialogText(),
                    solvedPuzzle.getX(), solvedPuzzle.getY(),
                    solvedPuzzle.getWidth(), puzsolvedPuzzlezleObj.getHeight());
                newClue.setFound(true);

                 */
                //gameContext.getCurrentScene().addInteractiveObject(newClue);
                gameplayController.checkSceneCompletion();
                break;
            case "FINAL_CHOICE":
                boolean choiceCorrect = (boolean) data[0];
                if(choiceCorrect){
                    gameplayController.updateViewForScene(new Scene("Victory View", 0));
                    game.setScreen(gameplayController.getScreen());
                    Gdx.input.setInputProcessor(gameplayController.getScreen().getStage());
                } else {
                    gameplayController.updateViewForScene(new Scene("Defeat View", 0));
                    game.setScreen(gameplayController.getScreen());
                    Gdx.input.setInputProcessor(gameplayController.getScreen().getStage());
                }
                break;
                /*
                stage = gameplayController.getScreen().getStage();
                skin = gameplayController.getScreen().getSkin();
                showChoice(skin, stage, game, gameplayController);

                 */

        }
    }
}
