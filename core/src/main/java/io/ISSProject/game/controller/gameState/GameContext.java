// GameContext.java
package io.ISSProject.game.controller.gameState;

import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.GameStateMemento;
import java.util.HashMap;
import java.util.Map;

public class GameContext {
    private static GameContext instance;
    private GameState currentState;
    private Scene currentScene;
    private String username; // Per memorizzare l'utente loggato
    private Scene savedScene; // Per salvare lo stato della scena
    private Map<String, Scene> sceneRegistry = new HashMap<>();
    private Map<String, Clue> clueRegistry = new HashMap<>();


    public GameContext() {
        this.currentState = new MainMenuState(this);
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

        // Aggiorna la scena solo se il nuovo stato ha una scena associata
        Scene associatedScene = newState.getAssociatedScene();
        if (associatedScene != null) {
            this.currentScene = associatedScene;
            System.out.println("Scena corrente impostata a: " + this.currentScene.getName());
        } else {
            System.out.println("Il nuovo stato non ha una scena associata. Scena corrente invariata. ");
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

    public void restoreScene(GameStateMemento memento) {
        if (memento != null) {
            // Controlla se la scena è già registrata
            Scene loadedScene = sceneRegistry.get(memento.getSceneName());

            if (loadedScene == null) {
                System.out.println("La scena '" + memento.getSceneName() + "' non è registrata. La registro ora.");
                if(memento.getSceneName().equals("Brother's Living Room")) {
                    loadedScene = new Scene(memento.getSceneName(), 1); // Creiamo una nuova scena se non esiste
                    loadedScene.setAssociatedState(new BrotherLivingRoomState(this));
                    registerScene(loadedScene);  // La registriamo nel registro
                } else {
                    //sviluppare per altre scene
                }
            }

            // Ora che la scena è registrata, la carichiamo dal registro
            loadedScene = loadSceneByName(memento.getSceneName());

            // Imposta la scena corrente nel contesto di gioco
            setCurrentScene(loadedScene);

            // Recupera gli indizi trovati dal memento e li aggiunge alla scena
            for (String clueName : memento.getFoundClues()) {
                Clue foundClue = clueRegistry.get(clueName);
                if (foundClue != null) {
                    loadedScene.addInteractiveObject(foundClue);
                    System.out.println("Indizio aggiunto alla scena: " + foundClue.getTooltipText());
                } else {
                    System.err.println("Errore: Indizio '" + clueName + "' non registrato.");
                }
            }
            // Aggiorna gli indizi trovati nella scena caricata
            loadedScene.markCluesAsFound(memento.getFoundClues());

            System.out.println("Scena del gioco ripristinata con successo.");
        } else {
            System.out.println("GameContext: Nessuna scena da ripristinare.");
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
        }
    }

    // Metodo per registrare le scene
    public void registerScene(Scene scene) {
        if (scene != null && scene.getName() != null) {
            if (sceneRegistry.containsKey(scene.getName())) {
                System.out.println("La scena '" + scene.getName() + "' è già registrata. Nessuna azione necessaria.");
                return; // Evitiamo la registrazione duplicata
            }

            sceneRegistry.put(scene.getName(), scene);
            System.out.println("Scena registrata: " + scene.getName());
            printSceneRegistry();
        } else {
            System.err.println("Errore nella registrazione della scena: nome nullo o scena nulla.");
        }
    }


    // Metodo per caricare una scena dal nome
    public Scene loadSceneByName(String sceneName) {
        if (sceneName == null || sceneName.isEmpty()) {
            System.err.println("Errore: il nome della scena è null o vuoto.");
            return null;
        }

        Scene scene = sceneRegistry.get(sceneName);
        if (scene == null) {
            System.err.println("Errore: la scena con il nome '" + sceneName + "' non è stata trovata nel registro.");
        }

        return scene;
    }


    // Metodo per ispezionare il contenuto di sceneRegistry
    public void printSceneRegistry() {
        if (sceneRegistry.isEmpty()) {
            System.out.println("Il registry delle scene è vuoto.");
        } else {
            System.out.println("Contenuto del registry delle scene:");
            for (Map.Entry<String, Scene> entry : sceneRegistry.entrySet()) {
                System.out.println("Nome scena: " + entry.getKey() + ", Scena: " + entry.getValue());
            }
        }
    }

    public void registerClue(Clue clue) {
        if (clue != null && clue.getTooltipText() != null) {
            if (clueRegistry.containsKey(clue.getTooltipText())) {
                System.out.println("L'indizio '" + clue.getTooltipText() + "' è già registrato. Nessuna azione necessaria.");
                return; // Evitiamo la registrazione duplicata
            }

            clueRegistry.put(clue.getTooltipText(), clue);
            System.out.println("Indizio registrato: " + clue.getTooltipText());
            printClueRegistry();
        } else {
            System.err.println("Errore nella registrazione dell'indizio: nome nullo o oggetto nullo.");
        }
    }

    public void printClueRegistry() {
        if (clueRegistry.isEmpty()) {
            System.out.println("Il registry degli indizi è vuoto.");
        } else {
            System.out.println("Contenuto del registry degli indizi:");
            for (Map.Entry<String, Clue> entry : clueRegistry.entrySet()) {
                System.out.println("Nome indizio: " + entry.getKey() + ", Indizio: " + entry.getValue());
            }
        }
    }
}
