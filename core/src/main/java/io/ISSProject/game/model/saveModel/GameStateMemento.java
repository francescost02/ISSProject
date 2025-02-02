package io.ISSProject.game.model.saveModel;

import java.util.List;

public class GameStateMemento {
    private String username;        // Nome dell'utente
    private String sceneName;       // Nome della scena salvata
    private List<String> foundClues; // Indizi trovati (solo i nomi/tooltipText)

    // Costruttore
    public GameStateMemento(String username, String sceneName, List<String> foundClues) {
        this.username = username;
        this.sceneName = sceneName;
        this.foundClues = foundClues;
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
}
