package io.ISSProject.game.controller.mainMenuCommand;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import io.ISSProject.game.controller.exitMenuStrategy.ExitMenuController2;
import io.ISSProject.game.controller.gameState.GameplayState;
import io.ISSProject.game.controller.mediator.GameMediator;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.saveMenu.SaveController;
import io.ISSProject.game.controller.settingsMenuController.SettingsController;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.model.userManagment.UserManager;

import java.util.List;

public class MainMenuReceiver {

    private final GameContext gameContext;
    private SaveGameManager saveGameManager;
    private List<Clue> clues;

    public MainMenuReceiver(GameMediator mediator) {
        this.gameContext = GameContext.getInstance();
        //this.gameContext.setUsername(UserManager.getInstance().getCurrentUser());
        this.saveGameManager = new SaveGameManager();
    }
    public void createNewGame() {
        System.out.println ("Creazione di una nuova partita...");
        //Logica per inizializzare una nuova partita
        String username = gameContext.getUsername(); // Recupera l'username del giocatore
        if (username == null || username.isEmpty()) {
            System.out.println("Errore: nessun utente loggato.");
            return;
        }
        // Genera un nome file univoco per il salvataggio
        String saveFileName = saveGameManager.generateFileName(username);
        //saveGameManager.saveGame(username, saveFileName, );
        System.out.println("Nuova partita salvata con il nome file: " + saveFileName);
        Scene initialScene = gameContext.getCurrentScene();
    }
    public void loadGame() {
        System.out.println("Sto caricando la schermata di caricamento...");
        /*
        String username = gameContext.getUsername();
        if (username == null || username.isEmpty()) {
            System.out.println("Errore: nessun utente loggato.");
            return;
        }
        System.out.println("Sto caricando la schermata di caricamento...");
        SaveController saveController = new SaveController(username);
        saveController.setMediator(mediator);
        Game game = (Game) Gdx.app.getApplicationListener();
        game.setScreen(saveController.getScreen());
        */
    }

    public void openSettings() {
            System.out.println("Apertura menu delle impostazioni...");
        // Logica per aprire le impostazioni

        /*
        // Inizializza il controller
        SettingsController settingsController = new SettingsController(gameContext);
        settingsController.setMediator(mediator);
        Game game = (Game) Gdx.app.getApplicationListener(); // Ottieni l'oggetto Game corrente
        // Imposta lo schermo tramite il controller
        game.setScreen(settingsController.getScreen());
        */
    }

    public void exitGame() {
        System.out.println("Uscita dal gioco...");
        /*
        // Logica per uscire dal gioco
        ExitMenuController2 exitMenuController = new ExitMenuController2(gameContext);
        exitMenuController.setMediator(mediator);
        Game game = (Game) Gdx.app.getApplicationListener(); // Ottieni l'oggetto Game corrente
        game.setScreen(exitMenuController.getScreen()); // Imposta la schermata di uscita
        */
    }
}
