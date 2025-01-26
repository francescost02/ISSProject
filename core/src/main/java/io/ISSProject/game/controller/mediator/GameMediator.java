package io.ISSProject.game.controller.mediator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ISSProject.game.ClueNotification;
import io.ISSProject.game.controller.GameplayController;
import io.ISSProject.game.controller.PauseMenuController;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.gameState.*;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.view.UI.UnregisteredUI;

public class GameMediator {
    private MainMenuController2 menuController;
    private UserManager userManager;
    private GameContext gameContext;
    private ScreenController screenController;
    private Game game;
    private GameplayController gameplayController;

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


            case "RETURN_TO_MAIN_MENU":
                if (menuController != null) {
                    gameContext.changeState(new MainMenuState(gameContext));
                    game.setScreen(menuController.getScreen());
                }
                break;

            case "OPEN_PAUSE_MENU":
                gameContext.saveCurrentState();
                PauseMenuController pauseMenuController = new PauseMenuController(gameContext);
                pauseMenuController.setMediator(this);
                gameContext.changeState(new PauseMenuState(gameContext));
                game.setScreen(pauseMenuController.getScreen());
                break;

            case "SHOW_SETTINGS_MENU":
                SettingsController settingsController = new SettingsController(gameContext);
                settingsController.setMediator(this);
                game.setScreen(settingsController.getScreen());
                break;

            case "SHOW_EXIT_MENU":
                ExitMenuController2 exitMenuController = new ExitMenuController2(gameContext);
                exitMenuController.setMediator(this);
                game.setScreen(exitMenuController.getScreen());
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
                    return; // Esce senza fare nulla
                }

                // Segna l'indizio come trovato
                clue.setFound(true);

                // Mostra la notifica del nuovo indizio
                Stage currentStage = gameplayController.getScreen().getStage();
                Skin skin = gameplayController.getScreen().getSkin();
                new ClueNotification(clueTitle, skin, currentStage);

                // Aggiungi l'indizio alla scena solo se non è già presente
                if (!currentScene.getInteractiveObjects().contains(clue)) {
                    currentScene.addInteractiveObject(clue);
                }
                break;

            case "GO_TO_NEXT_SCENE":
                Scene nextScene = gameContext.getCurrentScene().getNextScene();
                if (nextScene != null) {
                    gameContext.changeState(new GameplayState(gameContext, nextScene));
                    game.setScreen(gameplayController.getScreen());
                } else {
                    System.out.println("Non ci sono più scene.");
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
        }
    }
}
