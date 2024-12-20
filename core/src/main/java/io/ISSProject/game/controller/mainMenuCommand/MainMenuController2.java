package io.ISSProject.game.controller.mainMenuCommand;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.mainMenuCommand.*;
import io.ISSProject.game.controller.menuState.GameContext;
import io.ISSProject.game.controller.menuState.MainMenuState;
import io.ISSProject.game.model.settingsMenuModel.AudioManager;
import io.ISSProject.game.view.MainMenuView2;

public class MainMenuController2 {

    private MainMenuReceiver mainMenuReceiver;
    private MainMenuView2 mainMenuView;
    private MainMenuState mainMenuState;
   private final GameContext gameContext;


    public MainMenuController2(GameContext gameContext) {
        AudioManager.getInstance().playMusic();
        this.gameContext = gameContext;
        this.mainMenuView = new MainMenuView2();
        addListeners();

        //this.mainMenuState = new MainMenuState(this.mainMenuState.getGameContext());
        //this.mainMenuReceiver = new MainMenuReceiver(this.mainMenuState.getGameContext());
        this.mainMenuReceiver = new MainMenuReceiver(gameContext);
        this.mainMenuState = new MainMenuState(gameContext);
    }

    private void addListeners() {
        // Aggiungi i listener per ogni pulsante
        mainMenuView.getNewGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //mainMenuState.getGameContext().exit();
                gameContext.exit();
                NewGameCommand command = new NewGameCommand(mainMenuReceiver);
                command.execute();
            }
        });

        mainMenuView.getLoadGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameContext.exit();
                LoadGameCommand command = new LoadGameCommand(mainMenuReceiver);
                command.execute();
            }
        });

        mainMenuView.getSettingsButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameContext.exit();
                SettingsCommand command = new SettingsCommand(mainMenuReceiver);
                command.execute();
            }
        });

        mainMenuView.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameContext.exit();
                ExitGameCommand command = new ExitGameCommand(mainMenuReceiver);
                command.execute();
            }
        });
    }

    public MainMenuView2 getScreen() {
        return mainMenuView;
    }
}
