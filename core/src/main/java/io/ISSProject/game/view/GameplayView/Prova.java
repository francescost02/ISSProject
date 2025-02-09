/*
package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.ISSProject.game.controller.GameplayController;
import io.ISSProject.game.view.DialogWindow;

public class StoreView extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private Texture backgroundTexture;
    private DialogWindow dialogWindow;
    private TextButton nextButton;
    private TextButton pauseButton;
    private String[] dialogLines = {
        "Investigatore: Salve, sa dirmi cosa è successo al ragazzo che è venuto qualche ora fa a comprare questi oggetti?",
        "Commesso: Mi dispiace, non posso aiutarla...",
        "Investigatore: È importante. Dica quello che sa.",
        "Commesso: Va bene... un uomo è entrato mentre suo fratello acquistava una torcia e dei guanti...",
        "Commesso: Sembrava una discussione accesa, poi ha concluso l'acquisto e se n'è andato.",
        "Investigatore (pensando): Da come lo descrive... è sicuramente Marco,  ",
        "uno scagnozzo di un’organizzazione criminale a cui avevo messo i bastoni tra le ruote in casi precedenti"
    };
    private int currentLineIndex = 0;

    public StoreView() {
        this.stage = new Stage(new FitViewport(800, 600));
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.backgroundTexture = new Texture(Gdx.files.internal("images/Store.jpeg"));
        this.dialogWindow = new DialogWindow(skin);
        this.nextButton = new TextButton("Next", skin);
        this.pauseButton = new TextButton("Pause", skin);
    }

    public void setupUI() {
        stage.clear();
        Table mainTable = new Table();
        mainTable.setFillParent(true);

        mainTable.add(new com.badlogic.gdx.scenes.scene2d.ui.Image(backgroundTexture)).expand().fill().row();
        mainTable.add(dialogWindow).expandX().fill().height(stage.getHeight() * 0.3f).row();

        stage.addActor(mainTable);
        dialogWindow.updateText(dialogLines[currentLineIndex]);

        Table overlayArea = new Table(); // crea un'area separata per il pulsante pausa e next

        // Configura il pulsante di pausa (alto a destra)
        pauseButton.setSize(100, 40);
        pauseButton.setPosition(
            stage.getViewport().getWorldWidth() - pauseButton.getWidth() - 20, // 20px di margine dal bordo destro
            stage.getViewport().getWorldHeight() - pauseButton.getHeight() - 10 // 10px di margine dal bordo superiore
        );

        // Configura il pulsante next (basso a destra, allineato orizzontalmente con pausa)
        nextButton.setSize(100, 40);
        nextButton.setPosition(
            stage.getViewport().getWorldWidth() - nextButton.getWidth() - 20, // Stesso margine orizzontale del pulsante pausa
            10 // 10px di margine dal bordo inferiore
        );

        // Aggiungi il pulsante di pausa allo stage
        overlayArea.addActor(pauseButton);
        overlayArea.addActor(nextButton);
        stage.addActor(overlayArea);
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
        Gdx.input.setInputProcessor(stage);
        setupUI();
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

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public DialogWindow getDialogWindow() {
        return dialogWindow;
    }

    public TextButton getPauseButton() {
        return pauseButton;
    }

    public TextButton getNextButton() {
        return nextButton;
    }
}
*/



