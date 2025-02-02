package io.ISSProject.game.controller.settingsMenuController;

public class ToggleMuteCommand implements Command {
    private SettingsController settingsController;
    private boolean mute;

    public ToggleMuteCommand(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    @Override
    public void execute() {
        settingsController.setMute(mute);
    }
}
