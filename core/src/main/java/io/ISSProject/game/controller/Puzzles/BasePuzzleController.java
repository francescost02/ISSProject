package io.ISSProject.game.controller.Puzzles;


import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Puzzles.PuzzleStrategy;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;

// 2. Controller base astratto con la logica comune
public abstract class BasePuzzleController implements PuzzleController {
    protected final PuzzleStrategy model;
    protected final GameMediator mediator;
    protected final PuzzleObject puzzleObject;

    public BasePuzzleController(PuzzleStrategy model, GameMediator mediator, PuzzleObject puzzleObject) {
        this.model = model;
        this.mediator = mediator;
        this.puzzleObject = puzzleObject;
    }

    public PuzzleStrategy getModel(){
        return model;
    }
/*
    @Override
    public String getPuzzleDescription() {
        return puzzleObject.getDialogText();
    }

 */
    @Override
    public String getPuzzleDescription(){
        return model.getDescription();
    }

    @Override
    public void showHint(AbstractPuzzleView view) {
        view.showHintDialog();
    }


    @Override public String getPuzzleHint(){
        return model.getHint();
    }

    protected void notifyPuzzleSolved() {
        mediator.notify(puzzleObject, "PUZZLE_SOLVED", puzzleObject);
    }
}
