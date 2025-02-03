package io.ISSProject.game.controller.saveMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.SaveMenuState;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.FileManager;
import io.ISSProject.game.model.saveModel.GameStateMemento;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.view.saveMenu.SaveGameView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SaveController implements GameComponent {
    private SaveGameView saveGameView;
    private SaveMenuState saveMenuState;
    private final GameContext gameContext;
    private final FileManager fileManager;
    private GameState currentState;
    private GameMediator mediator;
    private final SaveGameManager saveGameManager;
    private List<Clue> clues;

    public SaveController() {
        this.gameContext = GameContext.getInstance();
        this.saveGameManager = new SaveGameManager(/*clues*/);
        this.saveGameView = new SaveGameView(gameContext.getUsername()); // Passa il nome utente
        this.currentState = gameContext.getCurrentState(); // Recupera lo stato corrente
        this.saveMenuState = new SaveMenuState(currentState, gameContext);
        this.fileManager = new FileManager();
        addListeners();
    }

    private void addListeners() {
        saveGameView.getLoadButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveMenuCommand loadCommand = new LoadCommand(SaveController.this, saveGameManager);
                loadCommand.execute();
            }
        });

        saveGameView.getDeleteButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveMenuCommand deleteCommand = new DeleteCommand(SaveController.this, saveGameManager);
                deleteCommand.execute();
            }
        });

        saveGameView.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveMenuState.exit();
                if (mediator != null) {
                    mediator.notify(SaveController.this, "RETURN_TO_MAIN_MENU");
                }
            }
        });

        saveGameView.getFileList().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                boolean isItemSelected = saveGameView.getSelectedFile() != null;
                saveGameView.getLoadButton().setDisabled(!isItemSelected);
                saveGameView.getDeleteButton().setDisabled(!isItemSelected);
            }
        });
    }

    @Override
    public void setMediator(GameMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void notify(String event, Object... data) {
        if (mediator != null) {
            mediator.notify(this, event, data);
        }
    }

    public Screen getScreen() {
        return saveGameView;
    }

/*
    public void switchToLoadedScene(Scene loadedScene) {
        // Cambia la schermata attuale alla nuova scena
        Game game = (Game) Gdx.app.getApplicationListener();
        GameplayController gameplayController = GameplayController.getInstance(gameContext);

        gameContext.setCurrentScene(loadedScene);
        game.setScreen(gameplayController.getScreen());

        // Aggiorna l'InputProcessor per la scena caricata
        Gdx.input.setInputProcessor(gameplayController.getScreen().getStage());
    }*/

    public void switchToLoadedScene(Scene loadedScene) {
        // Cambia la schermata attuale alla nuova scena
        Game game = (Game) Gdx.app.getApplicationListener();

        GameplayController gameplayController = new GameplayController();
        MainMenuController2 mainMenuController2 = new MainMenuController2();

        mediator = new GameMediator(game);
        mediator.registerComponents2(
            mainMenuController2,
            gameplayController);

        mainMenuController2.setMediator(mediator);
        gameplayController.setMediator(mediator);

        gameContext.setCurrentScene(loadedScene);
        game.setScreen(gameplayController.getScreen());

        // Aggiorna l'InputProcessor per la scena caricata
        Gdx.input.setInputProcessor(gameplayController.getScreen().getStage());
    }
}
