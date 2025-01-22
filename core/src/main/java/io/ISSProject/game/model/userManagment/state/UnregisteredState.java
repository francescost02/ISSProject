package io.ISSProject.game.model.userManagment.state;

import com.badlogic.gdx.Screen;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.model.userManagment.UserState;
import io.ISSProject.game.view.UI.UnregisteredUI;

public class UnregisteredState implements UserState {
    private UserManager userManager;

    public UnregisteredState(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void handleInput(String input){
    }

    @Override
    public Screen getUIScreen(ScreenController controller){
        return new UnregisteredUI(controller);
    }

}

