package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.controller.GameInitializer;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.InteractiveObject;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.saveModel.GameStateMemento;

import java.util.HashMap;
import java.util.Map;

public class GameContext {
    private static GameContext instance;
    private GameState currentState;
    private Scene currentScene;
    private String username; // Per memorizzare l'utente loggato
    private Scene savedScene; // Per salvare lo stato della scena
    private Map<String, Clue> clueRegistry = new HashMap<>(); //indizi
    private Map<String, Scene> sceneRegistry = new HashMap<>(); //scene disponibili


    public GameContext() {
        GameInitializer.initializeGame(this);
        this.currentState = new MainMenuState(this);  // Stato iniziale
    }

    public static synchronized GameContext getInstance() {
        if (instance == null) {
            instance = new GameContext();
            System.out.println("GameContext: Istanza creata.");
        }
        return instance;
    }

    public void changeState(GameState newState) {
        if (currentState != null) {
            currentState.exit();
        }
        this.currentState = newState;

        // Imposta la scena corrente in base allo stato
        Scene associatedScene = newState.getAssociatedScene();
        if (associatedScene != null) {
            setCurrentScene(associatedScene);
            System.out.println("Scena corrente impostata a: " + associatedScene.getName());
        } else {
            System.out.println("Il nuovo stato non ha una scena associata. Scena corrente invariata.");
        }
        System.out.println("Stato corrente: " + this.currentState.getClass().getSimpleName());
    }

    public void exit() {
        if (currentState != null) {
            currentState.exit();
        }
    }

    public GameState getCurrentState() {
        return currentState;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
        System.out.println("Scena corrente aggiornata: " + (scene != null ? scene.getName() : "null"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            System.out.println("GameContext: Username non valido.");
        } else {
            this.username = username;
            System.out.println("GameContext: Username impostato a " + this.username);
        }
    }

    public void saveCurrentScene() {
        if (this.currentScene == null) {
            System.err.println("ERRORE: currentScene è null!");
        } else {
            System.out.println("Scena corrente salvata: " + this.currentScene.getName());
        }
        this.savedScene = this.currentScene;
    }

    public void restorePreviousState() {
        if (this.savedScene != null) {
            this.currentScene = this.savedScene;
            System.out.println("Stato precedente ripristinato. Scena: " + this.savedScene.getName());
        }
    }

    // Gestione degli indizi
    public void registerClue(Clue clue) {
        if (clue != null && clue.getTooltipText() != null) {
            if (clueRegistry.containsKey(clue.getTooltipText())) {
                System.out.println("L'indizio '" + clue.getTooltipText() + "' è già registrato. Nessuna azione necessaria.");
                return; // Evitiamo la registrazione duplicata
            }

            clueRegistry.put(clue.getTooltipText(), clue);
            System.out.println("Indizio registrato: " + clue.getTooltipText());
        } else {
            System.err.println("Errore nella registrazione dell'indizio: nome nullo o oggetto nullo.");
        }
    }


    // Salvataggio e ripristino dello stato
    public void restoreScene(GameStateMemento memento) {
        if (memento != null) {
            String savedSceneName = memento.getSceneName();
            System.out.println(savedSceneName);
            System.out.println("Caricando la scena: " + savedSceneName);

            // Ripristina la scena dal nome salvato
            Scene restoredScene = loadSceneByName(savedSceneName);

            if (restoredScene != null) {
                setCurrentScene(restoredScene);
                System.out.println("Scena ripristinata: " + restoredScene.getName());
            } else {
                System.err.println("Errore: scena '" + savedSceneName + "' non trovata.");
            }

            //  Riassegna lo stato alla scena se manca
            if (restoredScene.getAssociatedState() == null) {
                System.out.println("Lo stato della scena e' nullo, lo ripristino...");
                restoredScene.setAssociatedState(new GameplayState(this, restoredScene));
            }

            // Aggiungi gli indizi trovati
            for (String clueName : memento.getFoundClues()) {
                Clue foundClue = clueRegistry.get(clueName);
                if (foundClue != null) {
                    currentScene.addInteractiveObject(foundClue);
                    System.out.println("Indizio aggiunto alla scena: " + foundClue.getTooltipText());
                }
            }

            // Ripristina lo stato dei puzzle completati
            if (memento.getCompletedPuzzles() != null) {
                for (InteractiveObject obj : currentScene.getInteractiveObjects()) {
                    if (obj instanceof PuzzleObject puzzleObj) {
                        if (memento.getCompletedPuzzles().contains(puzzleObj.getTooltipText())) {
                            puzzleObj.setPuzzleCompleted(true);
                            System.out.println("Puzzle ripristinato come completato: " + puzzleObj.getTooltipText());
                        }
                    }
                }

                //**Assicuriamoci che gli indizi siano registrati PRIMA di aggiungerli alla scena**
                for (String clueName : memento.getFoundClues()) {
                    if (!clueRegistry.containsKey(clueName)) {
                        System.out.println("Registrazione dell'indizio mancante: " + clueName);
                        Clue newClue = new Clue(clueName, "Testo segnaposto per " + clueName, "ciao");
                        registerClue(newClue);
                    }
                }

                // Recupera gli indizi trovati dal memento e li aggiunge alla scena
                for (String clueName : memento.getFoundClues()) {
                    Clue foundClue = clueRegistry.get(clueName);
                    if (foundClue != null) {
                        currentScene.addInteractiveObject(foundClue);
                        System.out.println("Indizio aggiunto alla scena: " + foundClue.getTooltipText());
                    } else {
                        System.err.println("Errore: Indizio '" + clueName + "' non registrato.");
                    }
                }
                // Aggiorna gli indizi trovati nella scena caricata
                currentScene.markCluesAsFound(memento.getFoundClues());
                System.out.println("Scena del gioco ripristinata con successo.");
            } else {
                System.out.println("Nessuna scena da ripristinare.");
            }
        }
    }


    public Scene loadSceneByName(String sceneName) {
        if (sceneName == null || sceneName.isEmpty()) {
            System.err.println("Errore: nome della scena nullo o vuoto.");
            return null;
        }

        // Cerca la scena nella mappa delle scene
        Scene scene = sceneRegistry.get(sceneName);
        if (scene != null) {
            return scene;
        }

        System.err.println("Errore: scena con nome '" + sceneName + "' non trovata.");
        return null;
    }


    // Metodo per aggiungere una scena alla mappa
    public void addScene(Scene scene) {
        if (scene != null && !sceneRegistry.containsKey(scene.getName())) {
            sceneRegistry.put(scene.getName(), scene);
            System.out.println("Scena '" + scene.getName() + "' aggiunta.");
        }
    }
}
