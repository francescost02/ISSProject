package io.ISSProject.game.controller.Puzzles;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Puzzles.SequenceButtonPuzzle;
import io.ISSProject.game.view.Puzzles.AbstractPuzzleView;
import io.ISSProject.game.view.Puzzles.SequencePuzzleView;

import javax.sound.midi.Sequence;

public class SequenceButtonPuzzleController extends BasePuzzleController{
    private final SequenceButtonPuzzle sequencePuzzle;
    private SequencePuzzleView view;

    public SequenceButtonPuzzleController(SequenceButtonPuzzle model, GameMediator mediator, PuzzleObject puzzleObject){
        super(model, mediator, puzzleObject);
        this.sequencePuzzle = model;
    }

    @Override
    public void handleInput(Object input, AbstractPuzzleView view) {
        if (!(input instanceof Integer) || !(view instanceof SequencePuzzleView)) {
            return;
        }
        int buttonNumber = (Integer) input;
        SequencePuzzleView sequenceView = (SequencePuzzleView) view;

        boolean isCorrect = sequencePuzzle.checkButton(buttonNumber);

        if(isCorrect){
            sequenceView.disableActor(buttonNumber);
            //sequenceView.updateDialogText("Interruttore" + buttonNumber + "attivato");

            if(sequencePuzzle.isCompleted()){
                notifyPuzzleSolved();
                //sequenceView.updateDialogText("Hai risolto l'enigma");
                view.showSuccessDialog();
            }
        } else {
            sequencePuzzle.resetPuzzle();
            sequenceView.resetActors();
            //sequenceView.updateDialogText("Sequenza errata! Riprova.");
            view.showErrorDialog();
        }
    }

    @Override
    public String getPuzzleDescription(){
        return "Trova la sequenza corretta. Ogni interruttore può essere usato solo una volta";
    }

    @Override
    public String getPuzzleHint(){
        return "Prova a osservare attentamente la sequenza degli interruttori";
    }

    public void onButtonPressed(int buttonNumber, AbstractPuzzleView view){
        handleInput(buttonNumber, view);
    }
}
