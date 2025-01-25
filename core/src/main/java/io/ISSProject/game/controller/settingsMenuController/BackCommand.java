package io.ISSProject.game.controller.settingsMenuController;

import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.controller.gameState.SettingsMenuState;

public class BackCommand implements Command {
    private SettingsMenuState settingsMenuState;

    public BackCommand (SettingsMenuState settingsMenuState) {
        this.settingsMenuState = settingsMenuState;
    }

    @Override
    public void execute() {
        System.out.println("Uscita cancellata. Ritorna allo stato "+ settingsMenuState.getPreviousState().getClass().getSimpleName());

        // Recupera lo stato precedente
        GameState previousState = settingsMenuState.getPreviousState();

        if (previousState == null) {
            System.out.println("Nessuno stato precedente.");
        }
    }
}

