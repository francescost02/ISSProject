package io.ISSProject.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.view.UI.UnregisteredUI;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    private UserManager userManager;
    private ScreenController controller;

    @Override
    public void create() {
        userManager = new UserManager();
        controller = new ScreenController(this);
        setScreen(new UnregisteredUI(controller));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        if(getScreen() != null){
            getScreen().dispose();
        }
    }
}
