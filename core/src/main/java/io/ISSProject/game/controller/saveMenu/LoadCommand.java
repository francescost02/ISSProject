package io.ISSProject.game.controller.saveMenu;

import io.ISSProject.game.controller.gamePlayController.GameplayController;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.GameState;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.GameStateMemento;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import io.ISSProject.game.view.GameplayView.*;
import io.ISSProject.game.view.saveMenu.SaveGameView;

public class LoadCommand implements SaveMenuCommand {
    private final GameContext gameContext;
    private final SaveGameManager saveGameManager;
    private final SaveController saveController;
    private final GameplayController gameplayController;

    public LoadCommand(SaveController saveController, SaveGameManager saveGameManager) {
        this.gameContext = GameContext.getInstance();
        this.gameplayController = GameplayController.getInstance();
        this.saveGameManager = saveGameManager;
        this.saveController = saveController;
    }

    @Override
    public void execute() {
        SaveGameView view = (SaveGameView) saveController.getScreen();
        String selectedFile = view.getSelectedFile();

        if (selectedFile != null && !selectedFile.isEmpty()) {
            try {
                // Carica il memento dal file
                GameStateMemento gameState = saveGameManager.loadGame(gameContext.getUsername(), selectedFile);

                if (gameState != null) {
                    // Ripristina lo stato nel contesto di gioco
                    gameContext.restoreScene(gameState);

                    // Carica la scena
                    Scene scena = gameContext.loadSceneByName(gameState.getSceneName());
                    //Scene scena = gameContext.loadSceneByName("Brother's Living Room");
                    if (scena != null) {
                        // Ripristina gli indizi trovati nella scena
                        scena.markCluesAsFound(gameState.getFoundClues());

                        // Verifica se la scena è già associata al controller
                        if (gameplayController.getScreen() == null) {
                            // Crea la vista dinamicamente in base alla scena
                            AbstractSceneView sceneView;
                            switch (scena.getName()) {
                                case "Intro":
                                    sceneView = new IntroView(gameplayController);
                                    break;
                                case "Brother's Bedroom":
                                    sceneView = new BrotherBedroomView(gameplayController);
                                    break;
                                case "Brother's Living Room":
                                    sceneView = new BrotherLivingRoomView(gameplayController);
                                    break;
                                case "Ferramenta":
                                    sceneView = new StoreView(gameplayController);  // Crea una vista per "Ferramenta"
                                    break;
                                case "Warehouse":
                                    sceneView = new WarehouseView(gameplayController);
                                    break;
                                case "Call":
                                    sceneView = new CallView(gameplayController);
                                    break;
                                case "Before Abandoned Shelter":
                                    sceneView = new BeforeAbandonedShelterView(gameplayController);
                                    break;
                                case "Abandoned Shelter":
                                    sceneView = new AbandonedShelterView(gameplayController);
                                    break;
                                case "Trap Door":
                                    sceneView = new TrapDoorView(gameplayController);
                                    break;
                                case "Secret Room 1":
                                    sceneView = new SecretRoom1View(gameplayController);
                                    break;
                                case "Ex Boss' Hiddenout 1":
                                    sceneView = new ExBossHiddenoutView1(gameplayController);
                                    break;
                                case "Studio":
                                    sceneView = new StudioView(gameplayController);
                                    break;
                                case "Ex Boss' Hiddenout 2":
                                    sceneView = new ExBossHiddenoutView2(gameplayController);
                                    break;
                                case "Buttons":
                                    sceneView = new ButtonsView(gameplayController);
                                    break;
                                case "Secret Room 2":
                                    sceneView = new SecretRoom2View(gameplayController);
                                    break;
                                case "Before Boss' Hiddenout":
                                    sceneView = new BeforeBossHiddenoutView();
                                    break;
                                case "Final":
                                    sceneView = new FinalView();
                                    break;
                                // Aggiungi altri casi per altre scene, se necessario
                                default:
                                    sceneView = null; // Vista di default
                                    break;
                            }

                            // Imposta la scena nel controller
                            gameplayController.setScreen(sceneView);
                        }

                        System.out.println("GameplayController getScreen(): " + gameplayController.getScreen());

                        // Cambia la scena nel SaveController
                        saveController.switchToLoadedScene(scena);

                        // CAMBIO DI STATO: Aggiorniamo lo stato in base alla scena caricata
                        GameState newState = scena.getAssociatedState();
                        if (newState != null) {
                            gameContext.changeState(newState);
                            System.out.println("Nuovo stato impostato: " + newState.getClass().getSimpleName());
                        } else {
                            System.err.println("Errore: la scena caricata non ha uno stato associato!");
                        }
                        System.out.println("Partita caricata correttamente: " + selectedFile);
                    } else {
                        System.err.println("Errore: la scena caricata non è valida!");
                    }

                } else {
                    System.err.println("Errore: il file selezionato non contiene uno stato valido.");
                }
            } catch (Exception e) {
                System.err.println("Errore durante il caricamento del file di salvataggio: " + e.getMessage());
            }
        } else {
            System.out.println("Nessun file di salvataggio selezionato.");
        }
    }
}

