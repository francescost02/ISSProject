package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class BeforeAbandonedShelterView extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;


    public BeforeAbandonedShelterView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/BeforeAbandonedShelter.jpg"));
        // Inizializzazione sicura dell'array dialoghi
        this.dialogLines = new String[]{
            "L'indirizzo sembra portare ad un rifugio abbandonato fuori città...",
            "Il vento sferza tra gli alberi, facendo scricchiolare i rami secchi...",
            "L’investigatore si ferma davanti alla casa... inghiottito dall’oscurità del bosco.",
            "Il legno marcio e le finestre infrante parlano di anni di abbandono.",
            "L'investigatore a bassa voce esclama tra sé e sé: Se è qui che dovrei trovarlo, non promette bene...",
            "Spinge la porta, che si apre con un cigolio sinistro...",
            ""
        };
    }

    public void setupUI() {
        super.setupUI();
    }

    public void setupLayout() {
        super.setupLayout();
        if (dialogLines != null && dialogLines.length > 0) {
            dialogWindow.updateText(dialogLines[currentLineIndex]);
        }
    }

    private void advanceDialog() {
        if (currentLineIndex < dialogLines.length - 1) {
            currentLineIndex++;
            dialogWindow.updateText(dialogLines[currentLineIndex]);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Controlla se il tasto ENTER è stato appena premuto
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            advanceDialog();
        }

        stage.act(delta);
        stage.draw();
    }
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void setupInteractiveObjects() {
        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        getGameArea().add(gameStack).expand().fill();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}


