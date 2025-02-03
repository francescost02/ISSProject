package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.model.Scene;

public class GameplayState implements GameState {
    private final GameContext gameContext;
    private final Scene currentScene;

    public GameplayState(GameContext gameContext, Scene currentScene) {
        this.gameContext = gameContext;
        this.currentScene = currentScene;
        //gameContext.setCurrentScene(currentScene);
        System.out.println("Entrato nello stato GameplayState per la scena: " + currentScene.getName());
    }

    @Override
    public void exit() {
        gameContext.saveCurrentScene();
        System.out.println("Uscita dallo stato GameplayState per la scena: " + currentScene.getName());
    }

    @Override
    public Scene getAssociatedScene() {
        return currentScene;
    }
}
