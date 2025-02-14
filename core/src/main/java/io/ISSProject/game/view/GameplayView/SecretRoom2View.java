package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.model.*;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class SecretRoom2View extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public SecretRoom2View(GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/SecretRoom2.jpg"));
        this.dialogLines = new String[]{
            DialogManager.get("SECRETROOMV2_1"),
            DialogManager.get("SECRETROOMV2_2"),
            DialogManager.get("SECRETROOMV2_3"),
            DialogManager.get("SECRETROOMV2_4"),


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
        Clue stoneAltar = new CluePaper(
            "Altare di pietra",
            "Un vecchio registro con pagine ingiallite...\n" +
                " Ci sono delle Transazioni ricorrenti verso una villa fuori città. È qui che stanno spostando il denaro adesso.",
            "- Importo: 10.000$\n" +
                "- Descrizione: Pagamento periodico\n"+
                "- Destinatario: Conto offshore\n"+
                "- Mittente: Via del Giudizio 1\n\nM"
        );

        InteractiveObject documents = new SceneObject(
            "Documenti",
            "Bonifici anonimi... Questo è il cuore del loro sistema finanziario."
        );

        // Creazione delle aree interattive
        Actor stoneAltarActor = controller.createInteractiveArea(stoneAltar);
        Actor documentsActor = controller.createInteractiveArea(documents);


        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla risoluzione dell'immagine)
        stoneAltarActor.setPosition(
            180f / 1152f * stage.getViewport().getWorldWidth(),
            256f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        stoneAltarActor.setSize(
            150f / 1152f * stage.getViewport().getWorldWidth(),
            360f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        documentsActor.setPosition(
            150f / 1152f * stage.getViewport().getWorldWidth(),
            16f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        documentsActor.setSize(
            870f / 1152f * stage.getViewport().getWorldWidth(),
            220f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(stoneAltarActor);
        interactiveLayer.addActor(documentsActor);

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
