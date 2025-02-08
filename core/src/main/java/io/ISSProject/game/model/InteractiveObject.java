package io.ISSProject.game.model;

public abstract class InteractiveObject {
    private String tooltipText; // Testo che appare quando passi sopra l'oggetto
    private String dialogText; // Testo che appare nella finestra di dialogo quando interagisci
    private float x;
    private float y;
    private float width;
    private float height;

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

    protected void setX(float x){
        this.x = x;
    }
    protected void setY(float y){
        this.y = y;
    }
    public float getX(){
        return x;
    }
    public float getY() {
        return y;
    }
    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
}
