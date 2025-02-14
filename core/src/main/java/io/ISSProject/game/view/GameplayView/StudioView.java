package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.DialogManager;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class StudioView extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public StudioView(GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/studio.jpg"));
        this.dialogLines = new String[]{
            DialogManager.get("STUDIO1"),
            DialogManager.get("STUDIO2"),
            DialogManager.get("STUDIO3"),
            DialogManager.get("STUDIO4"),
            DialogManager.get("STUDIO5"),
            DialogManager.get("STUDIO6"),
            DialogManager.get("STUDIO7"),
            DialogManager.get("STUDIO8"),
            DialogManager.get("STUDIO9"),
        };
    }

    private void advanceDialog() {
        if (currentLineIndex < dialogLines.length - 1) {
            currentLineIndex++;
            dialogWindow.updateText(dialogLines[currentLineIndex]);
        }
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

    public void setupInteractiveObjects() {
        Table interactiveLayer = new Table();

        // Creazione degli oggetti interattivi
        Clue phone = new Clue(
            "Telefono",
            DialogManager.get("CLUEPHONE"),
            ""
        );

        InteractiveObject documents = new SceneObject(
            "Pila di documenti",
            "Un mucchio di documenti accatastati. Contengono tutti i vecchi casi..."
        );

        InteractiveObject window = new SceneObject(
            "Finestra",
            "L'investigatore si affaccia alla finestra riflettendo su cosa fare."
        );

        // Creazione delle aree interattive
        Actor phoneActor = controller.createInteractiveArea(phone);
        Actor documentsActor = controller.createInteractiveArea(documents);
        Actor windowActor = controller.createInteractiveArea(window);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi
        phoneActor.setPosition(
            330f / 1152f * stage.getViewport().getWorldWidth(),
            416f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        phoneActor.setSize(
            80f / 1152f * stage.getViewport().getWorldWidth(),
            60f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        documentsActor.setPosition(
            910f / 1152f * stage.getViewport().getWorldWidth(),
            116f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        documentsActor.setSize(
            242f / 1152f * stage.getViewport().getWorldWidth(),
            270f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        windowActor.setPosition(
            600f / 1152f * stage.getViewport().getWorldWidth(),
            446f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        windowActor.setSize(
            350f / 1152f * stage.getViewport().getWorldWidth(),
            400f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(phoneActor);
        interactiveLayer.addActor(documentsActor);
        interactiveLayer.addActor(windowActor);

        getGameArea().add(gameStack).expand().fill();
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
