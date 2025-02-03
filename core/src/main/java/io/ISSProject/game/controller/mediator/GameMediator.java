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
import io.ISSProject.game.controller.gameState.PauseMenuState;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.MainMenuState;
import io.ISSProject.game.controller.gameState.BrotherLivingRoomState;
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
        this.gameContext = GameContext.getInstance();
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
        switch(event) {
            case "SHOW_UNREGISTERED_SCREEN":
                System.out.println("Mostra schermata Unregistered");
                game.setScreen(new UnregisteredUI(screenController));
                break;
            case "USER_LOGIN_REQUESTED":
                userManager.selectLoginPath();
                break;
            case"LOGIN_ERROR":
                screenController.showErrorNotification((String)data[0]);
                break;
            case "USER_REGISTRATION_REQUESTED":
                userManager.selectRegistrationPath();
                break;
            case "USER_LOGGED_IN":
                gameContext.changeState(new MainMenuState(gameContext));
                game.setScreen(menuController.getScreen());
                break;
            case "RETURN_TO_UNREGISTERED":
                userManager.returnToUnregistered();
                break;
            case "START_NEW_GAME":
                gameContext.changeState(new BrotherLivingRoomState(gameContext));
                game.setScreen(gameplayController.getScreen());
                break;
            case "RETURN_TO_MAIN_MENU":
                if(menuController != null){
                    gameContext.changeState(new MainMenuState(gameContext));
                    game.setScreen(menuController.getScreen());
                }
                break;
            case "OPEN_PAUSE_MENU":
                System.out.println("Apertura menu di pausa");
                // Salva la scena attuale nel contesto
                gameContext.saveCurrentScene();
                gameContext.changeState(new PauseMenuState());
                PauseMenuController pauseMenuController = new PauseMenuController(gameContext);
                pauseMenuController.setMediator(this);
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
                gameContext.changeState(new PauseMenuState());
                pauseMenuController.setMediator(this);
                game.setScreen(pauseMenuController.getScreen());
                Gdx.input.setInputProcessor(pauseMenuController.getScreen().getStage());
                break;
            case "CLUE_FOUND":
                handleClueFound((Clue) data[0]);
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

    private void handleClueFound(Clue receivedClue){
        String clueTitle = receivedClue.getTooltipText();
        Scene currentScene = gameContext.getCurrentScene();

        // Trova l'istanza esatta del Clue nella scena
        Clue clue = currentScene.getInteractiveObjects().stream()
            .filter(obj -> obj instanceof Clue && ((Clue) obj).getTooltipText().equals(clueTitle))
            .map(obj -> (Clue) obj)
            .findFirst()
            .orElse(null);

        if (clue == null) {
            clue = receivedClue; // Usa comunque il Clue ricevuto per evitare crash
            currentScene.addInteractiveObject(clue);
        }

        boolean alreadyFound = clue.isFound();
        if(!alreadyFound) {
            // Mostra la notifica del nuovo indizio
            Stage currentStage = gameplayController.getScreen().getStage();
            Skin skin = gameplayController.getScreen().getSkin();

            // Segna l'indizio come trovato
            clue.setFound(true);
            new ClueNotification(clueTitle, skin, currentStage);
        } else {
            // Mostra la notifica dell'indizio già trovato
            Stage currentStage = gameplayController.getScreen().getStage();
            Skin skin = gameplayController.getScreen().getSkin();

            new ClueNotification(clueTitle, skin, currentStage, alreadyFound);
        }

        // Aggiungi l'indizio alla scena solo se non è già presente
        if (!currentScene.getInteractiveObjects().contains(clue)) {
            currentScene.addInteractiveObject(clue);
        }
    }
}
