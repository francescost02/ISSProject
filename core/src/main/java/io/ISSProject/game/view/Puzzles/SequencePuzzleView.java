package io.ISSProject.game.view.Puzzles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.Puzzles.PuzzleController;

public class SequencePuzzleView extends AbstractPuzzleView {
    private  TextButton[] buttons;
    private  Table buttonTable;
    private Image switchesImage;
    private Table mainContent;

    public SequencePuzzleView(String title, Skin skin, PuzzleController controller) {
        super(title, skin, controller);
    }

    @Override
    protected void setupPuzzleUI(Table contentTable) {
        Texture switchesTexture = new Texture(Gdx.files.internal("assets/images/test_img.jpg"));
        switchesImage = new Image(switchesTexture);

        Table imageTable = new Table();
        imageTable.add(switchesImage).width(400).height(200);
        contentTable.add(imageTable).pad(10).row();

        this.buttons = new TextButton[3];
        this.buttonTable = new Table();
        buttonTable.center();

        // Crea i tre bottoni
        for (int i = 0; i < 3; i++) {
            final int buttonNumber = i + 1;
            buttons[i] = new TextButton(String.valueOf(buttonNumber), getSkin());
            buttons[i].setColor(1,1,1,0);
            buttons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    controller.handleInput(buttonNumber, SequencePuzzleView.this);
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
