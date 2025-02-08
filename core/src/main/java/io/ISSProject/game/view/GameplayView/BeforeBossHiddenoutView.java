package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
    import com.badlogic.gdx.utils.viewport.FitViewport;
import io.ISSProject.game.view.DialogWindow;

public class BeforeBossHiddenoutView extends AbstractSceneView {
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public BeforeBossHiddenoutView() {
        super();
        this.backgroundTexture = new Texture(Gdx.files.internal("images/BeforeBossHiddenout.png"));
        this.dialogLines = new String[] {
            "L’investigatore si dirige verso la villa.",
            "Luna piena. Il vento soffia tra i rami scheletrici degli alberi.",
            "Investigatore (sottovoce, scrutando la casa nell'oscurità):\n" +
            "Eccola… il nuovo covo dell'organizzazione. Se i documenti erano corretti, qui avviene il traffico di denaro.",
            "L'investigatore prende un respiro profondo, poi si incammina lentamente verso la villa, con l'adrenalina che pulsa nelle vene.",
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
