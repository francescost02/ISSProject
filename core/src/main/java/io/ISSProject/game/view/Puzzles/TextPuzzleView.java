package io.ISSProject.game.view.Puzzles;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import io.ISSProject.game.controller.Puzzles.PuzzleController;

public class TextPuzzleView extends AbstractPuzzleView {
    private TextField inputField;

    public TextPuzzleView(String title, Skin skin, PuzzleController controller){
        super(title, skin, controller);
    }

    @Override
    protected void setupPuzzleUI(Table contentTable) {
        inputField = new TextField("", getSkin());
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
