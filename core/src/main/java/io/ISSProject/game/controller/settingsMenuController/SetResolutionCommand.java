package io.ISSProject.game.controller.settingsMenuController;

public class SetResolutionCommand implements Command {
    private SettingsController settingsController;
    private String resolution;

    public SetResolutionCommand(SettingsController settingsController, String resolution) {
        this.settingsController = settingsController;
        this.resolution = resolution;
    }

    @Override
    public void execute() {
        settingsController.setResolution(resolution);
    }
}
