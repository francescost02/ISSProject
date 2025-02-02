
package io.ISSProject.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.PauseMenuState;
import io.ISSProject.game.view.PauseMenuView;

public class PauseMenuController implements GameComponent {
    private PauseMenuView pauseMenuView;
    private final GameContext gameContext;
    private PauseMenuState pauseMenuState;
    private GameMediator mediator;

    public PauseMenuController(GameContext gameContext) {
        this.gameContext = GameContext.getInstance();
        this.pauseMenuView = new PauseMenuView();
        this.pauseMenuState = new PauseMenuState();
        addListeners();
    }

    private void addListeners() {
        pauseMenuView.getSettingsButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (mediator != null) {
                    mediator.notify(PauseMenuController.this, "SHOW_SETTINGS_MENU");
                }
            }
        });

        pauseMenuView.getExitMenuButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (mediator != null) {
                    mediator.notify(PauseMenuController.this, "SHOW_EXIT_MENU");
                }
            }
        });

        pauseMenuView.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (mediator != null) {
                    mediator.notify(PauseMenuController.this, "RETURN_TO_GAME");
                }
            }
        });

    }

    public PauseMenuView getScreen() {
        return pauseMenuView;
    }

    public void setMediator(GameMediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public void notify(String event, Object... data) {
        // Eventuali notifiche da gestire nel futuro
    }
}
