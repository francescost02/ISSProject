
package io.ISSProject.game.model;

import io.ISSProject.game.controller.gameState.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Scene {
    private String name; // Nome della scena
    private List<InteractiveObject> interactiveObjects; // Lista degli oggetti interattivi nella scena
    private int requiredClues; // Numero di indizi richiesti per completare la scena
    private GameState associatedState;

    public Scene(String name, int requiredClues) {
        this.name = name;
        this.requiredClues = requiredClues;
        this.interactiveObjects = new ArrayList<>();
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


    // verifica se l'indizio è già stato trovato
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

    // Metodo per esportare gli indizi trovati in formato String
    public List<String> exportFoundClues() {
        return interactiveObjects.stream()
            .filter(obj -> obj instanceof Clue)    // Filtra solo gli oggetti che sono indizi
            .map(obj -> (Clue) obj)                // Converte gli oggetti in Clue
            .filter(Clue::isFound)                 // Considera solo gli indizi trovati
            .map(Clue::getTooltipText)            // Estrae il tooltipText
            .collect(Collectors.toList());        // Ritorna una lista di stringhe
    }


    // Metodo per importare gli indizi da una lista di String
    public void markCluesAsFound(List<String> foundClues) {

        if (interactiveObjects == null) {
            System.out.println("Errore: interactiveObjects è nullo!");
            return;
        }

        if (interactiveObjects.isEmpty()) {
            System.out.println("Errore: interactiveObjects è vuoto!");
            return;
        }

        interactiveObjects.stream()
            .filter(obj -> obj instanceof Clue)    // Filtra solo gli oggetti che sono indizi
            .map(obj -> (Clue) obj)                // Converte gli oggetti in Clue
            .forEach(clue -> {
                System.out.println("Indizio trovato: " + clue.getTooltipText());
                if (foundClues.contains(clue.getTooltipText())) {
                    System.out.println("Segno come trovato: " + clue.getTooltipText());
                    clue.setFound(true);
                }
            });
    }

    // Imposta lo stato associato alla scena
    public void setAssociatedState(GameState state) {
        this.associatedState = state;
    }

    // Restituisce lo stato associato alla scena
    public GameState getAssociatedState() {
        return associatedState;
    }
}


