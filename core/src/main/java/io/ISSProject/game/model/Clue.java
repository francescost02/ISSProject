package io.ISSProject.game.model;

public class Clue extends InteractiveObject {
    private boolean found; // Indica se il giocatore ha trovato l'indizio

    public Clue(String tooltipText, String dialogText) {
        super(tooltipText, dialogText);
        this.found = false;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    @Override
    public void interact() {
        System.out.println("Hai trovato un indizio: " + getTooltipText());
    }
}
