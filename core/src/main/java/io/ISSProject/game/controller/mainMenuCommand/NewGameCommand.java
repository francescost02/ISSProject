package io.ISSProject.game.controller.mainMenuCommand;

public class NewGameCommand implements MainMenuCommand {

    private MainMenuReceiver mainMenuReceiver;
    public NewGameCommand(MainMenuReceiver mainMenuReceiver) {
        this.mainMenuReceiver = mainMenuReceiver;
    }
    @Override
    public void execute() {
        mainMenuReceiver.createNewGame();
    }
}
