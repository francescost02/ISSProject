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
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.puzzles.ReverseTextPuzzle;
import io.ISSProject.game.view.DialogWindow;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class TrapDoorView extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public TrapDoorView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/TrapDoor.jpeg"));
        this.dialogLines = new String[]{
            "L’investigatore decide quindi di lasciare il rifugio e ritornare nel magazzino , sospettando che potrebbe ancora nascondere qualcosa di rilevante.",
            "Investigatore: Strano... Ho già controllato questo posto, eppure ho la sensazione che mi sia sfuggito qualcosa...",
            "Mentre scruta il pavimento, nota una botola parzialmente nascosta da alcune casse.",
            "Investigatore: E questa? Non l’avevo vista prima... Vediamo dove porta.",
            "Dannazione... è chiusa, bisogna inserire un codice per aprirla.",
            "Forse questi scarabocchi sui fogli possono essermi utili...",
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

        InteractiveObject box1 = new SceneObject(
            "Scatole",
            "Non contengono nulla di utile..."
        );

        InteractiveObject box2 = new SceneObject(
            "Scatole",
            "Non contengono nulla di utile..."
        );

        //Creazione enigmi
        PuzzleObject puzzleObject = new PuzzleObject(
            "Enigma Misterioso", // tooltipText che appare al passaggio del mouse
            "Hai trovato un enigma da risolvere!", // dialogText che appare al click
            new ReverseTextPuzzle()
        );


        // Creazione delle aree interattive
        Actor box1Actor = controller.createInteractiveArea(box1);
        Actor box2Actor = controller.createInteractiveArea(box2);
        Actor puzzleObjectActor = controller.createInteractiveArea(puzzleObject);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla risoluzione dell'immagine)
        box1Actor.setPosition(
            90f / 1600f * stage.getViewport().getWorldWidth(),
            194f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        box1Actor.setSize(
            345f / 1600f * stage.getViewport().getWorldWidth(),
            450f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        box2Actor.setPosition(
            1000f / 1600f * stage.getViewport().getWorldWidth(),
            144f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        box2Actor.setSize(
            500f / 1600f * stage.getViewport().getWorldWidth(),
            640f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Usa un approccio relativo per il posizionamento
        puzzleObjectActor.setPosition(
            640f / 1600f * stage.getViewport().getWorldWidth(),
            274f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );
        puzzleObjectActor.setSize(
            350f / 1600f * stage.getViewport().getWorldWidth(),
            110f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );


        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(box2Actor);
        interactiveLayer.addActor(box1Actor);
        interactiveLayer.addActor(puzzleObjectActor);

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
