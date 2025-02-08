package io.ISSProject.game.controller.Puzzles;


import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;

// 2. Controller base astratto con la logica comune
public abstract class BasePuzzleController implements IPuzzleController {
    protected final GameMediator mediator;
    protected final PuzzleObject puzzleObject;

    public BasePuzzleController(GameMediator mediator, PuzzleObject puzzleObject) {
        this.mediator = mediator;
        this.puzzleObject = puzzleObject;
    }

    @Override
    public String getPuzzleDescription() {
        return puzzleObject.getDialogText();
    }

    @Override
    public void showHint(AbstractPuzzleView view) {
        view.showHintDialog();
    }

    protected void notifyPuzzleSolved() {
        mediator.notify(puzzleObject, "PUZZLE_SOLVED", puzzleObject);
    }
}
