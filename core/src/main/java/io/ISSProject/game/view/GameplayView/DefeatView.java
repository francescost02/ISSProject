package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class DefeatView extends AbstractSceneView {
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;
    private GameplayController controller;

    public DefeatView() {
        super();
        this.backgroundTexture = new Texture(Gdx.files.internal("images/defeat.png"));
        this.dialogLines = new String[] {
            "Dobbiamo ancora lavorare sulle tue capacità investigative",
            "Hai accusato Marco, ma era lui a lasciarti gli indizi per arrivare alla verità.",
            "Gli indizi con la lettera \"M\" non erano un segnale di colpevolezza, ma il modo in cui Marco cercava di farti arrivare al boss.\n",
                "Purtroppo, hai abboccato al depistaggio del boss e il vero colpevole è sfuggito alla giustizia. Riprova e fai più attenzione ai dettagli!"
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
