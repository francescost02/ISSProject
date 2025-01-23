package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.controller.GameContext;
import io.ISSProject.game.controller.GameplayController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;


public class BrotherLivingRoomState implements GameState {
    private final GameContext gameContext;
    private Scene currentScene; //scena con oggetti interattivi
    private GameplayController controller;

    public BrotherLivingRoomState(GameContext gameContext) {
        this.gameContext = gameContext;
        this.currentScene = new Scene ("Brother's Living Room", 2); //imposta nome e indizi

        // Aggiungi indizi alla scena
        Clue lamp = new Clue("Una lampada vintage", "Una lampada vintage...non credo mi possa aiutare nella risoluzione di questo caso.");
        this.currentScene.addInteractiveObject(lamp);
        System.out.println("Oggetti nella scena: " + this.currentScene.getInteractiveObjects().size());
    }

    @Override
    public void exit() {
        System.out.println ("Uscita da Brother'sLivingRoomState");
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
