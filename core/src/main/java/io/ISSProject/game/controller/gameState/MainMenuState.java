package io.ISSProject.game.controller.gameState;

//import io.test.controller.mainMenuCommand.MainMenuInvoker;

import io.ISSProject.game.controller.GameContext;

public class MainMenuState implements GameState {

    private final GameContext gameContext;
    public GameContext getGameContext() {
        return gameContext;
    }

    public MainMenuState (GameContext gameContext) {
        this.gameContext = gameContext;
        // Il Mediator già si occupa di gestire il cambio di stato, questa chiamata è ridondante
        //gameContext.changeState(this); // Passa allo stato MainMenu
    }
    @Override
    public void exit() {
        System.out.println ("Uscita da MainMenuState");
    }
}
