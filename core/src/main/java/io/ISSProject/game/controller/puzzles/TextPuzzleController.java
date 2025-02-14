package io.ISSProject.game.controller.puzzles;

import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.puzzles.ReverseTextPuzzle;
import io.ISSProject.game.view.puzzles.AbstractPuzzleView;

public class TextPuzzleController extends BasePuzzleController{
    private final ReverseTextPuzzle textPuzzle;

    public TextPuzzleController(ReverseTextPuzzle model, GameMediator mediator, PuzzleObject puzzleObject){
        super(model, mediator, puzzleObject);
        this.textPuzzle = model;
    }

    @Override
    public void handleInput(Object input, AbstractPuzzleView view){
        if(!(input instanceof String)){
            return;
        }

        String solution = (String) input;
        if(solution.isEmpty()){
            view.showEmptyInputDialog();
            return;
        }

        if(textPuzzle.solve(solution)){
            notifyPuzzleSolved();
            view.showSuccessDialog();
        } else{
            view.showErrorDialog();
        }
    }

}
