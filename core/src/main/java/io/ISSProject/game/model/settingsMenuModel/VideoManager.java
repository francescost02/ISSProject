package io.ISSProject.game.model.settingsMenuModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class VideoManager {
    private static VideoManager instance;
    private String resolution = "1920x1080"; // Risoluzione di default

    public VideoManager() {}

    public static VideoManager getInstance() {
        if (instance == null) {
            instance = new VideoManager();
        }
        return instance;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
        applyResolution();
    }

    public String getResolution() {
        return resolution;
    }

    private void applyResolution() {
        String[] dimensions = resolution.split("x");
        if (dimensions.length == 2) {
            int width = Integer.parseInt(dimensions[0]);
            int height = Integer.parseInt(dimensions[1]);

            if(width == 1920 && height == 1080){
                Graphics.DisplayMode displayMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
                Gdx.graphics.setFullscreenMode(displayMode);
            }
            else{
                Gdx.graphics.setWindowedMode(width, height);
            }

        }
    }
}
