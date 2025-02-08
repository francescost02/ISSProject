
package io.ISSProject.game.view.GameplayView;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import io.ISSProject.game.controller.gamePlayController.GameplayController;

public class CallView extends AbstractSceneView {
    private GameplayController controller;
    private Texture backgroundTexture;
    private String[] dialogLines;
    private int currentLineIndex = 0;


    public CallView (GameplayController controller) {
        super(); // Chiama il costruttore della superclasse
        this.controller = controller;
        this.backgroundTexture = new Texture(Gdx.files.internal("images/call.png"));
        // Inizializzazione sicura dell'array dialoghi
        this.dialogLines = new String[]{
            "Drin... drin.. drin... drin...",
            "Investigatore: Il telefono sta squillando, chi mi chiama a quest'ora...",
            "Osserva lo schermo: numero anonimo...",
            "Esita un momento, un pò confuso e sospettoso, poi risponde...",
            "Voce misteriosa: So cosa stai cercando...",
            "Investigatore: ... chi sei?",
            "Voce misteriosa: Vuoi salvare tuo fratello, vero?",
            "Le dita dell’investigatore si stringono attorno al telefono e urla: DIMMI DOVE SI TROVA",
            "Voce misteriosa: Vai in questo indirizzo... Troverai quello che cerchi.",
            "L’investigatore afferra rapidamente un taccuino e annota l’indirizzo ed esclama: Perché dovrei fidarmi?",
            "Una breve pausa, poi un sussurro quasi ironico: Perché non hai altra scelta.",
            "La chiamata si interrompe. Il battito dell’investigatore accelera. Resta un attimo immobile, scrutando il telefono, come se potesse dargli altre risposte.",
            "Con un sospiro, si alza. Infila il taccuino nel cappotto e si prepara.",
            "Afferra il martello... se è una trappola, vuole essere pronto.",
            "Ma se c’è anche solo una possibilità di trovare suo fratello, deve andare.",
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

