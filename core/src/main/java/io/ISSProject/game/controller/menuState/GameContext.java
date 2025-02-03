package io.ISSProject.game.controller.menuState;

// GameManager: Control class that handles state transitions
public class GameContext {
    private GameState currentState;
    public GameContext() {
        // Imposta lo stato iniziale durante l'istanza
        this.currentState = new MainMenuState(this);
    }

    public void changeState(GameState state) {
            this.currentState = state;
            System.out.println("Stato corrente: " + this.currentState.getClass().getSimpleName());
    }
    public void exit() {
        if (currentState != null) {
            currentState.exit();
        }
    }
    public GameState getCurrentState() {
        return currentState;
    }
}





