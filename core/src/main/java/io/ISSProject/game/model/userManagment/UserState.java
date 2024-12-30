package io.ISSProject.game.model.userManagment;

import com.badlogic.gdx.Screen;
import io.ISSProject.game.controller.ScreenController;

public interface UserState {
    Screen getUIScreen(ScreenController controller);
    void handleInput(String input);
}
