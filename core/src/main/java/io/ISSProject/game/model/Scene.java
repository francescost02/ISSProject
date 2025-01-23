package io.ISSProject.game.model;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;

public class Scene {
    private String name; // Nome della scena
    private List<InteractiveObject> interactiveObjects; // Lista degli oggetti interattivi nella scena
    private int requiredClues; // Numero di indizi richiesti per completare la scena

    public Scene(String name, int requiredClues) {
        this.name = name;
        this.requiredClues = requiredClues;
        this.interactiveObjects = new ArrayList<>();
    }

    // Aggiunge un oggetto interattivo alla scena
    public void addInteractiveObject(InteractiveObject object) {
        interactiveObjects.add(object);
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

    /*
    // Restituisce solo gli oggetti che sono indizi
    public List<Clue> getClues() {
        List<Clue> clues = new ArrayList<>();
        for (InteractiveObject object : interactiveObjects) {
            if (object instanceof Clue clue) {
                clues.add(clue);
            }
        }
        return clues;
    }
    */

    /*
    // Trova un indizio per nome
    public Clue getClueByName(String clueName) {
        for (InteractiveObject obj : interactiveObjects) {
            if (obj instanceof Clue) {
                Clue clue = (Clue) obj;
                if (clue.getTooltipText().equals(clueName)) {
                    return clue;  // Restituisce l'indizio se il nome corrisponde
                }
            }
        }
        return null;  // Se non trovato
    }
    */

    // Aggiungi il metodo per aggiornare lo stato di un indizio
    public void updateClueStatus(String clueName, boolean found) {
        for (InteractiveObject obj : interactiveObjects) {
            if (obj instanceof Clue) {
                Clue clue = (Clue) obj;
                if (clue.getTooltipText().equals(clueName)) {
                    clue.setFound(found);  // Aggiorna lo stato dell'indizio
                    break;
                }
            }
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<InteractiveObject> getInteractiveObjects() {
        return interactiveObjects;
    }
}
