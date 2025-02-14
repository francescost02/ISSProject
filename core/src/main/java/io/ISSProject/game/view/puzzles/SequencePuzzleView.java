package io.ISSProject.game.view.puzzles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.puzzles.PuzzleController;

public class SequencePuzzleView extends AbstractPuzzleView {
    private Actor[] invisibleActors;
    private Image switchesImage;
    private Label dialogLabel;
    private final int PUZZLE_WIDTH = 600;
    private final int PUZZLE_HEIGHT = 400;
    private final int BUTTON_WIDTH = 80;
    private final int BUTTON_HEIGHT = 100;

    public SequencePuzzleView(String title, Skin skin, PuzzleController controller) {
        super(title, skin, controller);
        //setFillParent(true);
    }

    @Override
    protected void setupPuzzleUI(Table contentTable) {
        Stack puzzleStack = new Stack();

        // Immagine
        Texture switchesTexture = new Texture(Gdx.files.internal("assets/images/buttons.jpg"));
        switchesImage = new Image(switchesTexture);
        puzzleStack.add(switchesImage);

        // Layer interattivo
        Table interactiveLayer = new Table();
        interactiveLayer.setFillParent(true); // Questo va sul layer, non sui container
        invisibleActors = new Actor[3];

        float[][] positions = {
            {0.4f, 0.6f},
            {0.6f, 0.6f},
            {0.75f, 0.6f}
        };

        for (int i = 0; i < 3; i++) {
            final int buttonNumber = i + 1;

            // Crea l'attore invisibile
            Actor invisibleActor = new Actor();
            invisibleActor.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            invisibleActor.setTouchable(Touchable.enabled);
            invisibleActor.setDebug(true); // Per vedere l'area cliccabile

            // Calcola le coordinate in pixel
            float x = positions[i][0] * PUZZLE_WIDTH - (BUTTON_WIDTH / 2);
            float y = positions[i][1] * PUZZLE_HEIGHT - (BUTTON_HEIGHT / 2);

            // Aggiungi il listener
            invisibleActor.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    System.out.println("Bottone " + buttonNumber + " cliccato");
                    controller.handleInput(buttonNumber, SequencePuzzleView.this);
                }
            });

            invisibleActors[i] = invisibleActor;

            // Aggiungi l'attore al layer con la posizione corretta
            interactiveLayer.addActor(invisibleActor);
            invisibleActor.setPosition(x, y);
        }

        puzzleStack.add(interactiveLayer);
        contentTable.add(puzzleStack).width(PUZZLE_WIDTH).height(PUZZLE_HEIGHT).pad(20);
    }

    public void disableActor(int actorNumber) {
        if (actorNumber > 0 && actorNumber <= invisibleActors.length) {
            invisibleActors[actorNumber-1].setTouchable(Touchable.disabled);
            // Feedback visivo opzionale
            invisibleActors[actorNumber-1].setColor(1, 1, 1, 0.3f);
        }
    }

    public void resetActors() {
        for (Actor actor : invisibleActors) {
            actor.setTouchable(Touchable.enabled);
            actor.setColor(1, 1, 1, 1);
        }
    }

    @Override
    protected boolean validateInput() {
        return true;
    }

    @Override
    protected Object getInputValue() {
        return null;
    }
}
