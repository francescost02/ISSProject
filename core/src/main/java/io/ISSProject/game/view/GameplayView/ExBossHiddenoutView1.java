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
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.puzzles.PuzzleStrategy;
import io.ISSProject.game.model.puzzles.SequenceButtonPuzzle;

public class ExBossHiddenoutView1 extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public ExBossHiddenoutView1(GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/ExBossHiddenout.jpg"));
        this.dialogLines = new String[]{
            "L' edificio è davvero fatiscente",
            "Questo dovrebbe essere il posto... Ma qualcosa non torna...",
            "Finestre sporche, porte socchiuse, nessun segno di attività recente.",
            "Si avvicina cautamente alla porta e la spinge. Con un cigolio sinistro, l’ingresso si apre senza alcuna resistenza.",
            "Entra, l'interno è spoglio: solamente documenti strappati e segni di un’evacuazione frettolosa.",
            " Investigatore: Dannazione... Sapevano che sarei arrivato.",
            "Si avvicina a un tavolo polveroso, dove trova un mozzicone di sigaretta ancora tiepido.",
            "Investigatore: Non può essere passato troppo tempo… Forse posso ancora recuperarli.",
            "Stringe il pugno attorno al bigliettino e si guarda intorno, cercando il prossimo indizio.",
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
        InteractiveObject scatteredDocuments = new SceneObject(
            "Documenti strappati",
            "Ci sono fogli sparsi sul pavimento, alcuni ancora leggibili. Potrebbero contenere informazioni utili..."
        );

        InteractiveObject table = new SceneObject(
            "Tavolo",
            "Un vecchio tavolo impolverato. Qualcuno ha lavorato qui di recente?"
        );

        InteractiveObject pizzaBoxes = new SceneObject(
            "Scatoloni di pizza vuoti",
            "Resti di una cena frettolosa... Qualcuno è stato qui non molto tempo fa."
        );

        InteractiveObject armchair = new SceneObject(
            "Poltrona",
            "Una vecchia poltrona logora. Forse qualcuno ci ha passato molto tempo seduto a leggere o a riflettere."
        );

        Clue brotherDocuments = new CluePaper(
            "Documenti del fratello",
            "Riconosco la calligrafia... sono i documenti mancanti di mio fratello. Devo assolutamente leggerli.",
            "Nome del boss: Luigi Bianchi"
        );

        // Creazione delle aree interattive
        Actor scatteredDocumentsActor = controller.createInteractiveArea(scatteredDocuments);
        Actor tableActor = controller.createInteractiveArea(table);
        Actor pizzaBoxesActor = controller.createInteractiveArea(pizzaBoxes);
        Actor armchairActor = controller.createInteractiveArea(armchair);
        Actor brotherDocumentsActor = controller.createInteractiveArea(brotherDocuments);


        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla nuova risoluzione)
        scatteredDocumentsActor.setPosition(
            600f / 1152f * stage.getViewport().getWorldWidth(),
            16f / 896f * stage.getViewport().getWorldHeight()
        );
        scatteredDocumentsActor.setSize(
            500f / 1152f * stage.getViewport().getWorldWidth(),
            120f / 896f * stage.getViewport().getWorldHeight()
        );

        tableActor.setPosition(
            0f / 1152f * stage.getViewport().getWorldWidth(),
            116f / 896f * stage.getViewport().getWorldHeight()
        );
        tableActor.setSize(
            430f / 1152f * stage.getViewport().getWorldWidth(),
            190f / 896f * stage.getViewport().getWorldHeight()
        );

        pizzaBoxesActor.setPosition(
            960f / 1152f * stage.getViewport().getWorldWidth(),
            70f / 896f * stage.getViewport().getWorldHeight()
        );
        pizzaBoxesActor.setSize(
            180f / 1152f * stage.getViewport().getWorldWidth(),
            100f / 896f * stage.getViewport().getWorldHeight()
        );

        armchairActor.setPosition(
            710f / 1152f * stage.getViewport().getWorldWidth(),
            206f / 896f * stage.getViewport().getWorldHeight()
        );
        armchairActor.setSize(
            230f / 1152f * stage.getViewport().getWorldWidth(),
            150f / 896f * stage.getViewport().getWorldHeight()
        );

        brotherDocumentsActor.setPosition(
            600f / 1152f * stage.getViewport().getWorldWidth(),
            206f / 896f * stage.getViewport().getWorldHeight()
        );
        brotherDocumentsActor.setSize(
            80f / 1152f * stage.getViewport().getWorldWidth(),
            80f / 896f * stage.getViewport().getWorldHeight()
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(scatteredDocumentsActor);
        interactiveLayer.addActor(tableActor);
        interactiveLayer.addActor(pizzaBoxesActor);
        interactiveLayer.addActor(armchairActor);
        interactiveLayer.addActor(brotherDocumentsActor);

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
