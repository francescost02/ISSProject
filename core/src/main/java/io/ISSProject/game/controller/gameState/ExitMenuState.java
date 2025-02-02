package io.ISSProject.game.controller.gameState;


import io.ISSProject.game.model.Scene;

public class ExitMenuState implements GameState {
    private final GameState previousState;
    private final GameContext gameContext;

    public ExitMenuState(GameState previousState, GameContext gameContext) {
        this.previousState = previousState;
        this.gameContext = gameContext;
        gameContext.changeState(this); //passa allo stato exitMenu
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    @Override
    public void exit() {
        System.out.println("Uscita da ExitMenuState");
    }

    @Override
    public Scene getAssociatedScene() {
        return null;  // Nessuna scena associata al menu principale
    }

    public GameState getPreviousState() {
        return previousState;
    }
}
