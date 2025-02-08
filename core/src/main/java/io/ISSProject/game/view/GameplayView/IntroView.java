package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class IntroView extends AbstractSceneView {
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;
    private GameplayController controller;

    public IntroView(GameplayController controller) {
        super();
        this.backgroundTexture = new Texture(Gdx.files.internal("images/investigatore_sfondo.png"));
        this.controller = controller;
        this.dialogLines = new String[] {
            "Un giovane e promettente investigatore privato riceve un messaggio di richiesta di aiuto dal fratello...",
            "Il fratello dell’investigatore è un banchiere... dice di essere in pericolo e per questo stabilisce di incontrarsi in un bar per discuterne.",
            "L’investigatore attende nel bar ma dopo diverse ore il fratello non si fa ancora vivo...",
            "Insospettito decide di recarsi a casa del fratello per capire cosa sia successo.",
            "giunto nell’appartamento capisce subito che qualcosa non va...",
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
