package io.ISSProject.game.controller;

import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.controller.gameState.GameplayState;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.puzzles.PuzzleObject;
import io.ISSProject.game.model.puzzles.PuzzleStrategy;
import io.ISSProject.game.model.puzzles.ReverseTextPuzzle;
import io.ISSProject.game.model.puzzles.SequenceButtonPuzzle;

public class GameInitializer {
    public static void initializeGame(GameContext gameContext) {
        // Crea scene
        Scene scene1 = new Scene("Intro", 0);
        Scene scene2 = new Scene("Brother's Bedroom", 1);
        Scene scene3 = new Scene("Ferramenta", 0);
        Scene scene4 = new Scene("Brother's Living Room", 2);
        Scene scene5 = new Scene("Warehouse", 1);
        Scene scene6 = new Scene("Call", 0);
        Scene scene7 = new Scene("Before Abandoned Shelter", 0);
        Scene scene8 = new Scene("Abandoned Shelter", 0);
        Scene scene9 = new Scene("Trap Door", 0);
        Scene scene10 = new Scene("Secret Room 1", 1);
        Scene scene11 = new Scene("Ex Boss' Hiddenout 1", 1);
        Scene scene12 = new Scene("Studio", 1);
        Scene scene13 = new Scene("Ex Boss' Hiddenout 2", 0);
        //Scene scene14 = new Scene("Buttons", 0);
        Scene scene14 = new Scene("Secret Room 2", 1);
        Scene scene15 = new Scene("Before Boss' Hiddenout", 0);
        Scene scene16 = new Scene("Final", 1);

        scene1.setAssociatedState(new GameplayState(gameContext, scene1));
        scene2.setAssociatedState(new GameplayState(gameContext, scene2));
        scene3.setAssociatedState(new GameplayState(gameContext, scene3));
        scene4.setAssociatedState(new GameplayState(gameContext, scene4));
        scene5.setAssociatedState(new GameplayState(gameContext, scene5));
        scene6.setAssociatedState(new GameplayState(gameContext, scene6));
        scene7.setAssociatedState(new GameplayState(gameContext, scene7));
        scene8.setAssociatedState(new GameplayState(gameContext, scene8));
        scene9.setAssociatedState(new GameplayState(gameContext, scene9));
        scene10.setAssociatedState(new GameplayState(gameContext, scene10));
        scene11.setAssociatedState(new GameplayState(gameContext, scene11));
        scene12.setAssociatedState(new GameplayState(gameContext, scene12));
        scene13.setAssociatedState(new GameplayState(gameContext, scene13));
        scene14.setAssociatedState(new GameplayState(gameContext, scene14));
        scene15.setAssociatedState(new GameplayState(gameContext, scene15));
        scene16.setAssociatedState(new GameplayState(gameContext, scene16));
        //scene17.setAssociatedState(new GameplayState(gameContext, scene17));

        gameContext.setCurrentScene(scene1);

        // Imposta la sequenza delle scene
        scene1.setNextScene(scene2);
        scene2.setNextScene(scene3);
        scene3.setNextScene(scene4);
        scene4.setNextScene(scene5);
        scene5.setNextScene(scene6);
        scene6.setNextScene(scene7);
        scene7.setNextScene(scene8);
        scene8.setNextScene(scene9);
        scene9.setNextScene(scene10);
        scene10.setNextScene(scene11);
        scene11.setNextScene(scene12);
        scene12.setNextScene(scene13);
        scene13.setNextScene(scene14);
        scene14.setNextScene(scene15);
        scene15.setNextScene(scene16);
        //scene16.setNextScene(scene17);

        // Aggiungi tutte le scene al GameContext
        gameContext.addScene(scene1);
        gameContext.addScene(scene2);
        gameContext.addScene(scene3);
        gameContext.addScene(scene4);
        gameContext.addScene(scene5);
        gameContext.addScene(scene6);
        gameContext.addScene(scene7);
        gameContext.addScene(scene8);
        gameContext.addScene(scene9);
        gameContext.addScene(scene10);
        gameContext.addScene(scene11);
        gameContext.addScene(scene12);
        gameContext.addScene(scene13);
        gameContext.addScene(scene14);
        gameContext.addScene(scene15);
        gameContext.addScene(scene16);
        //gameContext.addScene(scene17);

/*
        //Creazione enigmi
        PuzzleObject puzzleObject = new PuzzleObject(
            "Enigma Misterioso", // tooltipText che appare al passaggio del mouse
            "Hai trovato un enigma da risolvere!", // dialogText che appare al click
            new ReverseTextPuzzle()
        );
        scene1.addInteractiveObject(puzzleObject);

        PuzzleStrategy sequencePuzzle = new SequenceButtonPuzzle();
        PuzzleObject puzzleObject1 = new PuzzleObject(
            "Enigma sequenza",
            "Cosa c'è dietro la libreria, degli strani interuttori..., ",
            sequencePuzzle
        );
        scene1.addInteractiveObject(puzzleObject1);
*/
    }
}
