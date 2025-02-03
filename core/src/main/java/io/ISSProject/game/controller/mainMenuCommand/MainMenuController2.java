package io.ISSProject.game.controller.mainMenuCommand;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.MainMenuState;
import io.ISSProject.game.model.settingsMenuModel.AudioManager;
import io.ISSProject.game.view.MainMenuView2;

public class MainMenuController2 implements GameComponent {

    private MainMenuReceiver mainMenuReceiver;
    private MainMenuView2 mainMenuView;
    private MainMenuState mainMenuState;
    private final GameContext gameContext;
    private GameMediator mediator;


    public MainMenuController2() {
        AudioManager.getInstance().playMusic();
        this.gameContext = GameContext.getInstance();
        this.mainMenuView = new MainMenuView2();
        //addListeners();

        //this.mainMenuState = new MainMenuState(this.mainMenuState.getGameContext());
        //this.mainMenuReceiver = new MainMenuReceiver(this.mainMenuState.getGameContext());
        //this.mainMenuReceiver = new MainMenuReceiver(gameContext);
        this.mainMenuState = new MainMenuState(gameContext);
    }

    private void addListeners() {
        // Aggiungi i listener per ogni pulsante
        mainMenuView.getNewGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //mainMenuState.getGameContext().exit();
                //gameContext.exit();
                NewGameCommand command = new NewGameCommand(mainMenuReceiver);
                command.execute();
                mediator.notify(MainMenuController2.this, "START_NEW_GAME");
            }
        });

        mainMenuView.getLoadGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameContext.exit();
                LoadGameCommand command = new LoadGameCommand(mainMenuReceiver);
                command.execute();
                mediator.notify(MainMenuController2.this, "LOAD_GAME");
            }
        });

        mainMenuView.getSettingsButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameContext.exit();
                SettingsCommand command = new SettingsCommand(mainMenuReceiver);
                command.execute();
                mediator.notify(MainMenuController2.this, "SHOW_SETTINGS_MENU");
            }
        });

        mainMenuView.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameContext.exit();
                ExitGameCommand command = new ExitGameCommand(mainMenuReceiver);
                command.execute();
                mediator.notify(MainMenuController2.this, "SHOW_EXIT_MENU");
            }
        });
    }

    public MainMenuView2 getScreen() {
        return mainMenuView;
    }

    @Override
    public void setMediator(GameMediator mediator) {
        this.mediator = mediator;

        this.mainMenuReceiver = new MainMenuReceiver(mediator);
        addListeners();
    }

    @Override
    public void notify(String event, Object... data) {
        if (mediator != null) {
            mediator.notify(this, event, data);
        }
    }
}
