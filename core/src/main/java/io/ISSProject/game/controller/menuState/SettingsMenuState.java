package io.ISSProject.game.controller.menuState;

public class SettingsMenuState implements GameState {
    private final GameContext gameContext;
    private final GameState previousState;

    public SettingsMenuState(GameState previousState, GameContext gameContext) {
        this.previousState = previousState;
        this.gameContext = gameContext;
        gameContext.changeState(this); //passa allo stato settingsMenu
    }
    @Override
    public void exit() {
        System.out.println("Uscita da SettingsMenu");
    }
    public GameState getPreviousState() {
        return previousState;
    }

    public GameContext getGameContext() {
        return gameContext;
    }
}
