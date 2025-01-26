package io.ISSProject.game.controller;

import io.ISSProject.game.controller.gameState.GameplayState;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.model.Clue;
import io.ISSProject.game.model.Scene;

public class GameInitializer {
    public static void initializeGame(GameContext gameContext) {
        // Crea le scene
        Scene scene1 = new Scene("Brother's Living Room", 1);  // imposta nome e indizi
        Scene scene2 = new Scene("Ferramenta", 1);
        Scene scene3 = new Scene("Giardino", 2);

        // Aggiungi indizi alle scene
        Clue lamp = new Clue("Una lampada vintage", "Una lampada vintage...non credo mi possa aiutare nella risoluzione di questo caso.");
        scene1.addInteractiveObject(lamp);  // Aggiungi l'indizio alla scena

        // Configura la sequenza delle scene
        scene1.setNextScene(scene2);
        scene2.setNextScene(scene3);
    }
}
