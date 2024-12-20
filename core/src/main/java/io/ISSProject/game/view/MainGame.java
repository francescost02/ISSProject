
package io.ISSProject.game.view;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.g2d.*;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.menuState.GameContext;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.settingsMenuModel.AssetManager;

// MAIN APPLICATION
public class MainGame extends Game {
    private SpriteBatch batch;
    private MainMenuController2 mainMenuController;
    private GameContext gameContext;
    private SettingsController settingsController;

    @Override
    public void create() {
        batch = new SpriteBatch();

        this.gameContext = new GameContext(); // Inizializzato una sola volta
        this.mainMenuController = new MainMenuController2(gameContext);
        this.setScreen(this.mainMenuController.getScreen()); //imposta la schermata


/*
        // Inizializza il controller
        settingsController = new SettingsController();

        // Imposta lo schermo tramite il controller
        setScreen(settingsController.getScreen());

 */
    }


/*
    public void setScreen(Screen screen) {
        screen.show(); // Imposta lo schermo attivo
    }
    */

    @Override
    public void render() {
        settingsController.getScreen().render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        settingsController.getScreen().resize(width, height);
    }

    @Override
    public void dispose() {
        settingsController.getScreen().dispose();
        batch.dispose();
        AssetManager.getInstance().dispose();
    }
}



