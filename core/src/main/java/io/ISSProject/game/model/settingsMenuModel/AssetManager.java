package io.ISSProject.game.model.settingsMenuModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetManager {
    private static AssetManager instance;
    private Skin skin;

    private AssetManager() {
        loadAssets();
    }

    public static AssetManager getInstance() {
        if (instance == null) {
            instance = new AssetManager();
        }
        return instance;
    }

    private void loadAssets() {
        skin = new Skin(Gdx.files.internal("assets/ui/uiskin.json"));

    }

    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        if (skin != null) {
            skin.dispose();
        }
    }
}
