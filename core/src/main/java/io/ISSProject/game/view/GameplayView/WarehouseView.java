package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.*;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.CluePaper;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.view.DialogWindow;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class WarehouseView extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public WarehouseView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/Warehouse.jpeg"));
        this.dialogLines = new String[]{
            "La stessa notte l'investigatore si dirige verso il magazzino...",
            "Sembra un luogo desolato... ma si vede una luce accesa in una stanza...",
            "Non sembra esserci nessuno... ci sono solo attrezzi sparsi e un disordine evidente...",
            "... Come se qualcuno fosse andato via di fretta.",
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
        InteractiveObject hammer = new SceneObject(
            "Un martello abbandonato",
            "Un martello... Potrebbe tornarmi utile..."
        );

        InteractiveObject sign = new SceneObject(
            "Una vecchia insegna",
            "L'insegna ha delle scritte rovinate, pobabilemente è la vecchia insegna del magazzino."
        );

        InteractiveObject box = new SceneObject(
            "Una scatola chiusa",
            "Una scatola chiusa. Chissà cosa contiene... Proviamo ad aprirla... dannazione solo cianfrusaglie."
        );

        Clue paper = new CluePaper(
            "Un foglio appeso alla parete",
            "Il foglio sembra avere qualcosa di scritto, ma la luce lo illumina solo parzialmente.",
            "Segui il denaro, \nnon fermarti\n\nM"
        );

        // Creazione delle aree interattive
        Actor hammerActor = controller.createInteractiveArea(hammer);
        Actor signActor = controller.createInteractiveArea(sign);
        Actor boxActor = controller.createInteractiveArea(box);
        Actor paperActor = controller.createInteractiveArea(paper);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla risoluzione dell'immagine)
        hammerActor.setPosition(
            330f / 1600f * stage.getViewport().getWorldWidth(),
            94f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        hammerActor.setSize(
            300f / 1600f * stage.getViewport().getWorldWidth(),
            214f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        signActor.setPosition(
            1310f / 1600f * stage.getViewport().getWorldWidth(),
            154f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        signActor.setSize(
            210f / 1600f * stage.getViewport().getWorldWidth(),
            340f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        boxActor.setPosition(
            30f / 1600f * stage.getViewport().getWorldWidth(),
            104f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        boxActor.setSize(
            260f / 1600f * stage.getViewport().getWorldWidth(),
            250f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        paperActor.setPosition(
            1050f / 1600f * stage.getViewport().getWorldWidth(),
            784f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        paperActor.setSize(
            125f / 1600f * stage.getViewport().getWorldWidth(),
            175f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(hammerActor);
        interactiveLayer.addActor(signActor);
        interactiveLayer.addActor(boxActor);
        interactiveLayer.addActor(paperActor);

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
