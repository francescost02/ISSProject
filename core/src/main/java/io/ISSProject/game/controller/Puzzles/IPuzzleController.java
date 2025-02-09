package io.ISSProject.game.controller.Puzzles;

import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;

public interface IPuzzleController {
    void handleInput(Object input, AbstractPuzzleView view);
    String getPuzzleDescription();
    String getPuzzleHint();
    void showHint(AbstractPuzzleView view);
}

