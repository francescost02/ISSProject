package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class VictoryView extends AbstractSceneView {
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;
    private GameplayController controller;

    private TextButton backButton;

    public VictoryView() {
        super();
        this.backgroundTexture = new Texture(Gdx.files.internal("images/winner.png"));
        this.dialogLines = new String[] {
            "Complimenti, hai risolto il caso e salvato tuo fratello",
            "\nHai capito che Marco non poteva essere il colpevole. " +
                "Tutti gli indizi con la lettera \"M\"ti hanno guidato fino al boss, dimostrando che qualcuno dall'interno voleva aiutarti",
            "Marco era un pentito, non un traditore.\n",
                "Il boss ha cercato di confonderti fino all'ultimo, ma tu hai messo insieme i pezzi e smascherato la verità. La giustizia ha vinto!"
        };
    }

    public void setupUI() {
        super.setupUI();
        // Memorizzarlo come variabile membro
        this.backButton = backButton;
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
