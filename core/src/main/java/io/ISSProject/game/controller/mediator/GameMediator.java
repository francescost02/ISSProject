package io.ISSProject.game.controller.mediator;

import com.badlogic.gdx.Game;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.menuState.GameContext;
import io.ISSProject.game.controller.menuState.MainMenuState;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.view.UI.UnregisteredUI;

public class GameMediator {
    private MainMenuController2 menuController;
    private UserManager userManager;
    private GameContext gameContext;
    private ScreenController screenController;
    private Game game;

    public GameMediator(Game game) {
        this.game = game;
    }

    public void registerComponents(
        MainMenuController2 menuController,
        UserManager userManager,
        GameContext gameContext,
        ScreenController screenController) {
        this.menuController = menuController;
        this.userManager = userManager;
        this.gameContext = gameContext;
        this.screenController = screenController;
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
            case "MENU_STATE_CHANGED":
                screenController.update(userManager);
                break;
        }
    }
}
