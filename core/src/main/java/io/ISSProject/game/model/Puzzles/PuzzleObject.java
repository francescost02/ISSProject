package io.ISSProject.game.model.Puzzles;

import io.ISSProject.game.controller.mediator.GameComponent;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.SceneObject;

public class PuzzleObject extends SceneObject implements GameComponent {
    private final PuzzleStrategy puzzle;
    private  GameMediator mediator;
    private final String originalDialogText;

    public PuzzleObject(String tooltipText, String dialogText, float x, float y, float width, float height, PuzzleStrategy puzzle){
        super(tooltipText, dialogText, x, y, width, height);
        this.puzzle = puzzle;
        this.originalDialogText = dialogText;
        puzzle.initialize();
    }

    @Override
    public String getDialogText(){
        return puzzle.isCompleted()?"Hai già risolto qusto enigma": originalDialogText;
    }

    @Override
    public void interact() {
        if (puzzle.isCompleted()) {
            super.interact();
        } else {
            super.interact();
            notify("SHOW_PUZZLE", puzzle);
        }

    }

    @Override
    public void setMediator(GameMediator mediator) {
        if (mediator == null){
            throw new IllegalArgumentException("Il mediator non può essere null");
        }
        this.mediator = mediator;
    }

    @Override
    public void notify(String event, Object... data) {
        if (mediator != null) {
            mediator.notify(this, event, data);
        } else{
            System.err.println("Errore: Mediator non inizializzato in PuzzleObject");
        }
    }

    public void setPuzzleCompleted(boolean completed) {
        if (puzzle instanceof ReverseTextPuzzle) {
            ((ReverseTextPuzzle) puzzle).setCompleted(completed);
        }
    }

    public boolean isPuzzleCompleted() {
        return puzzle.isCompleted();
    }

    public PuzzleStrategy getPuzzle(){
        return puzzle;
    }
}
