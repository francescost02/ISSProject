package io.ISSProject.game.model.GameplayState;

import io.ISSProject.game.controller.menuState.GameContext;
import io.ISSProject.game.controller.menuState.GameState;


public class BrotherLivingRoomState implements GameState {
    private final GameContext gameContext;

    public BrotherLivingRoomState(GameContext gameContext){
        this.gameContext = gameContext;
    }

    @Override
    public void exit(){
        System.out.println("Uscita da BrotherLivingRoomState");
    }
}
