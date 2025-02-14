package io.ISSProject.game.controller.mainMenuCommand;

public class SettingsCommand implements MainMenuCommand {

    private final MainMenuReceiver mainMenuReceiver;

    public SettingsCommand (MainMenuReceiver mainMenuReceiver) {
        this.mainMenuReceiver = mainMenuReceiver;
    }
    @Override
    public void execute() {
        mainMenuReceiver.openSettings();
    }
}
