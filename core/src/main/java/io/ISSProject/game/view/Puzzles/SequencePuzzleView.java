package io.ISSProject.game.view.Puzzles;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.ISSProject.game.controller.Puzzles.PuzzleController;

public class SequencePuzzleView extends AbstractPuzzleView {
    private final TextButton[] buttons;
    private final Table buttonTable;

    public SequencePuzzleView(String title, Skin skin, PuzzleController controller) {
        super(title, skin, controller);
        this.buttons = new TextButton[3];
        this.buttonTable = new Table();
    }

    @Override
    protected void setupPuzzleUI(Table contentTable) {
        buttonTable.center();

        // Crea i tre bottoni
        for (int i = 0; i < 3; i++) {
            final int buttonNumber = i + 1;
            buttons[i] = new TextButton(String.valueOf(buttonNumber), getSkin());
            buttons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (controller instanceof SequencePuzzleController) {
                        ((SequencePuzzleController) controller).onButtonPressed(buttonNumber);
                    }
                }
            });
            buttonTable.add(buttons[i]).pad(10).size(100, 100);
        }

        contentTable.add(buttonTable).expand().fill().row();
    }

    public void disableButton(int buttonNumber) {
        buttons[buttonNumber-1].setDisabled(true);
        buttons[buttonNumber-1].setColor(Color.GRAY);
    }

    public void resetButtons() {
        for (TextButton button : buttons) {
            button.setDisabled(false);
            button.setColor(Color.WHITE);
        }
    }

    @Override
    protected boolean validateInput() {
        return true; // Non necessario per questo tipo di puzzle
    }

    @Override
    protected Object getInputValue() {
        return null; // Non necessario per questo tipo di puzzle
    }
}
