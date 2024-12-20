package io.ISSProject.game.controller.exitMenuStrategy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import io.ISSProject.game.controller.mainMenuCommand.MainMenuController2;
import io.ISSProject.game.controller.menuState.ExitMenuState;
import io.ISSProject.game.controller.menuState.GameState;

public class CancelStrategy implements ExitStrategy {
    private ExitMenuState exitMenuState;
    public CancelStrategy (ExitMenuState exitMenuState) {
        this.exitMenuState = exitMenuState;
    }
    @Override
    public void execute() {
        System.out.println("Uscita cancellata. Ritorna allo stato precedente.");

        // Recupera lo stato precedente
        GameState previousState = exitMenuState.getPreviousState();

        if (previousState == null) {
            System.out.println("Nessuno stato precedente.");
        }
    }
}
