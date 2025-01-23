package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.controller.GameContext;

public class PauseMenuState implements GameState {

    private final GameContext gameContext;
    //invoker

    public PauseMenuState (GameContext gameContext) {
        this.gameContext = gameContext;
    }
    @Override
    public void exit() {
        System.out.println("Uscita dal menu di pausa");
    }
}
