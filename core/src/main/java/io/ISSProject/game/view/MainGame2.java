/*package io.ISSProject.game.view;

import com.badlogic.gdx.Game;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.gameState.GameContext;
=======
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.menuState.GameContext;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
>>>>>>> branch-di-giacomo

public class MainGame2 extends Game {
    private SpriteBatch batch;
    private MainMenuController2 mainMenuController;
    private GameContext gameContext;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.gameContext = new GameContext(); // Inizializzato una sola volta

        this.mainMenuController = new MainMenuController2(gameContext);
        this.setScreen(this.mainMenuController.getScreen()); //imposta la schermata
    }

    @Override
    public void render() {
        super.render();  // Renderizza la schermata corrente
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

 */


