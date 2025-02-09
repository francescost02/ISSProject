package io.ISSProject.game.controller.Puzzles;

import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Puzzles.SequenceButtonPuzzle;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;
import io.ISSProject.game.view.Puzzles.SequencePuzzleView;

public class SequenceButtonPuzzleController extends BasePuzzleController{
    private final SequenceButtonPuzzle sequencePuzzle;
    private SequencePuzzleView view;

    public SequenceButtonPuzzleController(SequenceButtonPuzzle model, GameMediator mediator, PuzzleObject puzzleObject){
        super(model, mediator, puzzleObject);
        this.sequencePuzzle = model;
    }

    @Override
    public void handleInput(Object input, AbstractPuzzleView view) {
        if (!(input instanceof Integer)) {
            return;
        }
        int buttonNumber = (Integer) input;
        boolean isCorrect = sequencePuzzle.checkButton(buttonNumber);

        if(isCorrect){
            if(sequencePuzzle.isCompleted()){
                notifyPuzzleSolved();
                view.showSuccessDialog();
            }
        } else {
            view.showErrorDialog();
        }
    }

    public void onButtonPressed(int buttonNumber, AbstractPuzzleView view){
        handleInput(buttonNumber, view);
    }

    @Override
    public String getPuzzleHint(){
        return "Prova a osservare attentamente la sequenza";
    }
}
