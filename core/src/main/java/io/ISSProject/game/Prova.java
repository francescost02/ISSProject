package io.ISSProject.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.*;
import io.ISSProject.game.view.DialogWindow;

public class Prova extends ApplicationAdapter {
    private Stage stage;
    private Stage gameStage;
    private Stage uiStage;
    private Skin skin;
    private Table mainTable;
    private Table gameArea;
    private DialogWindow dialogWindow;
    private Texture backgroundTexture;

    // Costanti per il layout
    private static final float DIALOG_RATIO = 0.3f;
    private static final float GAME_RATIO = 0.7f;

    @Override
    public void create() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Create separate viewports
        ExtendViewport gameViewport = new ExtendViewport(800, 600); // Fixed size for game area
        ScreenViewport uiViewport = new ScreenViewport(); // Scales with screen for UI

        stage = new Stage(new FitViewport(screenWidth, screenHeight));
        Gdx.input.setInputProcessor(stage);


        // Carica le risorse
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("images/Salotto.png"));


        dialogWindow = new DialogWindow(skin);

        // Setup del layout base
        setupLayout();
        // Aggiungi oggetti interattivi di esempio
        setupInteractiveObjects();

        stage.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Vector2 stageCoordinates = stage.screenToStageCoordinates(new Vector2(x, y));
                System.out.println("Coorinate del click nello Stage: (" +stageCoordinates.x+","+stageCoordinates.y+")");
            }
        });

        //stage.setDebugAll(true);
    }

    private void setupGameArea(){
        gameArea = new Table();
        gameArea.setFillParent(true);
        gameArea.setBackground(new TextureRegionDrawable(backgroundTexture));

        setupInteractiveObjects();

        gameStage.addActor(gameArea);
    }

    private void setupLayout() {
        // Table principale che occupa tutto lo schermo
        mainTable = new Table();
        mainTable.setFillParent(true);

        // Area di gioco (parte superiore)
        gameArea = new Table();
        gameArea.setBackground(new TextureRegionDrawable(backgroundTexture));

        updateTableLayout();

        stage.addActor(mainTable);
    }

    private void updateTableLayout() {
        // Rimuovi eventuali configurazioni precedenti
        mainTable.clear();

        float viewportHeight = stage.getViewport().getWorldHeight();
        float viewportWidth = stage.getViewport().getWorldWidth();

        // Aggiungi le componenti con proporzioni dinamiche
        mainTable.add(gameArea)
            .expand().fill()
            .height(viewportHeight * GAME_RATIO)
            .width(viewportWidth)
            .row();

        mainTable.add(dialogWindow)
            .expand().fill()
            .height(viewportHeight * DIALOG_RATIO)
            .width(viewportWidth);

        mainTable.invalidate();
        mainTable.layout();
    }

    private void setupInteractiveObjects() {
        // Crea attori trasparenti per le aree interattive
        Actor lampActor = createInteractiveArea(
            715, 103, 70, 100,
            "Una lampada vintage...",
            "Una lampada vintage...non credo mi possa aiutare nella risoluzione di questo caso."
        );

        Actor bookActor = createInteractiveArea(
            20, 35, 50, 10,
            "Un antico libro sulla magia...",
            "Un antico libro sulla magia...chissà se esiste qualche incantesimo per avere una giornata normale ogni tanto."
        );

        gameArea.addActor(lampActor);
        gameArea.addActor(bookActor);
    }

    private Actor createInteractiveArea(float x, float y, float width, float height,
                                        String tooltipText, String dialogText) {
        Actor actor = new Actor();

        actor.setBounds(x, y, width, height);
        actor.setTouchable(Touchable.enabled);

        // Aggiungi tooltip
        TextTooltip tooltip = new TextTooltip(tooltipText, skin);
        tooltip.setInstant(true);
        actor.addListener(tooltip);

        // Aggiungi listener per il click
        actor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialogWindow.updateText(dialogText);
            }
        });

        return actor;
    }

    @Override
    public void resize(int width, int height) {
        // Aggiorna il viewport
        stage.getViewport().update(width, height, true);

        System.out.println("Resized to: " + width + "x" + height);

        updateTableLayout();

    }

    @Override
    public void render() {
        // Pulisci lo schermo
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Stampa le dimensioni del viewport per debug
        System.out.println("Viewport size: " + stage.getViewport().getWorldWidth() + "x" + stage.getViewport().getWorldHeight());


        // Aggiorna e disegna lo stage
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}


