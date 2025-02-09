package io.ISSProject.game.controller.Puzzles;

import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;

public interface PuzzleController {
    void handleInput(Object input, AbstractPuzzleView view);
    String getPuzzleDescription();
    String getPuzzleHint();
    void showHint(AbstractPuzzleView view);
}

