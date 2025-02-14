package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.model.DialogManager;

public class DefeatView extends AbstractSceneView {
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;
    private GameplayController controller;
    private GameMediator mediator;

    public DefeatView(GameplayController controller, GameMediator mediator) {
        super();
        this.controller = controller;
        this.mediator = controller.getMediator();
        this.backgroundTexture = new Texture(Gdx.files.internal("images/defeat.png"));
        this.dialogLines = new String[] {
            DialogManager.get("DEFEAT1"),
            DialogManager.get("DEFEAT2"),
            DialogManager.get("DEFEAT3"),
            DialogManager.get("DEFEAT4"),

        };
    }

    public void setupUI() {
        super.setupUI();
        TextButton menuButton = new TextButton("Torna al Menu", skin);
        menuButton.setPosition(stage.getWidth()/2 - 50, stage.getHeight()/2 - 100);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //((MainGame)Gdx.app.getApplicationListener()).resetGame();
                mediator.notify(controller, "RESTART_GAME");
            }
        });
        stage.addActor(menuButton);
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
    public void setupInteractiveObjects() {
        Stack gameStack = new Stack();
        gameStack.add(new Image(backgroundTexture));
        getGameArea().add(gameStack).expand().fill();
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
