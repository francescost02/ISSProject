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
import io.ISSProject.game.model.*;
import io.ISSProject.game.view.DialogWindow;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class BrotherBedroomView extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public BrotherBedroomView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/BrotherBedroom.jpg"));
        this.dialogLines = new String[]{
            DialogManager.get("BROTHERBEDROOM1"),
            DialogManager.get("BROTHERBEDROOM2"),
            DialogManager.get("BROTHERBEDROOM3"),
            DialogManager.get("BROTHERBEDROOM4")
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
        InteractiveObject bed = new SceneObject(
            "Un letto ben fatto",
            "Sembra che nessuno ci abbia dormito di recente..."
        );

        InteractiveObject wardrobe = new SceneObject(
            "Un grande armadio",
            "Potrebbe contenere qualcosa di utile..."
        );

        Clue openDrawer = new CluePaper(
            "Un cassetto aperto",
            "Qualcuno ha rovistato qui dentro. Sembra che manchino dei documenti. Inoltre c'è una ricevuta stropicciata...",
            "Ferramenta\n 12/12/1975\n11:47\n\nTorcia: 18$\n\n Guanti: 7$"
        );

        // Creazione degli attori per le aree interattive
        Actor bedActor = controller.createInteractiveArea(bed);
        Actor wardrobeActor = controller.createInteractiveArea(wardrobe);
        Actor drawerActor = controller.createInteractiveArea(openDrawer);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Posizionamento relativo degli oggetti interattivi
        bedActor.setPosition(
            580f / 1280f * stage.getViewport().getWorldWidth(),
            236f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );
        bedActor.setSize(
            470f / 1280f * stage.getViewport().getWorldWidth(),
            140f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );

        wardrobeActor.setPosition(
            150f / 800f * stage.getViewport().getWorldWidth(),
            200f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );
        wardrobeActor.setSize(
            200f / 800f * stage.getViewport().getWorldWidth(),
            300f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );

        drawerActor.setPosition(
            50f / 1280f * stage.getViewport().getWorldWidth(),
            350f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );
        drawerActor.setSize(
            100f / 1280f * stage.getViewport().getWorldWidth(),
            50f / 996f * stage.getViewport().getWorldHeight() * 0.7f
        );

        // Aggiunta degli oggetti interattivi al layer
        interactiveLayer.addActor(bedActor);
        interactiveLayer.addActor(wardrobeActor);
        interactiveLayer.addActor(drawerActor);

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
