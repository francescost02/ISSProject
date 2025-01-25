package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.controller.gameState.BrotherLivingRoomState;
import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.controller.gameState.MainMenuState;
import io.ISSProject.game.model.Scene;

// GameManager: Control class that handles state transitions
public class GameContext {
    private GameState currentState;
    private Scene currentScene;
    private Scene savedScene; // Aggiungi questa variabile per salvare la scena

    public GameContext() {
        // Imposta lo stato iniziale durante l'istanza
        this.currentState = new MainMenuState(this);
    }

    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();  // Uscita dallo stato precedente
        }
        this.currentState = newState;

        // Aggiorna la scena associata automaticamente
        this.currentScene = newState.getAssociatedScene();

        System.out.println("Stato corrente: " + this.currentState.getClass().getSimpleName());
    }

    public void exit() {
        if (currentState != null) {
            currentState.exit();
        }
    }

    public void saveCurrentState() {
        this.savedScene = this.currentScene;
    }

    public void restorePreviousState() {
        if (this.savedScene != null) {
            this.currentScene = this.savedScene;
        }
    }


    public GameState getCurrentState() {
        return currentState;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}





