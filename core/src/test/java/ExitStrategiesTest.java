import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import io.ISSProject.game.controller.exitMenuStrategy.*;
import io.ISSProject.game.controller.gameState.GameContext;
import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExitStrategiesTest {
    private GameContext gameContext;
    private SaveGameManager saveGameManager;
    private Application mockApp;

    @BeforeEach
    void setUp() {
        // Configura ambiente headless
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new DummyApplication(), config);

        // Resetta il singleton e inizializza
        GameContext.resetInstance();
        gameContext = GameContext.getInstance();
        saveGameManager = new SaveGameManager();

        mockApp = mock(Application.class);
        Gdx.app = mockApp;
    }

    @Test
    void testSaveAndCloseStrategyWithValidUser() {
        // Configura uno stato valido
        gameContext.setUsername("testUser");
        gameContext.setCurrentScene(new DummyScene());

        SaveAndCloseStrategy strategy = new SaveAndCloseStrategy(saveGameManager);
        assertDoesNotThrow(strategy::execute, "L'esecuzione dovrebbe completarsi senza errori");

        verify(Gdx.app, times(1)).exit();

    }

    @Test
    void testSaveAndCloseStrategyWithoutUser() {
        SaveAndCloseStrategy strategy = new SaveAndCloseStrategy(saveGameManager);

        strategy.execute();

        verify(Gdx.app, times(1)).exit();
    }

    @Test
    void testCloseWithoutSavingStrategy() {
        CloseWithoutSavingStrategy strategy = new CloseWithoutSavingStrategy();
        strategy.execute();
        verify(Gdx.app, times(1)).exit();
    }

    // Classe helper per testing
    private static class DummyScene extends Scene {
       public DummyScene(){
           super("TestScene", 1);
       }

        @Override
        public List<String> exportFoundClues() {
            return List.of("TestClue");
        }
    }

    private static class DummyApplication extends com.badlogic.gdx.ApplicationAdapter {
        @Override
        public void create() {}
    }
}
