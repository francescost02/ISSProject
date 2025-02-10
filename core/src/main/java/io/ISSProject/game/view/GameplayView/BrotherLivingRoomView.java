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
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.puzzles.ReverseTextPuzzle;
import io.ISSProject.game.view.DialogWindow;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class BrotherLivingRoomView extends AbstractSceneView {
    private final GameplayController controller;
    private final Texture backgroundTexture;
    private final Vector2 tempCoords = new Vector2();
    private String[] dialogLines;
    private int currentLineIndex = 0;

    public BrotherLivingRoomView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/BrotherLivingRoom.jpeg"));
        this.dialogLines = new String[]{
            "Meglio tornare nell'appartamento di mio fratello...",
            "Bisogna controllare bene le altre stanze... magari mi sono fatto sfuggire qualche altro indizio.",
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
        }    }

    public void setupInteractiveObjects() {
        Table interactiveLayer = new Table();

        // Crea attori per le aree interattive (simile a prima)
        InteractiveObject lamp = new SceneObject(
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

        Clue drawer = new CluePaper(
            "Un cassetto socchiuso",
            "Forse contiene qualcosa di utile... un volantino con un indirizzo di un magazzino nella periferia della città.",
            "Grande Magazzino\nVicolo del silenzio n34\n\nM"
        );

        Clue letter = new CluePaper(
            "Una busta",
            "Sembra esserci una busta sopra il divano...",
            "Lascialo perdere,\n o finirai nei guai anche tu"
        );


        Actor lampActor = controller.createInteractiveArea(lamp);
        Actor bookActor = controller.createInteractiveArea(book);
        Actor paintingActor = controller.createInteractiveArea(painting);
        Actor drawerActor = controller.createInteractiveArea(drawer);
        Actor letterActor = controller.createInteractiveArea(letter);


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

        drawerActor.setPosition(
            100f / 1600f * stage.getViewport().getWorldWidth(),
            364f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        drawerActor.setSize(
            150f / 1600f * stage.getViewport().getWorldWidth(),
            120f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        letterActor.setPosition(
            915f / 1600f * stage.getViewport().getWorldWidth(),
            434f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );

        letterActor.setSize(
            95f / 1600f * stage.getViewport().getWorldWidth(),
            100f / 1244f * stage.getViewport().getWorldHeight() * 0.7f
        );


        interactiveLayer.addActor(lampActor);
        interactiveLayer.addActor(bookActor);
        interactiveLayer.addActor(paintingActor);
        interactiveLayer.addActor(drawerActor);
        interactiveLayer.addActor(letterActor);

        getGameArea().add(gameStack).expand().fill();
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
