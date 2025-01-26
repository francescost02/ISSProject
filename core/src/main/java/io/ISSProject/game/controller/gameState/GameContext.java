package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.controller.GameInitializer;
import io.ISSProject.game.controller.gameState.GameplayState;
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

        // Inizializza le scene del gioco
        GameInitializer.initializeGame(this);
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
        System.out.println ("Scena corrente: " + this.currentScene.getName());
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    // Metodo per passare alla scena successiva
    public void goToNextScene() {
        if (currentScene != null && currentScene.getNextScene() != null) {
            this.currentScene = currentScene.getNextScene();
            System.out.println("Passaggio alla scena successiva: " + currentScene.getName());
        } else {
            System.out.println("Non esiste una scena successiva.");
        }
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}





