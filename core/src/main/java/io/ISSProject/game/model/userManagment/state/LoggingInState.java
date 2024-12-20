package io.ISSProject.game.model.userManagment.state;

import com.badlogic.gdx.Screen;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.model.userManagment.UserState;
import io.ISSProject.game.view.UI.LoggingInUI;

public class LoggingInState implements UserState {
    private UserManager userManager;
    private ScreenController controller;

    public LoggingInState(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public Screen getUIScreen(ScreenController controller){
        this.controller = controller;
        return new LoggingInUI(controller);
    }

    @Override
    public void handleInput(String username){
        //Delega la logica di business a UserManager e cambiamo stato solo se non restituisce null
        if(!userManager.loginUser(username)){
            System.out.println("Login fallito");
        }
    }
}
