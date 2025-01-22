package io.ISSProject.game.controller.mediator;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.ISSProject.game.ClueNotification;
import io.ISSProject.game.controller.GameplayController;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.menuState.GameContext;
import io.ISSProject.game.controller.menuState.MainMenuState;
import io.ISSProject.game.model.GameplayState.BrotherLivingRoomState;
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
                String clueTitle = (String) data[0];
                Stage currentStage = gameplayController.getScreen().getStage();
                Skin skin = gameplayController.getScreen().getSkin();
                new ClueNotification(clueTitle, skin, currentStage);
                break;


        }
    }
}
