package io.ISSProject.game.controller.menuState;

//import io.test.controller.mainMenuCommand.MainMenuInvoker;

public class MainMenuState implements GameState {

    private final GameContext gameContext;
    public GameContext getGameContext() {
        return gameContext;
    }

    public MainMenuState (GameContext gameContext) {
        this.gameContext = gameContext;
        gameContext.changeState(this); // Passa allo stato MainMenu
    }
    @Override
    public void exit() {
        System.out.println ("Uscita da MainMenuState");
    }
}
