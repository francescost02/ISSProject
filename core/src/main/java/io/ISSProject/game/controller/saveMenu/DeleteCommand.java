package io.ISSProject.game.controller.saveMenu;

import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.view.saveMenu.SaveGameView;

public class DeleteCommand implements SaveMenuCommand {
    private final SaveGameManager saveGameManager;
    private final SaveGameView saveGameView;

    public DeleteCommand(SaveGameManager saveGameManager, SaveGameView saveGameView) {
        this.saveGameManager = saveGameManager;
        this.saveGameView = saveGameView;
    }
    @Override
    public void execute() {
        String selectedFile = saveGameView.getSelectedFile();
        String username = saveGameView.getUsername(); // Ottieni l'username dalla view

        if (selectedFile != null && username != null && !username.isEmpty()) {
            if (saveGameManager.deleteSave(username, selectedFile)) {
                System.out.println("Salvataggio eliminato: " + selectedFile);
                saveGameView.populateFileList(username); // Aggiorna la lista dei file dell'utente
            } else {
                System.out.println("Errore nell'eliminazione del file o file non trovato.");
            }
        } else {
            System.out.println("Nessun file selezionato o nome utente non valido.");
        }
    }

}
