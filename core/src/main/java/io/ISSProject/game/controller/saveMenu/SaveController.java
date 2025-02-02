package io.ISSProject.game.controller.saveMenu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.GameplayController;
import io.ISSProject.game.controller.ScreenController;
import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.SaveMenuState;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.view.MainGame;
import io.ISSProject.game.view.saveMenu.SaveGameView;

import java.util.List;

public class SaveController implements GameComponent {
    private SaveGameView saveGameView;
    private SaveMenuState saveMenuState;
    private final GameContext gameContext;
    private final SaveGameManager saveGameManager;
    private GameState currentState;
    private GameMediator mediator;
    private List<Clue> clues;

    public SaveController(String username) {
        this.gameContext = GameContext.getInstance();
        this.saveGameManager = new SaveGameManager(clues);
        this.saveGameView = new SaveGameView(username); // Passa il nome utente
        this.currentState = gameContext.getCurrentState(); // Recupera lo stato corrente
        this.saveMenuState = new SaveMenuState(currentState, gameContext);
        addListeners();
    }

    private void addListeners() {
        saveGameView.getLoadButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveMenuCommand loadCommand = new LoadCommand(saveGameManager, saveGameView);
                loadCommand.execute();
            }
        });

        saveGameView.getDeleteButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SaveMenuCommand deleteCommand = new DeleteCommand(saveGameManager, saveGameView);
                deleteCommand.execute();
            }
        });

        saveGameView.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveMenuState.exit();
                io.ISSProject.game.controller.saveMenu.BackCommand command = new BackCommand(saveMenuState);
                command.execute();

                if (mediator!=null){
                    mediator.notify(SaveController.this, "RETURN_TO_MAIN_MENU");
                }

                /*
                // Torna alla schermata principale
                Game game = (Game) Gdx.app.getApplicationListener();
                MainMenuController2 mainMenuController = new MainMenuController2(gameContext);
                game.setScreen(mainMenuController.getScreen());
                // Aggiorna l'InputProcessor per il menu principale
                Gdx.input.setInputProcessor(mainMenuController.getScreen().getStage());
                */
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
    public void setMediator(GameMediator mediator){
        this.mediator = mediator;
    }

    @Override
    public void notify(String event, Object...data){
        if(mediator != null){
            mediator.notify(this, event, data);
        }
    }

    public Screen getScreen() {
        return saveGameView;
    }

    public void switchToLoadedScene(Scene loadedScene) {
        // Cambia la schermata attuale alla nuova scena
        Game game = (Game) Gdx.app.getApplicationListener();

        GameplayController gameplayController = new GameplayController(gameContext);
        MainMenuController2 mainMenuController2 = new MainMenuController2(gameContext);

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
