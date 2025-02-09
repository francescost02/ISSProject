package io.ISSProject.game.controller.Puzzles;

import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Puzzles.ReverseTextPuzzle;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;

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
