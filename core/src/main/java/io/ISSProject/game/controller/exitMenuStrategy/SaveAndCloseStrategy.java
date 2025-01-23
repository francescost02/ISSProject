package io.ISSProject.game.controller.exitMenuStrategy;

import com.badlogic.gdx.Gdx;

public class SaveAndCloseStrategy implements ExitStrategy {
    @Override
    public void execute() {
        System.out.println("Gioco chiuso con salvataggio...");
        // Add save game logic
        Gdx.app.exit();
    }
}
