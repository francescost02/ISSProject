package io.ISSProject.game.model.userManagment.state;

import com.badlogic.gdx.Screen;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.model.userManagment.User;
import io.ISSProject.game.model.userManagment.UserState;
import io.ISSProject.game.view.UI.LoggedInUI;

public class LoggedInState implements UserState {
    private final User user;

    public LoggedInState(User user){
        this.user = user;
    }

    @Override
    public void handleInput(String input){

    }

    @Override
    public Screen getUIScreen(ScreenController controller){
        return new LoggedInUI(controller, user);
    }

}
