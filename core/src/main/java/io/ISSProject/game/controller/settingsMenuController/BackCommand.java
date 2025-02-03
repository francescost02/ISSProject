package io.ISSProject.game.controller.settingsMenuController;

import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.controller.gameState.SettingsMenuState;


public class BackCommand implements Command {
    private SettingsController settingsController;
    private SettingsMenuState settingsMenuState;

    public BackCommand (SettingsMenuState settingsMenuState) {
        this.settingsMenuState = settingsMenuState;
    }

    @Override
    public void execute() {
        System.out.println("Uscita cancellata. Ritorna allo stato precedente.");

        // Recupera lo stato precedente
        GameState previousState = settingsMenuState.getPreviousState();

        if (previousState == null) {
            System.out.println("Nessuno stato precedente.");
        }
    }
}

