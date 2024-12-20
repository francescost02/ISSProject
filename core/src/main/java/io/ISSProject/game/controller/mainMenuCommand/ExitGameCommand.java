package io.ISSProject.game.controller.mainMenuCommand;

public class ExitGameCommand implements MainMenuCommand {

    private final MainMenuReceiver mainMenuReceiver;

    public ExitGameCommand (MainMenuReceiver mainMenuReceiver) {
        this.mainMenuReceiver = mainMenuReceiver;
    }
    @Override
    public void execute() {
        mainMenuReceiver.exitGame();
    }
}
