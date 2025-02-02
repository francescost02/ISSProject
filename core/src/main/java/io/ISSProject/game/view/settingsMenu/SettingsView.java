package io.ISSProject.game.view.settingsMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsView implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture;
    private SelectBox<String> resolutionSelectBox;
    private Slider audioSlider;
    private CheckBox muteCheckBox;
    private TextButton backButton;

    public SettingsView() {
        stage = new Stage(new ScreenViewport());
        this.skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        backgroundTexture = new Texture(Gdx.files.internal("images/background-menu-tmp.jpg"));

        Gdx.input.setInputProcessor(stage);
        setupUI();
    }

    public TextButton getTextButton() {
        return backButton;
    }


    private void setupUI() {
        // Creazione del layout principale
        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        stage.addActor(rootTable);

        // Label "Settings"
        Label titleLabel = new Label("Settings", skin);
        titleLabel.setFontScale(2.5f);
        titleLabel.setColor(Color.WHITE);
        rootTable.add(titleLabel).pad(20).row();

        // SelectBox per la risoluzione
        resolutionSelectBox = new SelectBox<>(skin);
        resolutionSelectBox.getStyle().listStyle.font.getData().setScale(1.5f);
        rootTable.add(createLabeledWidget("Resolution:", resolutionSelectBox)).pad(10).row();

        // Slider per il volume
        audioSlider = new Slider(0, 100, 1, false, skin);
        rootTable.add(createLabeledWidget("Audio Volume:", audioSlider)).pad(10).row();

        // CheckBox per il mute
        muteCheckBox = new CheckBox("Mute", skin);
        muteCheckBox.getLabel().setFontScale(1.5f);
        rootTable.add(muteCheckBox).pad(10).row();

        // Aggiungere il pulsante indietro
        TextButton backButton = new TextButton("Indietro", skin);
        backButton.getLabel().setFontScale(1.5f);
        rootTable.add(backButton).pad(20).expandX().bottom();

        // Memorizzarlo come variabile membro
        this.backButton = backButton;

    }

    private Table createLabeledWidget(String labelText, Actor widget) {
        Table table = new Table();
        Label label = new Label(labelText, skin);
        label.setFontScale(1.5f);
        label.setColor(Color.WHITE);
        table.add(label).padRight(20);
        table.add(widget).expandX().fillX();
        return table;
    }

    public void setAvailableResolutions(String[] resolutions) {
        resolutionSelectBox.setItems(resolutions);
    }

    public void setCurrentResolution(String resolution) {
        resolutionSelectBox.setSelected(resolution);
    }

    public void setCurrentVolume(int volume) {
        audioSlider.setValue(volume);
    }

    public void setMuteState(boolean isMuted) {
        muteCheckBox.setChecked(isMuted);
    }

    public Stage getStage() {
        return stage;
    }

    public SelectBox<String> getResolutionSelectBox() {
        return resolutionSelectBox;
    }

    public Slider getAudioSlider() {
        return audioSlider;
    }

    public CheckBox getMuteCheckBox() {
        return muteCheckBox;
    }

    // Getter per il pulsante indietro
    public TextButton getBackButton() {
        return backButton;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }
}
