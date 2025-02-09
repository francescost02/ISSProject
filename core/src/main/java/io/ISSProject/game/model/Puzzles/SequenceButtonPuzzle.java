package io.ISSProject.game.model.Puzzles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SequenceButtonPuzzle implements PuzzleStrategy {
    // La sequenza corretta dei bottoni (2->3->1)
    private final int[] correctSequence = {2, 3, 1};
    private int currentStep = 0;
    private boolean completed = false;

    // Lista per tenere traccia della sequenza inserita dall'utente
    private List<Integer> currentSequence = new ArrayList<>();

    // Set per tenere traccia dei bottoni già utilizzati
    private Set<Integer> usedButtons = new HashSet<>();

    @Override
    public void initialize() {
        currentStep = 0;
        completed = false;
        currentSequence = new ArrayList<>();
        usedButtons = new HashSet<>();
    }

    /**
     * Verifica se il bottone premuto è corretto nella sequenza
     * @param buttonNumber il numero del bottone premuto (1-3)
     * @return true se il bottone è corretto nella sequenza, false altrimenti
     */
    public boolean checkButton(int buttonNumber) {

        // Verifica se il bottone è già stato usato
        if (usedButtons.contains(buttonNumber)) {
            return false;
        }

        if (buttonNumber == correctSequence[currentStep]) {
            // Aggiunge il bottone agli utilizzati
            usedButtons.add(buttonNumber);
            currentSequence.add(buttonNumber);

            currentStep++;
            if (currentStep == correctSequence.length) {
                completed = true;
            }
            return true;
        } else {
            resetPuzzle();
            return false;
        }
    }

    public void resetPuzzle() {
        currentSequence.clear();
        usedButtons.clear();
        currentStep = 0;
        completed = false;
    }

    /**
     * Verifica se un bottone è già stato utilizzato
     * @param buttonNumber il numero del bottone da verificare
     * @return true se il bottone è già stato usato, false altrimenti
     */
    public boolean isButtonUsed(int buttonNumber) {
        return usedButtons.contains(buttonNumber);
    }

    /**
     * Restituisce il passo corrente nella sequenza
     * @return l'indice del passo corrente (0-2)
     */
    public int getCurrentStep() {
        return currentStep;
    }

    @Override
    public boolean solve(Object input) {
        // Non utilizzato in questo tipo di puzzle
        return completed;
    }

    @Override
    public String getDescription() {
        return "Trova la giusta sequenza dei bottoni. Ogni bottone può essere usato una sola volta!";
    }

    @Override
    public String getHint() {
        return "Osserva attentamente l'ordine e ricorda: non puoi usare lo stesso bottone due volte!";
    }

    @Override
    public boolean isCompleted() {
        return completed;
    }


    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Restituisce la lunghezza totale della sequenza
     * @return la lunghezza della sequenza corretta
     */
    public int getSequenceLength() {
        return correctSequence.length;
    }
}
