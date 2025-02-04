
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

public class StoreView extends AbstractSceneView {
    private final GameplayController controller;
    private final Texture backgroundTexture;
    private final Vector2 tempCoords = new Vector2();
    private String[] dialogLines;
    private int currentLineIndex = 0;


    public StoreView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/Store.jpeg"));
        // Inizializzazione sicura dell'array dialoghi
        this.dialogLines = new String[]{
            "Investigatore: Salve, sa dirmi cosa è successo al ragazzo che è venuto qualche ora fa a comprare questi oggetti?",
            "Commesso: Mi dispiace, non posso aiutarla...",
            "Investigatore: È importante. Dica quello che sa.",
            "Commesso: Va bene... un uomo è entrato mentre suo fratello acquistava una torcia e dei guanti...",
            "Commesso: Sembrava una discussione accesa, poi ha concluso l'acquisto e se n'è andato.",
            "Investigatore (pensando): Da come lo descrive... è sicuramente Marco,  ",
            "uno scagnozzo di un’organizzazione criminale a cui avevo messo i bastoni tra le ruote in casi precedenti"
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

