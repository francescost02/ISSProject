package io.ISSProject.game.controller.mediator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ISSProject.game.ClueNotification;
import io.ISSProject.game.controller.GameplayController;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.GameContext;
import io.ISSProject.game.controller.gameState.MainMenuState;
import io.ISSProject.game.controller.gameState.BrotherLivingRoomState;
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
                    //Gdx.input.setInputProcessor(menuController.getScreen().getStage());
                }
                break;
            case "CLUE_FOUND":
                Clue clue = (Clue) data[0]; // Assumiamo che data[0] sia un oggetto di tipo Clue
                String clueTitle = clue.getTooltipText(); // Ottieni il titolo dell'indizio tramite un metodo di Clue
                Scene currentScene = gameContext.getCurrentScene(); // Ottieni la scena corrente

                // Aggiorna lo stato dell'indizio nella scena
                currentScene.updateClueStatus(clueTitle, true);  // Segna l'indizio come trovato

                // Aggiungi l'indizio alla lista degli oggetti interattivi se non è già presente
                if (!currentScene.getInteractiveObjects().contains(clue)) {
                    currentScene.addInteractiveObject(clue);  // Aggiungi l'indizio alla lista se non già presente
                }
                // Crea la notifica per l'utente
                Stage currentStage = gameplayController.getScreen().getStage();
                Skin skin = gameplayController.getScreen().getSkin();
                new ClueNotification(clueTitle, skin, currentStage);
                break;

        }
    }
}
