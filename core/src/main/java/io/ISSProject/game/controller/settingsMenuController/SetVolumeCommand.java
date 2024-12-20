package io.ISSProject.game.controller.settingsMenuController;

public class SetVolumeCommand implements Command {
    private SettingsController settingsController;
    private int volume;

    public SetVolumeCommand(SettingsController settingsController, int volume) {
        this.settingsController = settingsController;
        this.volume = volume;
    }

    @Override
    public void execute() {
        settingsController.setVolume(volume);
    }
}
