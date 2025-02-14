package io.ISSProject.game.model.saveModel;

import io.ISSProject.game.model.Diary.DetectiveDiary;
import io.ISSProject.game.model.Diary.DiaryEntry;

import java.util.ArrayList;
import java.util.List;

public class GameStateMemento {
    private String username;        // Nome dell'utente
    private String sceneName;       // Nome della scena salvata
    private List<String> foundClues; // Indizi trovati (solo i nomi/tooltipText)
    private List<DiaryEntry> diaryEntries;
    private List<String> completedPuzzles;

    // Costruttore
    public GameStateMemento(String username, String sceneName, List<String> foundClues) {
        this.username = username;
        this.sceneName = sceneName;
        this.foundClues = foundClues;
        this.diaryEntries = DetectiveDiary.getInstance().getEntriesChronological();
        this.completedPuzzles = new ArrayList<>();
    }

    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public List<String> getFoundClues() {
        return foundClues;
    }

    public void setFoundClues(List<String> foundClues) {
        this.foundClues = foundClues;
    }

    public List<DiaryEntry> getDiaryEntries(){
        return diaryEntries;
    }

    public void setDiaryEntries(List<DiaryEntry> diaryEntries){
        this.diaryEntries = diaryEntries;
    }

    public void addCompletedPuzzle(String puzzleId) {
        if (!completedPuzzles.contains(puzzleId)) {
            completedPuzzles.add(puzzleId);
        }
    }

    public boolean isPuzzleCompleted(String puzzleId) {
        return completedPuzzles.contains(puzzleId);
    }

    public void setCompletedPuzzles(List<String> completedPuzzles) {
        this.completedPuzzles = completedPuzzles;
    }

    public List<String> getCompletedPuzzles() {
        return completedPuzzles != null ? completedPuzzles : new ArrayList<>();
    }
}
