
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

public class PauseMenuView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;

    private TextButton settingsButton;
    private TextButton exitMenuButton;
    private TextButton backButton;

    public PauseMenuView() {
        this.stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        this.backgroundTexture = new Texture(Gdx.files.internal("images/background-menu-tmp.jpg"));
        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    public Stage getStage() {
        return stage;
    }

    public TextButton getSettingsButton() {
        return settingsButton;
    }

    public TextButton getExitMenuButton() {
        return exitMenuButton;
    }
    public TextButton getBackButton() {
        return backButton;
    }

    private void setupUI() {
        Table rootTable = new Table();
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        settingsButton = new TextButton("Impostazioni", skin);
        exitMenuButton = new TextButton("Uscita", skin);
        backButton = new TextButton("Ritorna", skin);

        settingsButton.getLabel().setFontScale(1.5f);
        exitMenuButton.getLabel().setFontScale(1.5f);
        backButton.getLabel().setFontScale(1.5f);

        rootTable.add(settingsButton).width(300).height(80).pad(20).row();
        rootTable.add(exitMenuButton).width(300).height(80).pad(20).row();
        rootTable.add(backButton).width(300).height(80).pad(20);

    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15F, 0.15F, 0.2F, 1.0F);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}
