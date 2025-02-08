
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

public class StoreView extends AbstractSceneView {
    private final GameplayController controller;
    private final Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;


    public StoreView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/Store.jpeg"));
        // Inizializzazione sicura dell'array dialoghi
        this.dialogLines = new String[] {
            "Investigatore: Salve, sa dirmi cosa è successo al ragazzo che è venuto qualche ora fa a comprare questi oggetti?",
            "Commesso: Mi dispiace, non posso aiutarla...",
            "Investigatore: È importante. Dica quello che sa.",
            "Commesso: Va bene... un uomo è entrato mentre suo fratello acquistava una torcia e dei guanti...",
            "Commesso: Sembrava una discussione accesa, poi ha concluso l'acquisto e se n'è andato.",
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
        InteractiveObject workbench = new SceneObject(
            "Un banco da lavoro",
            "Il banco è pieno di strumenti e parti meccaniche."
        );

        InteractiveObject seller = new SceneObject(
            "Il commesso",
            "Un uomo dall'aria stanca, ma attento. Potrebbe sapere qualcosa..."
        );

        InteractiveObject tools1 = new SceneObject(
            "Una parete di attrezzi",
            "Chiavi inglesi, martelli, pinze... tutto quello che serve per riparare qualcosa."
        );

        InteractiveObject tools2 = new SceneObject(
            "Una parete di attrezzi",
            "Chiavi inglesi, martelli, pinze... tutto quello che serve per riparare qualcosa."
        );

        // Creazione delle aree interattive
        Actor workbenchActor = controller.createInteractiveArea(workbench);
        Actor sellerActor = controller.createInteractiveArea(seller);
        Actor tools1Actor = controller.createInteractiveArea(tools1);
        Actor tools2Actor = controller.createInteractiveArea(tools2);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento degli oggetti interattivi (calcolato rispetto alla risoluzione dell'immagine)
        workbenchActor.setPosition(
            0f / 1280f * stage.getViewport().getWorldWidth(),
            96f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );
        workbenchActor.setSize(
            1.0f * stage.getViewport().getWorldWidth(),
            240f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );

        sellerActor.setPosition(
            580f / 1280f * stage.getViewport().getWorldWidth(),
            336f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );
        sellerActor.setSize(
            170f / 1280f * stage.getViewport().getWorldWidth(),
            200f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );

        tools1Actor.setPosition(
            150f / 1280f * stage.getViewport().getWorldWidth(),
            336f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );
        tools1Actor.setSize(
            410f / 1280f * stage.getViewport().getWorldWidth(),
            330f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );

        tools2Actor.setPosition(
            760f / 1280f * stage.getViewport().getWorldWidth(),
            336f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );
        tools2Actor.setSize(
            1070f / 1280f * stage.getViewport().getWorldWidth(),
            330f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti alla scena
        interactiveLayer.addActor(workbenchActor);
        interactiveLayer.addActor(sellerActor);
        interactiveLayer.addActor(tools1Actor);
        interactiveLayer.addActor(tools2Actor);

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

