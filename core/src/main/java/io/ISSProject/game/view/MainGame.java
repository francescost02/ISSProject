package io.ISSProject.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.ISSProject.game.controller.GameInitializer;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.userManagment.UserManager;
import io.ISSProject.game.view.GameplayView.AbstractSceneView;
import io.ISSProject.game.view.GameplayView.BrotherLivingRoomView;
import io.ISSProject.game.view.GameplayView.IntroView;
import io.ISSProject.game.view.GameplayView.StoreView;
import io.ISSProject.game.view.UI.LoggingInUI;
import com.badlogic.gdx.scenes.scene2d.Stage;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    private SpriteBatch batch;
    private GameMediator mediator;
    private MainMenuController2 mainMenuController;
    private GameContext gameContext;
    private UserManager userManager;
    private ScreenController screenController;
    private GameplayController gameplayController;
    private Scene scene;

    @Override
    public void create() {
        batch = new SpriteBatch();
        GameContext.resetInstance();
        gameContext = GameContext.getInstance();
        userManager = UserManager.getInstance();
        mainMenuController = new MainMenuController2();
        screenController = new ScreenController(this);

        // Recupera la scena iniziale dal GameContext o creane una di default
        Scene initialScene = gameContext.getCurrentScene();
        if (initialScene == null) {
            initialScene = new Scene("Intro", 0);
            gameContext.setCurrentScene(initialScene);
        }
        // Crea un controller senza una view
        gameplayController = new GameplayController();

        // Crea la view con il controller ora esistente
        AbstractSceneView initialView = new IntroView(gameplayController);

        // Imposta la view nel controllore
        gameplayController.setScreen(initialView);

        //configurazione del mediatore
        mediator = new GameMediator(this);
        userManager.setMediator(mediator);

        mediator.registerComponents(
            mainMenuController,
            userManager,
            gameContext,
            screenController,
            gameplayController);

        mainMenuController.setMediator(mediator);
        gameplayController.setMediator(mediator);

        mediator.notify(null, "SHOW_UNREGISTERED_SCREEN");
    }

    public Stage getCurrentStage(){
        if (getScreen() instanceof LoggingInUI){
            return ((LoggingInUI) getScreen()).getStage();
        }
        return null;
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
