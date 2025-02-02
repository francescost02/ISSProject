package io.ISSProject.game.controller.exitMenuStrategy;

import com.badlogic.gdx.Gdx;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.model.saveModel.FileManager;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import java.io.IOException;

public class SaveAndCloseStrategy implements ExitStrategy {
    private final GameContext gameContext;
    private final SaveGameManager saveGameManager;

    public SaveAndCloseStrategy(SaveGameManager saveGameManager) {
        this.gameContext = GameContext.getInstance();
        this.saveGameManager = saveGameManager;
    }

    @Override
    public void execute() {
        System.out.println("Gioco chiuso con salvataggio...");

        // Controlla che l'utente sia loggato
        String username = gameContext.getUsername();
        if (username == null || username.isEmpty()) {
            System.out.println("Errore: Nessun utente loggato. Impossibile salvare.");
        } else {
            try {
                // Log dello stato corrente
                System.out.println("Stato corrente: " + gameContext.getCurrentState().getClass().getSimpleName());
                System.out.println("Scena corrente: " + gameContext.getCurrentScene().getName());

                // Controlla se la scena corrente è null
                if (gameContext.getCurrentScene() == null) {
                    System.err.println("ERRORE: La scena corrente è null. Impossibile salvare.");
                    return; // Esci senza salvare
                }

                // Genera il nome del file di salvataggio
                String saveFileName = saveGameManager.generateFileName(username);

                // Effettua il salvataggio dello stato corrente
                saveGameManager.saveGame(username, saveFileName, gameContext.getCurrentScene());
                System.out.println("Partita salvata con il nome file: " + saveFileName);
            } catch (Exception e) {
                System.err.println("Errore durante il salvataggio del gioco: " + e.getMessage());
            }
        }

    // Chiude l'applicazione
        Gdx.app.exit();
    }
}
