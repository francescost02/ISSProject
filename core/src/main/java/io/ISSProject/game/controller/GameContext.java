package io.ISSProject.game.controller;

import io.ISSProject.game.controller.gameState.BrotherLivingRoomState;
import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.controller.gameState.MainMenuState;
import io.ISSProject.game.model.Scene;

// GameManager: Control class that handles state transitions
public class GameContext {
    private GameState currentState;
    private Scene currentScene;

    public GameContext() {
        // Imposta lo stato iniziale durante l'istanza
        this.currentState = new MainMenuState(this);
    }

    public void changeState(GameState state) {
        this.currentState = state;
        System.out.println("Stato corrente: " + this.currentState.getClass().getSimpleName());

        // Se lo stato corrente ha una scena associata, aggiornala nel contesto
        if (state instanceof BrotherLivingRoomState) {
            this.currentScene = ((BrotherLivingRoomState) state).getCurrentScene();
        } else {
            this.currentScene = null;
        }
    }
    public void exit() {
        if (currentState != null) {
            currentState.exit();
        }
    }
    public GameState getCurrentState() {
        return currentState;
    }
    public Scene getCurrentScene () { return currentScene;}
}





