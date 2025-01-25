package io.ISSProject.game.controller.mainMenuCommand;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;

public class MainMenuReceiver {

    private final GameContext gameContext;
    private final GameMediator mediator;

    public MainMenuReceiver(GameContext gameContext, GameMediator mediator) {
        this.gameContext = gameContext;
        this.mediator = mediator;
    }
    public void createNewGame() {
        System.out.println ("Creazione di una nuova partita...");
        //Logica per inizializzare una nuova partita
    }
    public void loadGame() {
        System.out.println("Carica partite salvate...");
        //Logica per caricare le partite salvate
    }

    public void openSettings() {
            System.out.println("Apertura menu delle impostazioni...");
        // Logica per aprire le impostazioni

        // Inizializza il controller
        SettingsController settingsController = new SettingsController(gameContext);
        settingsController.setMediator(mediator);
        Game game = (Game) Gdx.app.getApplicationListener(); // Ottieni l'oggetto Game corrente
        // Imposta lo schermo tramite il controller
        game.setScreen(settingsController.getScreen());
    }

    public void exitGame() {
        System.out.println("Uscita dal gioco...");

        // Logica per uscire dal gioco
        ExitMenuController2 exitMenuController = new ExitMenuController2(gameContext);
        exitMenuController.setMediator(mediator);
        Game game = (Game) Gdx.app.getApplicationListener(); // Ottieni l'oggetto Game corrente
        game.setScreen(exitMenuController.getScreen()); // Imposta la schermata di uscita
    }
}
