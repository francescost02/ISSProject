package io.ISSProject.game.controller;

import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.GameplayState;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Puzzles.PuzzleObject;
import io.ISSProject.game.model.Puzzles.ReverseTextPuzzle;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.SceneObject;

public class GameInitializer {
    public static void initializeGame(GameContext gameContext) {
        // Crea scene
        Scene scene1 = new Scene("Brother's Living Room", 1);
        Scene scene2 = new Scene("Ferramenta", 0);
        Scene scene3 = new Scene("Giardino", 2);

        scene1.setAssociatedState(new GameplayState(gameContext, scene1));
        scene2.setAssociatedState(new GameplayState(gameContext, scene2));

        gameContext.setCurrentScene(scene1);

        // Imposta la sequenza delle scene
        scene1.setNextScene(scene2);
        scene2.setNextScene(scene3);

        // Aggiungi tutte le scene al GameContext
        gameContext.addScene(scene1);
        gameContext.addScene(scene2);
        gameContext.addScene(scene3);

        // Crea e registra gli indizi
        Clue lamp = new Clue(
            "Una lampada vintage", "Una lampada vintage...non credo mi possa aiutare nella risoluzione di questo caso.",
            100f, 150f,
            40f, 120f
        );

        scene1.addInteractiveObject(lamp);
        gameContext.registerClue(lamp);

        SceneObject book = new SceneObject(
            "Un libro...", "un libro pieno di bestemmie ho sonno",
            50f, 60f,
            40f, 120f
        );
        scene2.addInteractiveObject(book);

        //Creazione enigmi
        PuzzleObject puzzleObject = new PuzzleObject(
            "Enigma Misterioso", // tooltipText che appare al passaggio del mouse
            "Hai trovato un enigma da risolvere!", // dialogText che appare al click
            100f, // posizione x nella scena
            100f, // posizione y nella scena
            50f,  // larghezza dell'area cliccabile
            50f,  // altezza dell'area cliccabile
            new ReverseTextPuzzle()
        );
        scene1.addInteractiveObject(puzzleObject);

        System.out.println("Scene1 in GameInitializer: " + System.identityHashCode(scene1));
        System.out.println("Current Scene in GameContext: " + System.identityHashCode(gameContext.getCurrentScene()));

    }
}
