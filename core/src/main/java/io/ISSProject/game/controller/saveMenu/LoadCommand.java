package io.ISSProject.game.controller.saveMenu;

import io.ISSProject.game.controller.GameplayController;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.GameStateMemento;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.view.saveMenu.SaveGameView;

public class LoadCommand implements SaveMenuCommand {
    private final GameContext gameContext;
    private final SaveGameManager saveGameManager;
    private final SaveGameView saveGameView;

    public LoadCommand(SaveGameManager saveGameManager, SaveGameView saveGameView) {
        this.gameContext = GameContext.getInstance();
        this.saveGameManager = saveGameManager;
        this.saveGameView = saveGameView;
    }

    @Override
    public void execute() {
        String selectedFile = saveGameView.getSelectedFile();
        if (selectedFile != null && !selectedFile.isEmpty()) {
            try {
                // Carica il memento dal file
                GameStateMemento gameState = saveGameManager.loadGame(saveGameView.getUsername(), selectedFile);

                if (gameState != null) {
                    // Ripristina lo stato nel contesto di gioco
                    gameContext.restoreScene(gameState);

                    SaveController saveController = new SaveController(saveGameView.getUsername());
                    Scene scena = gameContext.loadSceneByName(gameState.getSceneName());
                    saveController.switchToLoadedScene(scena);

                    // CAMBIO DI STATO: Aggiorniamo lo stato in base alla scena caricata
                    GameState newState = scena.getAssociatedState();
                    if (newState != null) {
                        gameContext.changeState(newState);
                        System.out.println("Nuovo stato impostato: " + newState.getClass().getSimpleName());
                    } else {
                        System.err.println("Errore: la scena caricata non ha uno stato associato!");
                    }

                    System.out.println("Partita caricata correttamente: " + selectedFile);
                } else {
                    System.err.println("Errore: il file selezionato non contiene uno stato valido.");
                }
            } catch (Exception e) {
                System.err.println("Errore durante il caricamento del file di salvataggio: " + e.getMessage());
            }
        } else {
            System.out.println("Nessun file di salvataggio selezionato.");
        }
    }
}
