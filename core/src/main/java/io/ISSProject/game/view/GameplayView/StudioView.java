package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import io.ISSProject.game.model.Clue;
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
            "L'investigatore ritorna nel suo studio... La scrivania è piena di documenti e appunti.",
            "Investigatore: Dannazione… sono bloccato. Ogni pista sembra portare a un vicolo cieco.",
            "Prende il biglietto con scritto \"Segui il denaro, non fermarti.\" da un fascicolo e lo osserva attentamente.",
            "Investigatore tra sé e sé, con tono determinato: Forse è proprio questa la chiave...",
            "Se traccio i movimenti di denaro dell’organizzazione, posso arrivare al boss senza rischiare uno scontro diretto.",
            "Ecco... questi movimenti non tornano. Ci sono società di copertura... trasporti, magazzini...",
            "Tutto sembra lecito, ma il denaro passa sempre attraverso le stesse attività.",
            "Mentre continua ad analizzare i dati... drin... drin... drin...",
            "Il telefono sulla scrivania squilla all’improvviso, interrompendo il silenzio della stanza.",
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
        Clue phone = new Clue(
            "Telefono",
            "Voce robotica: Hai poco tempo. Se vuoi arrivare in fondo, smetti di cercare e agisci. " +
                "Come immaginavi, il covo è stato spostato. Non so dove sia ora... ma c’è qualcos’altro che dovresti controllare. " +
                "Torna all’ex covo. C’è una stanza segreta che merita più attenzione.... Click",
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
