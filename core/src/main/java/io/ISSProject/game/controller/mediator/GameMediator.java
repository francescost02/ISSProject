package io.ISSProject.game.controller.mediator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ISSProject.game.ClueNotification;
import io.ISSProject.game.controller.Puzzles.PuzzleController;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.gamePlayController.PauseMenuController;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.gameState.*;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.saveMenu.SaveController;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Puzzles.PuzzleStrategy;
import io.ISSProject.game.model.Puzzles.ReverseTextPuzzle;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;
import io.ISSProject.game.view.Puzzles.TextPuzzleView;
import io.ISSProject.game.view.UI.UnregisteredUI;

import java.util.List;

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
                Scene initialScene = gameContext.getCurrentScene();
                if (initialScene == null) {
                    initialScene = new Scene("Brother's Living Room", 1);
                    gameContext.setCurrentScene(initialScene);
                }
                gameContext.changeState(new GameplayState(gameContext, initialScene));
                game.setScreen(gameplayController.getScreen());
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

                // Mostra la notifica del nuovo indizio
                Stage currentStage = gameplayController.getScreen().getStage();
                Skin skin = gameplayController.getScreen().getSkin();
                new ClueNotification(clueTitle, skin, currentStage);
                /*

                // Aggiungi l'indizio alla scena solo se non è già presente
                if (!currentScene.getInteractiveObjects().contains(clue)) {
                    currentScene.addInteractiveObject(clue);
                }

                 */
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

            case "SHOW_PUZZLE":
                PuzzleStrategy puzzle = (PuzzleStrategy) data[0];
                PuzzleObject puzzleObj = (PuzzleObject) sender;

                puzzleObj.setMediator(this);

                PuzzleController puzzleController = new PuzzleController(puzzle, this, puzzleObj);
                Stage puzzleStage = gameplayController.getScreen().getStage();
                Skin puzzleSkin = gameplayController.getScreen().getSkin();

                AbstractPuzzleView puzzleView = null;
                if (puzzle instanceof ReverseTextPuzzle) {
                    puzzleView = new TextPuzzleView("Enigma testuale", puzzleSkin, puzzleController);
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

        }
    }
}
