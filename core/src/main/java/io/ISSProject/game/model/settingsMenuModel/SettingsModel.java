package io.ISSProject.game.model.settingsMenuModel;

public class SettingsModel {
    private final VideoManager videoManager = VideoManager.getInstance();
    private final AudioManager audioManager = AudioManager.getInstance();
    private final AssetManager assetManager = AssetManager.getInstance();

    public String[] getAvailableResolutions() {
        return new String[]{"1920x1080", "1280x720", "800x600"};
    }

    public String getCurrentResolution() {
        return videoManager.getResolution();
    }

    public AssetManager getAssetManager(){
        return this.assetManager;
    }

    public void setResolution(String resolution) {
        videoManager.setResolution(resolution);
    }

    public int getCurrentVolume() {
        return audioManager.getVolume();
    }

    public void setVolume(int volume) {
        audioManager.setVolume(volume);
    }

    public boolean isMuted() {
        return audioManager.isMuted();
    }

    public void toggleMute() {
        audioManager.toggleMute();
    }
}
