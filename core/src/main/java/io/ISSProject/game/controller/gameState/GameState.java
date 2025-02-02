
package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.model.Scene;

// Interface for GameState (Part of State Pattern)
public interface GameState {
    void exit();
    Scene getAssociatedScene();  // Metodo per ottenere la scena associata
}
