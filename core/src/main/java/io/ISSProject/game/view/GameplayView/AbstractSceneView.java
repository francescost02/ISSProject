package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.ISSProject.game.view.DialogWindow;

public abstract class AbstractSceneView extends ScreenAdapter {
    protected Stage stage;
    protected Skin skin;
    protected Table overlayArea;
    private Table mainTable;
    private Table gameArea;
    protected TextButton pauseButton; // Pulsante di pausa
    private TextButton nextButton;
    protected DialogWindow dialogWindow;

    public AbstractSceneView() {
        this.stage = new Stage(new FitViewport(800, 600));
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.overlayArea = new Table();
        this.dialogWindow = new DialogWindow(skin);
        setupCommonUI();
        setupLayout();
    }


    private void setupCommonUI() {
        // Inizializzazione del pulsante di pausa
        pauseButton = new TextButton("Pause", skin);
        nextButton = new TextButton("Next", skin);
        gameArea = new Table();
        mainTable = new Table();
    }


    public void setupUI() {
        stage.clear();
        setupLayout();
        setupInteractiveObjects();
    }
    public void setupLayout() {
        mainTable = new Table();
        mainTable.setFillParent(true);

        gameArea = new Table();
        mainTable.add(gameArea).expandX().fill().height(stage.getHeight() * 0.7f).row();
        mainTable.add(dialogWindow).expandX().fill().height(stage.getHeight() * 0.3f).row();

        overlayArea = new Table(); // crea un'area separata per il pulsante pausa
        stage.addActor(mainTable);

        // Configura l'overlay (per esempio, il pulsante di pausa)
        pauseButton.setSize(100, 40); // Dimensioni del pulsante
        pauseButton.setPosition(
            stage.getViewport().getWorldWidth() - 120, stage.getViewport().getWorldHeight() - 50); // Posizionato in alto a destra


        // Configura il pulsante next (basso a destra, allineato orizzontalmente con pausa)
        nextButton.setSize(100, 40);
        nextButton.setPosition(
            stage.getViewport().getWorldWidth() - 120,  nextButton.getWidth() - 50);

        // Aggiungi il pulsante di pausa allo stage
        overlayArea.addActor(pauseButton);
        overlayArea.addActor(nextButton);
        stage.addActor(overlayArea);
    }

    public Table getGameArea() {
        return gameArea;
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }
    public TextButton getPauseButton() {
        return pauseButton;
    }
    public TextButton getNextButton() {
        return nextButton;
    }

    public DialogWindow getDialogWindow() {
        return dialogWindow;
    }

    public Stage getStage() {
        return stage;
    }
    public Skin getSkin() {
        return skin;
    }

    //public abstract void setupUI();
   // public abstract void setupLayout();
    public abstract void setupInteractiveObjects();
    public abstract void render(float delta);
    //public abstract void show();
    public abstract void resize(int width, int height);
    public abstract void dispose();
}
