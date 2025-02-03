

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
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.view.DiaryUI;
import io.ISSProject.game.view.DialogWindow;
import io.ISSProject.game.controller.GameplayController;

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

    public BrotherLivingRoomView(GameplayController controller) {
        this.controller = controller;
        this.stage = new Stage(new FitViewport(800, 600));
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.backgroundTexture = new Texture(Gdx.files.internal("images/BrotherLivingRoom.jpeg"));
        this.dialogWindow = new DialogWindow(skin);
        diaryWindow.setVisible(false);
        this.pauseButton = new TextButton("Pause", skin);
    }


    public DiaryUI getDiaryWindow() {
        return diaryWindow;
    }

    public void setupUI() {
        stage.clear();
        setupLayout();
        setupInteractiveObjects();
        stage.setDebugAll(true);
        setUpDiaryButton();
        stage.addActor(diaryWindow);
    }

    private void setupLayout() {
        mainTable = new Table();
        mainTable.setFillParent(true);

        gameArea = new Table();
        uiOverlay = new Table();
        uiOverlay.setFillParent(true);
        uiOverlay.top().right();

        mainTable.add(gameArea).expandX().fill().height(stage.getHeight() * 0.7f).row();
        mainTable.add(dialogWindow).expandX().fill().height(stage.getHeight() * 0.3f).row();

        // Configura il pulsante pausa
        pauseButton.setSize(100, 40);
        pauseButton.setPosition(stage.getViewport().getWorldWidth() - 120, stage.getViewport().getWorldHeight() - 50);
        uiOverlay.addActor(pauseButton);

        stage.addActor(mainTable);
        stage.addActor(uiOverlay);
    }

    private void setUpDiaryButton() {
        TextButton diaryButton = new TextButton("Diario", skin);
        diaryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                controller.toggleDiary();
            }
        });
        // Aggiungi il pulsante all'overlay UI con padding
        uiOverlay.add(diaryButton).pad(10).top().right();
    }

    private void setupInteractiveObjects() {
        //Container per ottenre coordinate assolute
        Table interactiveLayer = new Table();

        // Crea attori trasparenti per le aree interattive
        InteractiveObject lampActor = new
            "Una lampada vintage...",
            "Una lampada vintage...non credo mi possa aiutare nella risoluzione di questo caso.",
            false);

        Actor bookActor = controller.createInteractiveArea(
            "Un antico libro sulla magia...",
            "Un antico libro sulla magia...chissà se esiste qualche incantesimo per avere una giornata normale ogni tanto.",
            false
        );

        Actor paintingActor = controller.createInteractiveArea(
            "Un quadro di un famoso artista contemporaneo",
            "Un bellissimo quadro, ma a meno che mio fratello l'abbia rubato non credo mi servirà per risolvere il caso...",
            false
        );

        Actor envelopeActor = controller.createInteractiveArea(
            "Una busta...",
            "Una busta! Lascita così sul diavno...sembra contenere documenti importanti, vediamo un po'...\n\"*Qualcosa scritto nel documento*\"",
            true
        );

        //Stack per sovrapporre lo sfondo e gli oggetti interattivi
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

        envelopeActor.setPosition(
            458f / 800f * stage.getViewport().getWorldWidth(),
            213f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );
        envelopeActor.setSize(
            40f / 800f * stage.getViewport().getWorldWidth(),
            40f / 600f * stage.getViewport().getWorldHeight() * 0.7f
        );

        interactiveLayer.addActor(lampActor);
        interactiveLayer.addActor(bookActor);
        interactiveLayer.addActor(paintingActor);
        interactiveLayer.addActor(envelopeActor);

        gameArea.add(gameStack).expand().fill();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        // Pulisci lo schermo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Aggiorna e disegna lo stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
        Gdx.input.setInputProcessor(stage);
        setupUI();

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

}



