package io.ISSProject.game.view.puzzles;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import io.ISSProject.game.controller.puzzles.PuzzleController;

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

}
