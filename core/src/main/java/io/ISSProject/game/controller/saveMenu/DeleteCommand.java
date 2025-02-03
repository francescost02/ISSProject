package io.ISSProject.game.controller.saveMenu;

import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.view.saveMenu.SaveGameView;

public class DeleteCommand implements SaveMenuCommand {
    private final SaveGameManager saveGameManager;
    private final SaveController saveController;

    public DeleteCommand(SaveController saveController, SaveGameManager saveGameManager) {
        this.saveGameManager = saveGameManager;
        this.saveController = saveController;
    }
    @Override
    public void execute() {
        SaveGameView view = (SaveGameView) saveController.getScreen();
        String selectedFile = view.getSelectedFile();
        String username = view.getUsername(); // Ottieni l'username dalla view

        if (selectedFile != null && username != null && !username.isEmpty()) {
            if (saveGameManager.deleteSave(username, selectedFile)) {
                System.out.println("Salvataggio eliminato: " + selectedFile);
                view.populateFileList(username); // Aggiorna la lista dei file dell'utente
            } else {
                System.out.println("Errore nell'eliminazione del file o file non trovato.");
            }
        } else {
            System.out.println("Nessun file selezionato o nome utente non valido.");
        }
    }

}
