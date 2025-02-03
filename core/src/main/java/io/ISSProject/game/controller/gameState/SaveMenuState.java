package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.model.Scene;

public class SaveMenuState implements GameState {
    private final GameContext gameContext;
    private final GameState previousState;
    public SaveMenuState(GameState previousState, GameContext gameContext) {
        this.previousState = previousState;
        this.gameContext = gameContext;
        gameContext.changeState(this);
    }
    @Override
    public void exit() {
        System.out.println("Uscita da SaveMenu");
    }
    public GameState getPreviousState() {
        return previousState;
    }

    public GameContext getGameContext() {
        return gameContext;
    }
    public Scene getAssociatedScene() {
        return null;  // Nessuna scena associata al menu principale
    }
}
