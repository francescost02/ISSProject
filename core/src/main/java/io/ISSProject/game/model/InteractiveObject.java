package io.ISSProject.game.model;

public abstract class InteractiveObject {
    private String tooltipText; // Testo che appare quando passi sopra l'oggetto
    private String dialogText; // Testo che appare nella finestra di dialogo quando interagisci

    public InteractiveObject(String tooltipText, String dialogText) {
        this.tooltipText = tooltipText;
        this.dialogText = dialogText;
    }

    public String getTooltipText() {
        return tooltipText;
    }

    public String getDialogText() {
        return dialogText;
    }

    public abstract void interact();
}
