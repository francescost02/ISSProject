package io.ISSProject.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuView2 implements Screen {
    private final Stage stage;
    private final Skin skin;

    private TextButton newGameButton;
    private TextButton loadGameButton;
    private TextButton settingsButton;
    private TextButton exitButton;

    public MainMenuView2() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    public Stage getStage() {
        return stage;
    }

    public TextButton getNewGameButton() {
        return newGameButton;
    }

    public TextButton getLoadGameButton() {
        return loadGameButton;
    }

    public TextButton getSettingsButton() {
        return settingsButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    private void setupUI() {
        // Creazione del layout principale
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Creazione dei pulsanti con uno stile più moderno
        newGameButton = new TextButton("Nuova partita", skin);
        loadGameButton = new TextButton("Carica partita", skin);
        settingsButton = new TextButton("Impostazioni", skin);
        exitButton = new TextButton("Esci", skin);

        // Stile migliorato per i pulsanti
        newGameButton.getLabel().setFontScale(1.5f);
        loadGameButton.getLabel().setFontScale(1.5f);
        settingsButton.getLabel().setFontScale(1.5f);
        exitButton.getLabel().setFontScale(1.5f);

        // Layout dei pulsanti nel tavolo principale
        rootTable.center().top().padTop(100); // Posiziona i pulsanti al centro
        rootTable.add(newGameButton).width(300).height(80).pad(15).row();
        rootTable.add(loadGameButton).width(300).height(80).pad(15).row();
        rootTable.add(settingsButton).width(300).height(80).pad(15).row();
        rootTable.add(exitButton).width(300).height(80).pad(15).row();

        // Aggiungere uno sfondo personalizzato (opzionale)
        rootTable.setBackground(new TextureRegionDrawable(new Texture(Gdx.files.internal("images/background-menu-tmp.jpg"))));
        rootTable.setDebug(false);
    }

    @Override
    public void show() {
        /*Ogni volta che viene chiamato setScreen viene automaticamente chiamato show(),
         con questo codice ci assicuriamo che il gestore di input venga correttamente associato alla UI mostrata
         */
        Gdx.input.setInputProcessor(stage);

        if (stage.getActors().get(0) != null) {
            stage.getActors().get(0).setTouchable(Touchable.enabled);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15F, 0.15F, 0.2F, 1.0F);  // Sfondo del menu
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        // Pulisce o resetta la schermata quando viene nascosta
        if (stage.getActors().get(0) != null) {
            stage.getActors().get(0).setTouchable(Touchable.disabled);
        }
    }

    @Override
    public void pause() {
        // Logica per mettere in pausa la schermata, se necessario
    }

    @Override
    public void resume() {
        // Logica per riprendere la schermata, se necessario
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}


