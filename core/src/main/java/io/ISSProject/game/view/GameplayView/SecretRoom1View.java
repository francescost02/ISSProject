package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.CluePaper;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class SecretRoom1View extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public SecretRoom1View(GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/SecretRoom1.jpg"));
        this.dialogLines = new String[]{
            "L'investigatore apre con cautela la botola e scende in una stanza buia e polverosa...",
            "Una lampada illumina un vecchio mobile impolverato...",
            "Investigatore: Non sembra esserci molto qui sotto... Aspetta...",
            ""
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
        Clue note = new CluePaper(
            "Bigliettino sgualcito",
            "Contiene una scritta a mano.. vediamo cosa dice: L'indirizzo del covo!",
            "Covo del boss\n\n\nVia Venezia n12\n\n\nM"
        );

        InteractiveObject lamp = new SceneObject(
            "Lamapada",
            "Una lampada illumina un vecchio mobile impolverato."
        );

        InteractiveObject box = new SceneObject(
            "Scatole",
            "Non contengono nulla di utile..."
        );

        InteractiveObject debris = new SceneObject(
            "Detriti",
            "La stanza è piena di detriti, il tetto infatti è pieno di crepe... potrebbe corllare da un momento all'altro."
        );

        // Creazione delle aree interattive
        Actor noteActor = controller.createInteractiveArea(note);
        Actor lampActor = controller.createInteractiveArea(lamp);
        Actor boxActor = controller.createInteractiveArea(box);
        Actor debrisActor = controller.createInteractiveArea(debris);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla risoluzione dell'immagine)
        noteActor.setPosition(
            915f / 1600f * stage.getViewport().getWorldWidth(),
            464f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        noteActor.setSize(
            45f / 1600f * stage.getViewport().getWorldWidth(),
            40f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        lampActor.setPosition(
            800f / 1600f * stage.getViewport().getWorldWidth(),
            464f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        lampActor.setSize(
            70f / 1600f * stage.getViewport().getWorldWidth(),
            155f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        boxActor.setPosition(
            200f / 1600f * stage.getViewport().getWorldWidth(),
            274f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        boxActor.setSize(
            190f / 1600f * stage.getViewport().getWorldWidth(),
            400f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        debrisActor.setPosition(
            370f / 1600f * stage.getViewport().getWorldWidth(),
            64f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        debrisActor.setSize(
            890f / 1600f * stage.getViewport().getWorldWidth(),
            200f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(noteActor);
        interactiveLayer.addActor(boxActor);
        interactiveLayer.addActor(lampActor);
        interactiveLayer.addActor(debrisActor);

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
