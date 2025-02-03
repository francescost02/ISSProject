package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.*;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.view.DiaryUI;
import io.ISSProject.game.view.DialogWindow;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

import com.badlogic.gdx.utils.viewport.*;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.SceneObject;
import io.ISSProject.game.view.DialogWindow;

public class BrotherLivingRoomView extends ScreenAdapter {

    private final Stage stage;
    private final Skin skin;
    private Table mainTable;
    private DialogWindow dialogWindow;
    private final GameplayController controller;
    private final Texture backgroundTexture;
    private final Vector2 tempCoords = new Vector2();
    private Table gameArea;
    private Table uiOverlay;
    private TextButton pauseButton; // Pulsante di pausa
    private DiaryUI diaryWindow;
    private TextButton diaryButton;

    public BrotherLivingRoomView(GameplayController controller) {
        this.controller = controller;
        this.stage = new Stage(new FitViewport(800, 600));
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.backgroundTexture = new Texture(Gdx.files.internal("images/BrotherLivingRoom.jpeg"));
        this.dialogWindow = new DialogWindow(skin);

        this.diaryWindow = new DiaryUI(skin);
        diaryWindow.setVisible(false);
        this.pauseButton = new TextButton("Pause", skin);
        this.diaryButton = new TextButton("Diario", skin);
    }


    public DiaryUI getDiaryWindow() {
        return diaryWindow;
    }

    public void setupUI() {
        stage.clear();
        setupLayout();
        setupInteractiveObjects();
        stage.setDebugAll(true);
        //setUpDiaryButton();
        stage.addActor(diaryWindow);
    }

    private void setupLayout() {
        mainTable = new Table();
        mainTable.setFillParent(true);

        gameArea = new Table();
        mainTable.add(gameArea).expandX().fill().height(stage.getHeight() * 0.7f).row();
        mainTable.add(dialogWindow).expandX().fill().height(stage.getHeight() * 0.3f).row();

        Table buttonGroup = new Table();
        buttonGroup.setFillParent(true);
        buttonGroup.top().right();

        // Configura l'overlay (per esempio, il pulsante di pausa)
        pauseButton.setSize(100, 40); // Dimensioni del pulsante
        diaryButton.setSize(100, 40);// Posizionato in alto a destra

        buttonGroup.add(pauseButton).size(100, 40).padBottom(5).row();
        buttonGroup.add(diaryButton).size(100, 40);


        //float buttonX = stage.getViewport().getWorldWidth() - buttonGroup.getWidth() -5;
        //float buttonY = stage.getViewport().getWorldHeight() - buttonGroup.getHeight() -5;
        //buttonGroup.setPosition(buttonX, buttonY);

       stage.addActor(mainTable);
       stage.addActor(buttonGroup);
    }

    private void setupInteractiveObjects() {
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
            160f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );
/*
        envelopeActor.setPosition(
            458f / 800f * stage.getViewport().getWorldWidth(),
            213f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );
        envelopeActor.setSize(
            40f / 800f * stage.getViewport().getWorldWidth(),
            40f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );

 */

        interactiveLayer.addActor(lampActor);
        interactiveLayer.addActor(bookActor);
        interactiveLayer.addActor(paintingActor);
        //interactiveLayer.addActor(envelopeActor);

        gameArea.add(gameStack).expand().fill();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }

    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public DialogWindow getDialogWindow() {
        return dialogWindow;
    }

    public TextButton getPauseButton() {
        return pauseButton;
    }

    @Override
    public void show() {
        setupUI();
        Gdx.input.setInputProcessor(stage);

        // Listener per ottenere la posizione in cui clicco e posizionare gli attori, TEMPORANEO
        stage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Coordinate assolute del clic nello stage
                Vector2 localCoords = stageToGameAreaCoordinates(x, y);

                //Prima verifichiamo seil click è nell'area di ame
                if (isClickInGameArea(localCoords)) {
                    System.out.println("Coordinate stage:(" + x + "," + y + ")");
                    System.out.println("Coordinate locali di GameArea:(" + localCoords.x + "," + localCoords.y + ")");

                    //Conversione in coordinate relaitve, in modo che la poizione sia sempre coerente indifferentementa dalla dimensione della schermata
                    Vector2 relativeCoords = gameAreaToRelativeCoordinates(localCoords);
                    System.out.println("Relative coordinates (0-1): (" + relativeCoords.x + "," + relativeCoords.y + ")");
                }
            }
        });
    }

    private Vector2 stageToGameAreaCoordinates(float stageX, float stageY) {
        tempCoords.set(stageX, stageY);
        // Converti le coordinate dello stage in coordinate locali della gameArea
        Vector2 localCoords = gameArea.stageToLocalCoordinates(tempCoords);

        // Aggiusta le coordinate Y per tenere conto che gameArea occupa solo il 70% dell'altezza
        localCoords.y = localCoords.y / 0.7f;

        return localCoords;

    }

    private Vector2 gameAreaToRelativeCoordinates(Vector2 localCoords) {
        // Converti le coordinate locali in coordinate relative (0-1)
        return new Vector2(
            localCoords.x / gameArea.getWidth(),
            localCoords.y / gameArea.getHeight()
        );
    }

    private boolean isClickInGameArea(Vector2 localCoords) {
        return localCoords.x >= 0 && localCoords.x <= gameArea.getWidth() &&
            localCoords.y >= 0 && localCoords.y <= gameArea.getHeight();
    }

    public Table getGameArea() {
        return gameArea;
    }

    public TextButton getDiaryButton(){
        return diaryButton;
    }
}


