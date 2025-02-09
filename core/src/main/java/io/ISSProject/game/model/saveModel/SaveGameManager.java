package io.ISSProject.game.model.saveModel;

import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Diary.DetectiveDiary;
import io.ISSProject.game.model.Diary.DiaryEntry;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Scene;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SaveGameManager {
    private static final String SAVE_DIRECTORY = "saves/";
   private final FileManager fileManager;

    public SaveGameManager() {
        this.fileManager = new FileManager();
        ensureSaveDirectoryExists();
    }

    //Assicura che la directory principale dei salvataggi esista.
    public void ensureSaveDirectoryExists() {
        File saveDir = new File(SAVE_DIRECTORY);
        if (!saveDir.exists() && !saveDir.mkdirs()) {
            System.err.println("Impossibile creare la directory principale dei salvataggi.");
        }
    }


    //Assicura che la directory dei salvataggi per uno specifico utente esista.
    public void ensureUserDirectoryExists(String username) {
        File userDir = new File(SAVE_DIRECTORY + username);
        if (!userDir.exists() && !userDir.mkdirs()) {
            System.err.println("Impossibile creare la directory per l'utente: " + username);
        }
    }

    //Salva lo stato del gioco in un file.
    public void saveGame(String username, String fileName, Scene scene) {
        if (scene == null) {
            System.err.println("ERRORE: La scena è null! Controlla l'inizializzazione di currentScene.");
            return; // Evita di procedere con il salvataggio
        }

        // Assicura che la directory dell'utente esista
        ensureUserDirectoryExists(username);

        // Genera il percorso completo del file
        String filePath = getFilePath(username, fileName);

        // Evita duplicati aggiungendo un suffisso se necessario
        filePath = resolveDuplicateFileName(filePath);

        List<String> completedPuzzles = new ArrayList<>();
        for (InteractiveObject obj : scene.getInteractiveObjects()) {
            if (obj instanceof PuzzleObject puzzleObj && puzzleObj.isPuzzleCompleted()) {
                completedPuzzles.add(puzzleObj.getTooltipText());
            }
        }

        // Crea un oggetto memento con i dati da salvare
        GameStateMemento memento = new GameStateMemento(
            username,                     // Nome dell'utente
            scene.getName(),              // Nome della scena
            scene.exportFoundClues()      // Indizi trovati nella scena
        );

        // Aggiungi i puzzle completati al memento
        memento.setCompletedPuzzles(completedPuzzles);

        System.out.println ( scene.getName() );

        // Salva il memento nel file JSON
        System.out.println("Salvataggio in corso: " + filePath);
        fileManager.saveToFile(memento, filePath);
    }


    //Carica lo stato del gioco da un file.
    public GameStateMemento loadGame(String username, String fileName) {
        // Genera il percorso completo del file
        String filePath = getFilePath(username, fileName);
        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException("Il file di salvataggio non esiste: " + filePath);
        }

        System.out.println("Caricamento del file: " + filePath);

        // Carica i dati dal file
        GameStateMemento memento = fileManager.loadFromFile(filePath);

        if (memento == null) {
            throw new IllegalStateException("Errore durante il caricamento del salvataggio.");
        }
        DetectiveDiary diary = DetectiveDiary.getInstance();
        diary.clear();

        for (DiaryEntry entry: memento.getDiaryEntries()){
            diary.addEntry(entry.getTitle(), entry.getDescription());
        }

        return memento; // Ritorna il memento caricato
    }


    //Elimina un file di salvataggio specifico.
    public boolean deleteSave(String username, String fileName) {
        String filePath = getFilePath(username, fileName);
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Il file non esiste: " + filePath);
            return false;
        }

        boolean deleted = file.delete();
        if (deleted) {
            System.out.println("File eliminato: " + fileName);
        } else {
            System.err.println("Errore durante l'eliminazione del file: " + fileName);
        }
        return deleted;
    }

    //Genera un nome file univoco basato sulla data e l'ora.
    public String generateFileName(String username) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return username + "_" + timestamp + ".json";
    }
    //Risolve i conflitti di nomi aggiungendo un suffisso incrementale.
    public String resolveDuplicateFileName(String filePath) {
        File file = new File(filePath);
        int counter = 1;

        while (file.exists()) {
            String baseName = filePath.substring(0, filePath.lastIndexOf('.'));
            String extension = filePath.substring(filePath.lastIndexOf('.'));
            filePath = baseName + "_" + counter + extension;
            file = new File(filePath);
            counter++;
        }

        return filePath;
    }

    //Ottiene il percorso completo di un file di salvataggio per un utente specifico.
    public String getFilePath(String username, String fileName) {
        if (!fileName.endsWith(".json")) {
            fileName += ".json";
        }
        return SAVE_DIRECTORY + username + "/" + fileName;
    }



// *** NUOVE FUNZIONI AGGIUNTE ***
    // Restituisce una lista di tutti i file di salvataggio dell'utente
    public List<String> getAllSaveFiles(String username) {
        File userDir = new File(SAVE_DIRECTORY + username);
        if (!userDir.exists() || !userDir.isDirectory()) {
            return new ArrayList<>();
        }

        return Arrays.stream(userDir.listFiles((dir, name) -> name.endsWith(".json")))
            .map(File::getName)
            .sorted()
            .collect(Collectors.toList());
    }

    // Restituisce l'ultimo file salvato dell'utente
    public String getLastSavedFile(String username) {
        List<String> saveFiles = getAllSaveFiles(username);
        if (saveFiles.isEmpty()) return null;
        return saveFiles.get(saveFiles.size() - 1);
    }


}
