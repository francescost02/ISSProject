package io.ISSProject.game.controller.menuState;


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

    public GameState getPreviousState() {
        return previousState;
    }
}
