package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;

public class ButtonsView extends AbstractSceneView {
    private final GameplayController controller;
    private final Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;


    public ButtonsView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/buttons.jpg"));
        // Inizializzazione sicura dell'array dialoghi
        this.dialogLines = new String[] {
            "L'investigatore inizia a esaminare i volumi.",
            "Alcuni sembrano più usurati di altri. Ne sposta uno, poi un altro...",
            "Dietro alcuni libri trova 3 pulsanti...",
            "Investigatore: Tre pulsanti... Quindi c’è una combinazione.",
            "Non posso premerli a caso, ci sarà un ordine preciso.",
            ""
        };
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

    public void setupInteractiveObjects() {
        Table interactiveLayer = new Table();

        // Creazione degli oggetti interattivi
        InteractiveObject button1 = new SceneObject(
            "Primo pulsante",
            ""
        );

        InteractiveObject button2 = new SceneObject(
            "Secondo pulsante",
            ""
        );

        InteractiveObject button3 = new SceneObject(
            "Terzo pulsante",
            ""
        );


        // Creazione delle aree interattive
        Actor button1Actor = controller.createInteractiveArea(button1);
        Actor button2Actor = controller.createInteractiveArea(button2);
        Actor button3Actor = controller.createInteractiveArea(button3);


        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla risoluzione dell'immagine)
        button1Actor.setPosition(
            400f / 1152f * stage.getViewport().getWorldWidth(),
            446f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        button1Actor.setSize(
            160f / 1152f * stage.getViewport().getWorldWidth(),
            210f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        button2Actor.setPosition(
            610f / 1152f * stage.getViewport().getWorldWidth(),
            440f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        button2Actor.setSize(
            150f / 1152f * stage.getViewport().getWorldWidth(),
            220f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        button3Actor.setPosition(
            800f / 1152f * stage.getViewport().getWorldWidth(),
            446f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );
        button3Actor.setSize(
            160f / 1152f * stage.getViewport().getWorldWidth(),
            210f / 896f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(button1Actor);
        interactiveLayer.addActor(button2Actor);
        interactiveLayer.addActor(button3Actor);

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

