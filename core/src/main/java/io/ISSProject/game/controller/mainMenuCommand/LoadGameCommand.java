package io.ISSProject.game.controller.mainMenuCommand;

public class LoadGameCommand implements MainMenuCommand {
    private final MainMenuReceiver mainMenuReceiver;

    public LoadGameCommand (MainMenuReceiver mainMenuReceiver) {
       this.mainMenuReceiver = mainMenuReceiver;
    }

    @Override
    public void execute() {
        mainMenuReceiver.openSaveGameView();
    }
}
