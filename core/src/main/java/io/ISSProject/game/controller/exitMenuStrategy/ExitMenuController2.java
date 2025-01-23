package io.ISSProject.game.controller.exitMenuStrategy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.gameState.ExitMenuState;
import io.ISSProject.game.controller.GameContext;
import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.view.ExitMenuView2;


public class ExitMenuController2 {

    private ExitMenuView2 exitMenuView;
    private final GameContext gameContext;
    private ExitMenuState exitMenuState;
    private GameState currentState;

    public ExitMenuController2(GameContext gameContext) {
        this.gameContext = gameContext;
        this.exitMenuView = new ExitMenuView2();
        addListeners();

        //this.currentState = this.exitMenuState.getGameContext().getCurrentState();
        //this.exitMenuState = new ExitMenuState(currentState,this.exitMenuState.getGameContext());
        this.currentState = gameContext.getCurrentState(); //recupera lo stato corrente
        this.exitMenuState = new ExitMenuState(currentState, gameContext);
    }

    public void addListeners() {
        // Aggiungi i listener per ogni pulsante
        exitMenuView.getSaveAndCloseButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitMenuState.exit();
                SaveAndCloseStrategy strategy = new SaveAndCloseStrategy();
                strategy.execute();
            }
        });

        exitMenuView.getCloseWithoutSavingButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitMenuState.exit();
                CloseWithoutSavingStrategy strategy = new CloseWithoutSavingStrategy();
                strategy.execute();
            }
        });

        exitMenuView.getCancelButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitMenuState.exit();
                CancelStrategy strategy = new CancelStrategy(exitMenuState);
                strategy.execute();

                // Torna alla schermata principale
                Game game = (Game) Gdx.app.getApplicationListener();
                MainMenuController2 mainMenuController = new MainMenuController2(gameContext);
                game.setScreen(mainMenuController.getScreen());
            }
        });
    }
    public ExitMenuView2 getScreen() {
        return exitMenuView;
    }
}
