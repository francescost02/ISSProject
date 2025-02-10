package io.ISSProject.game.view.puzzles;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.puzzles.PuzzleController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.CluePaper;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.puzzles.ReverseTextPuzzle;

public class TextPuzzleView extends AbstractPuzzleView {

    private TextField inputField;

    public TextPuzzleView(String title, Skin skin, PuzzleController controller){
        super(title, skin, controller);
    }

    @Override
    protected void setupPuzzleUI(Table contentTable) {

        inputField = new TextField("", getSkin());

        // Modifica lo stile per centrare il testo e ingrandire il font
        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle(inputField.getStyle());

        // Allinea il testo al centro
        inputField.setAlignment(Align.center);

        contentTable.add(inputField).width(300f).pad(10).row();
    }

    @Override
    protected boolean validateInput() {
        return !inputField.getText().isEmpty();
    }

    @Override
    protected Object getInputValue() {
        return inputField.getText();
    }

    /*
    @Override
    protected void result(Object object) {
        if (object instanceof Boolean && (Boolean) object) {
            controller.attemptSolution(inputField.getText());
        }
    }
     */
}
