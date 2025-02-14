package io.ISSProject.game.model;

import io.ISSProject.game.model.Diary.DetectiveDiary;

public class Clue extends InteractiveObject {

    private boolean found; // Indica se il giocatore ha trovato l'indizio
    private DetectiveDiary diary;
    private String content;

    public Clue(String tooltipText, String dialogText, String contentDiary) {
        super(tooltipText, dialogText);
        this.found = false;
        this.content = contentDiary;
        this.diary = DetectiveDiary.getInstance();
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
        if(found){
            diary.addEntry(getTooltipText(), getContent());
        }
    }

    public String getContent() {
        return content;
    }

    @Override
    public void interact() {
        if(!found) {
            found = true;
            diary.addEntry(getTooltipText(), getContent());
            System.out.println("Hai trovato un indizio: " + getTooltipText() + "aggiunto al diario");
        }
    }
}
