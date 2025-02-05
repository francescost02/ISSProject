package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
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
import io.ISSProject.game.view.DialogWindow;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class BrotherLivingRoomView extends AbstractSceneView {
    //private Table mainTable;
    //private DialogWindow dialogWindow;
    private final GameplayController controller;
    private final Texture backgroundTexture;
    private final Vector2 tempCoords = new Vector2();
   // private Table gameArea;
   // private Table overlayArea;
    //private TextButton pauseButton; // Pulsante di pausa

    public BrotherLivingRoomView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/BrotherLivingRoom.jpeg"));
        //gameArea = new Table();
    }

    public void setupUI() {
        super.setupUI();
    }

    public void setupLayout() {
        super.setupLayout();
       // gameArea = new Table();
    }

    public void setupInteractiveObjects() {
        Table interactiveLayer = new Table();

        // Crea attori per le aree interattive (simile a prima)
        Clue lamp = new Clue(
            "una lampada vintage",
            "Una lampada vintage...non credo mi possa aiutare nella risoluzione di questo caso."
        );
        InteractiveObject book = new SceneObject(
            "Un antico libro sulla magia...",
            "Un antico libro sulla magia...chissà se esiste qualche incantesimo per avere una giornata normale ogni tanto."
        );

        InteractiveObject painting = new SceneObject(
            "Un quadro di un famoso artista contemporaneo",
            "Un bellissimo quadro, ma a meno che mio fratello l'abbia rubato a un gallerista, non credo mi servirà per risolvere il caso..."
        );

        InteractiveObject key = new Clue(
            "Una chiave misteriosa",
            "Questa chiave sembra importante... potrei usarla per qualcosa."
        );

        Actor lampActor = controller.createInteractiveArea(lamp);
        Actor bookActor = controller.createInteractiveArea(book);
        Actor paintingActor = controller.createInteractiveArea(painting);

        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        gameStack.add(interactiveLayer);

        // Usa un approccio relativo per il posizionamento
        lampActor.setPosition(
            599f / 800f * stage.getViewport().getWorldWidth(),
            127f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );
        lampActor.setSize(
            40f / 800f * stage.getViewport().getWorldWidth(),
            260f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );

        bookActor.setPosition(
            110f / 800f * stage.getViewport().getWorldWidth(),
            331f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );
        bookActor.setSize(
            10f / 800f * stage.getViewport().getWorldWidth(),
            50f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );

        paintingActor.setPosition(
            338f / 800f * stage.getViewport().getWorldWidth(),
            365f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );

        paintingActor.setSize(
            130f / 800f * stage.getViewport().getWorldWidth(),
            40f / 160f * stage.getViewport().getWorldHeight() * 0.7f
        );

        interactiveLayer.addActor(lampActor);
        interactiveLayer.addActor(bookActor);
        interactiveLayer.addActor(paintingActor);

        getGameArea().add(gameStack).expand().fill();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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

/*
    public Stage getStage() {
        return stage;
    }

    public DialogWindow getDialogWindow() {
        return dialogWindow;
    }

    public TextButton getPauseButton() {
        return pauseButton;
    }
     */
}
