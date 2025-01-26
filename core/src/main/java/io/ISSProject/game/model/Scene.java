package io.ISSProject.game.model;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private String name; // Nome della scena
    private List<InteractiveObject> interactiveObjects; // Lista degli oggetti interattivi nella scena

    public int getRequiredClues() {
        return requiredClues;
    }

    private int requiredClues; // Numero di indizi richiesti per completare la scena
    private Scene nextScene; // Scena successiva

    public Scene(String name, int requiredClues) {
        this.name = name;
        this.requiredClues = requiredClues;
        this.interactiveObjects = new ArrayList<>();
    }

    // Aggiungi un metodo per impostare la scena successiva
    public void setNextScene(Scene nextScene) {
        this.nextScene = nextScene;
    }

    // Ottieni la scena successiva
    public Scene getNextScene() {
        return nextScene;
    }

    // Aggiunge un oggetto interattivo alla scena
    public void addInteractiveObject(InteractiveObject object) {
        // Evita di aggiungere oggetti duplicati
        if (!interactiveObjects.contains(object)) {
            interactiveObjects.add(object);
        }
    }

    // Verifica se la scena è completata (indizi trovati >= requiredClues)
    public boolean isCompleted() {
        long foundClues = interactiveObjects.stream()
            .filter(obj -> obj instanceof Clue)
            .map(obj -> (Clue) obj)
            .filter(Clue::isFound)
            .count();
        System.out.println("Indizi trovati: " + foundClues + "/" + requiredClues);
        return foundClues >= requiredClues;
    }


    // Aggiungi un metodo per verificare se l'indizio è già stato trovato
    public boolean isClueAlreadyFound(String clueTooltipText) {
        return interactiveObjects.stream()
            .filter(obj -> obj instanceof Clue)
            .map(obj -> (Clue) obj)
            .anyMatch(clue -> clue.getTooltipText().equals(clueTooltipText) && clue.isFound());
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<InteractiveObject> getInteractiveObjects() {
        return interactiveObjects;
    }
}
