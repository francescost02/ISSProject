package io.ISSProject.game.controller.exitMenuStrategy;

import com.badlogic.gdx.Gdx;

public class CloseWithoutSavingStrategy implements ExitStrategy {
    @Override
    public void execute() {
        System.out.println("Gioco chiuso senza salvataggio...");
        Gdx.app.exit();
    }
}
