package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
    import com.badlogic.gdx.utils.viewport.FitViewport;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.DialogManager;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.view.DialogWindow;

public class FinalView extends AbstractSceneView {
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;
    private GameplayController controller;

    public FinalView(GameplayController controller) {
        super();
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/final.png"));
        this.dialogLines = new String[] {
            DialogManager.get("BOSS1"),
            DialogManager.get("BOSS2"),
            DialogManager.get("BOSS3"),
            DialogManager.get("BOSS4"),
            DialogManager.get("BOSS5"),
            DialogManager.get("BOSS6"),
            DialogManager.get("BOSS7"),
            DialogManager.get("BOSS8"),
            DialogManager.get("BOSS9"),
            DialogManager.get("BOSS10"),
        };
    }

    public void setupInteractiveObjects() {
        Table interactiveLayer = new Table();

        // Creazione degli oggetti interattivi
        InteractiveObject boss = new SceneObject(
            "Boss",
            "Scelta finale"
        );

        Actor bossActor = controller.createInteractiveArea(boss);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla nuova risoluzione)
        bossActor.setPosition(
            250f / 1152f * stage.getViewport().getWorldWidth(),
            80f / 896f * stage.getViewport().getWorldHeight()
        );
        bossActor.setSize(
            450f / 1152f * stage.getViewport().getWorldWidth(),
            400f / 896f * stage.getViewport().getWorldHeight()
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(bossActor);

        getGameArea().add(gameStack).expand().fill();

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
