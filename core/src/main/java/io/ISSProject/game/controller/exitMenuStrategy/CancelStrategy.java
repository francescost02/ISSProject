package io.ISSProject.game.controller.exitMenuStrategy;

import io.ISSProject.game.controller.gameState.ExitMenuState;
import io.ISSProject.game.controller.gameState.GameState;

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
