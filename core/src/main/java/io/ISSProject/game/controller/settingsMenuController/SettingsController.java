package io.ISSProject.game.controller.settingsMenuController;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.gameState.*;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.settingsMenuModel.SettingsModel;
import io.ISSProject.game.view.settingsMenu.SettingsView;


// CONTROLLER
public class SettingsController implements GameComponent {
    private final SettingsModel model;
    private final SettingsView settingMenuView;
    private final GameContext gameContext;
    private SettingsMenuState settingsMenuState;
    private GameState currentState;
    private Command setResolutionCommand;
    private Command setVolumeCommand;
    private Command toggleMuteCommand;
    private GameMediator mediator;

    public SettingsController(GameContext gameContext) {

        this.model = new SettingsModel();
        //this.view = new SettingsView(model.getAssetManager().getSkin());
        this.settingMenuView = new SettingsView();
        this.gameContext = gameContext;
        this.currentState = gameContext.getCurrentState(); //recupera lo stato corrente
        this.settingsMenuState = new SettingsMenuState(currentState, gameContext);


        // Configura il controller con la vista e il modello
        settingMenuView.setAvailableResolutions(model.getAvailableResolutions());
        settingMenuView.setCurrentResolution(model.getCurrentResolution());
        settingMenuView.setCurrentVolume(model.getCurrentVolume());
        settingMenuView.setMuteState(model.isMuted());

        addListeners();
    }

    public void addListeners() {
        // Listener per la selezione della risoluzione
        this.settingMenuView.getResolutionSelectBox().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String resolution = settingMenuView.getResolutionSelectBox().getSelected();
                setResolutionCommand = new SetResolutionCommand(SettingsController.this, resolution);
                setResolutionCommand.execute();
            }
        });

        this.settingMenuView.getAudioSlider().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                int volume = (int) settingMenuView.getAudioSlider().getValue();
                setVolumeCommand = new SetVolumeCommand(SettingsController.this, volume);
                setVolumeCommand.execute();
            }
        });
        // Listener per la checkbox Mute
        settingMenuView.getMuteCheckBox().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                toggleMuteCommand = new ToggleMuteCommand(SettingsController.this);
                toggleMuteCommand.execute();
            }
        });

        settingMenuView.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                settingsMenuState.exit();
                /*
                if(mediator!=null){
                    mediator.notify(SettingsController.this, "RETURN_TO_MAIN_MENU");
                }

                 */

                BackCommand command = new BackCommand(settingsMenuState);
                command.execute();

                if (mediator!=null) {
                    // Verifica lo stato precedente
                    GameState previousState = settingsMenuState.getPreviousState();

                    // Se lo stato precedente è il menu principale
                    if (previousState instanceof MainMenuState) {
                        mediator.notify(SettingsController.this, "RETURN_TO_MAIN_MENU");
                    }
                    // Se lo stato precedente è il menu di pausa
                    else if (previousState instanceof PauseMenuState) {
                        mediator.notify(SettingsController.this, "RETURN_TO_PAUSE_MENU");
                    }
                }
                else {
                    // Gestione di altri eventi
                    System.out.println("Evento non gestito: " + event);
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
    }

    // Metodi per essere utilizzati dai comandi
    public void setResolution(String resolution) {
        model.setResolution(resolution);
        settingMenuView.setCurrentResolution(resolution);
    }

    public void setVolume(int volume) {
        model.setVolume(volume);
        settingMenuView.setCurrentVolume(volume);
    }

    public void setMute(boolean mute) {
        model.toggleMute();
        settingMenuView.setMuteState(model.isMuted());
    }

    public Screen getScreen() {
        return settingMenuView;
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
}



