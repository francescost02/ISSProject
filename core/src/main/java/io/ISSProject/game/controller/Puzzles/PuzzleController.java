package io.ISSProject.game.controller.Puzzles;

import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Puzzles.PuzzleStrategy;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;

public class PuzzleController {
    private final PuzzleStrategy model;
    private final GameMediator mediator;
    private final PuzzleObject puzzleObject;

    public PuzzleController(PuzzleStrategy model, GameMediator mediator, PuzzleObject puzzleObject) {
        this.model = model;
        this.mediator = mediator;
        this.puzzleObject = puzzleObject;
    }

    public void handleSolutionAttempt(Object input, AbstractPuzzleView view) {
        if (input == null || input.toString().trim().isEmpty()) {
            view.showEmptyInputDialog();
            return;
        }

        if (model.solve(input)) {
            mediator.notify(puzzleObject, "PUZZLE_SOLVED", puzzleObject);
            view.showSuccessDialog();
        } else {
            view.showErrorDialog();
        }
    }

    public String getPuzzleDescription() {
        return model.getDescription();
    }

    public String getPuzzleHint() {
        return model.getHint();
    }

    public void showHint(AbstractPuzzleView view) {
        view.showHintDialog();
    }
}
