package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.model.DialogManager;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class AbandonedShelterView extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public AbandonedShelterView(GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/AbandonedShelter.jpeg"));
        this.dialogLines = new String[]{
            DialogManager.get("SHELTER1"),
            DialogManager.get("SHELTER2"),
            DialogManager.get("SHELTER3"),
            DialogManager.get("SHELTER4"),
            DialogManager.get("SHELTER5"),
            DialogManager.get("SHELTER6"),
            DialogManager.get("SHELTER7"),
            DialogManager.get("SHELTER8"),
            DialogManager.get("SHELTER9"),
            DialogManager.get("SHELTER10"),
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
        InteractiveObject debris = new SceneObject(
            "Detriti",
            "Questi detriti sembrano essersi ammucchiati qui per decenni."
        );

        InteractiveObject junk = new SceneObject(
            "Cianfrusaglie",
            "Oggetti rotti e senza valore... chi sa di chi erano..."
        );

        InteractiveObject glassShards = new SceneObject(
            "Cocci di vetro",
            "Dei frammenti di vetro, sembrano appartenere a una vecchia finestra."
        );

        InteractiveObject wallCracks = new SceneObject(
            "Pareti scrostate",
            "Le pareti sono piene di crepe. Come d'altronde tutta la casa..."
        );

        // Creazione delle aree interattive
        Actor debrisActor = controller.createInteractiveArea(debris);
        Actor junkActor = controller.createInteractiveArea(junk);
        Actor glassShardsActor = controller.createInteractiveArea(glassShards);
        Actor wallCracksActor = controller.createInteractiveArea(wallCracks);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla risoluzione dell'immagine)
        debrisActor.setPosition(
            1150f / 1600f * stage.getViewport().getWorldWidth(),
            250f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        debrisActor.setSize(
            300f / 1600f * stage.getViewport().getWorldWidth(),
            200f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        junkActor.setPosition(
            150f / 1600f * stage.getViewport().getWorldWidth(),
            220f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        junkActor.setSize(
            280f / 1600f * stage.getViewport().getWorldWidth(),
            180f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        glassShardsActor.setPosition(
            700f / 1600f * stage.getViewport().getWorldWidth(),
            100f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        glassShardsActor.setSize(
            200f / 1600f * stage.getViewport().getWorldWidth(),
            100f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        wallCracksActor.setPosition(
            900f / 1600f * stage.getViewport().getWorldWidth(),
            494f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        wallCracksActor.setSize(
            300f / 1600f * stage.getViewport().getWorldWidth(),
            420f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(debrisActor);
        interactiveLayer.addActor(junkActor);
        interactiveLayer.addActor(glassShardsActor);
        interactiveLayer.addActor(wallCracksActor);

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
