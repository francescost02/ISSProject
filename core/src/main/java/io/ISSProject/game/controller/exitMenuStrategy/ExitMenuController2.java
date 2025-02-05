package io.ISSProject.game.controller.exitMenuStrategy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.gameState.*;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.saveMenu.SaveController;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.view.ExitMenuView2;

import java.util.List;


public class ExitMenuController2 implements GameComponent {

    private ExitMenuView2 exitMenuView;
    private final GameContext gameContext;
    private ExitMenuState exitMenuState;
    private GameState currentState;
    private GameMediator mediator;
    private SaveGameManager saveGameManager;
    private List<Clue> clues;
   // private final SaveController saveController;

    public ExitMenuController2() {
        this.gameContext = GameContext.getInstance();
        this.exitMenuView = new ExitMenuView2();
        addListeners();

        this.currentState = gameContext.getCurrentState(); //recupera lo stato corrente
        this.exitMenuState = new ExitMenuState(currentState, gameContext);
        this.saveGameManager = new SaveGameManager();
    }

    public void addListeners() {
        // Aggiungi i listener per ogni pulsante
        exitMenuView.getSaveAndCloseButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitMenuState.exit();
                SaveAndCloseStrategy strategy = new SaveAndCloseStrategy(saveGameManager);
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

                if (mediator!=null) {
                    // Verifica lo stato precedente
                    GameState previousState = exitMenuState.getPreviousState();

                    // Se lo stato precedente è il menu principale
                    if (previousState instanceof MainMenuState) {
                        mediator.notify(ExitMenuController2.this,  "RETURN_TO_MAIN_MENU");
                    }
                    // Se lo stato precedente è il menu di pausa
                    else if (previousState instanceof PauseMenuState) {
                        mediator.notify(ExitMenuController2.this,"RETURN_TO_PAUSE_MENU");
                    }
                } else {
                    // Gestione di altri eventi
                    System.out.println("Evento non gestito: " + event);
                }

                /*
                // Torna alla schermata principale
                Game game = (Game) Gdx.app.getApplicationListener();
                MainMenuController2 mainMenuController = new MainMenuController2(gameContext);
                game.setScreen(mainMenuController.getScreen());
                */
            }
        });
    }
    public ExitMenuView2 getScreen() {
        return exitMenuView;
    }

    public void setMediator(GameMediator mediator){
        this.mediator = mediator;
    }

    @Override
    public void notify(String event, Object...data){
        if(mediator != null){
            mediator.notify(this, event, data);
        }
    }
}
