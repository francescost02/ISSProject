package io.ISSProject.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ExitMenuView2 implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;

    private Dialog dialog;
    private TextButton saveAndCloseButton;
    private TextButton closeWithoutSavingButton;
    private TextButton cancelButton;

    public ExitMenuView2() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.backgroundTexture = new Texture(Gdx.files.internal("images/background-menu-tmp.jpg"));
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    public Stage getStage() {
        return stage;
    }

    public TextButton getSaveAndCloseButton() {
        return saveAndCloseButton;
    }

    public TextButton getCloseWithoutSavingButton() {
        return closeWithoutSavingButton;
    }

    public TextButton getCancelButton() {
        return cancelButton;
    }

    private void setupUI() {
        // Creazione del layout principale
        Table rootTable = new Table();
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // Creazione del dialogo personalizzato
        dialog = new Dialog(" ", skin) {
            @Override
            protected void result(Object object) {
                // Il controller gestisce le azioni, non la vista
            }
        };

        dialog.text("Sei sicuro di voler uscire dal gioco?");
        dialog.getContentTable().padTop(20); // Aggiungi spazio sopra il testo
        dialog.getContentTable().padBottom(10); // Aggiungi spazio sotto il testo

        // Creazione dei pulsanti senza listener
        saveAndCloseButton = new TextButton(" Salva e chiudi", skin);
        closeWithoutSavingButton = new TextButton(" Chiudi senza salvare", skin);
        cancelButton = new TextButton("Annulla", skin);

        // Imposta il font personalizzato per i pulsanti
        saveAndCloseButton.getLabel().setFontScale(1.5f);
        closeWithoutSavingButton.getLabel().setFontScale(1.5f);
        cancelButton.getLabel().setFontScale(1.5f);

        // Layout dei pulsanti nel dialogo
        dialog.getButtonTable().add(saveAndCloseButton).width(250).height(60).pad(10).row();
        dialog.getButtonTable().add(closeWithoutSavingButton).width(250).height(60).pad(10).row();
        dialog.getButtonTable().add(cancelButton).width(250).height(60).pad(10);

        // Mostra il dialogo
        dialog.show(stage);
    }

    @Override
    public void show() {
        // Preparazioni aggiuntive per quando la schermata viene visualizzata (se necessarie)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15F, 0.15F, 0.2F, 1.0F);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        // Centrare manualmente il dialog
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Dialog) {
                Dialog dialog = (Dialog) actor;
                dialog.setPosition(
                    (stage.getWidth() - dialog.getWidth()) / 2,
                    (stage.getHeight() - dialog.getHeight()) / 2
                );
            }
        }
    }

    @Override
    public void hide() {
        // Pulisce o resetta la schermata quando viene nascosta
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
