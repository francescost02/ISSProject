package io.ISSProject.game.controller.saveMenu;

import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.controller.gameState.SaveMenuState;

public class BackCommand implements SaveMenuCommand {
    private SaveMenuState saveMenuState;

    public BackCommand (SaveMenuState saveMenuState) {
        this.saveMenuState = saveMenuState;
    }

    @Override
    public void execute() {
        System.out.println("Uscita cancellata. Ritorna allo stato precedente.");

        // Recupera lo stato precedente
        GameState previousState = saveMenuState.getPreviousState();

        if (previousState == null) {
            System.out.println("Nessuno stato precedente.");
        }
    }
}
