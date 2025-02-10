package io.ISSProject.game.controller.puzzles;

import io.ISSProject.game.view.puzzles.AbstractPuzzleView;

public interface PuzzleController {
    void handleInput(Object input, AbstractPuzzleView view);
    String getPuzzleDescription();
    String getPuzzleHint();
    void showHint(AbstractPuzzleView view);
}

