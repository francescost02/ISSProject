import io.ISSProject.game.model.Scene;
import io.ISSProject.game.model.saveModel.GameStateMemento;
import io.ISSProject.game.model.saveModel.SaveGameManager;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SaveGameManagerTest {
    private static final String USERNAME = "TestUser";
    private static final String SAVE_NAME = "test_save";
    private SaveGameManager saveGameManager;

    @BeforeEach
    void setUp() {
        saveGameManager = new SaveGameManager(List.of());
    }

    @AfterEach
    void tearDown() {
        // Elimina tutti i file e directory usati nei test
        File userDir = new File("saves/" + USERNAME);
        if (userDir.exists()) {
            for (File file : userDir.listFiles()) {
                file.delete();
            }
            userDir.delete();
        }
    }

    @Test
    void testSaveGame_CreatesFile() {
        Scene scene = new Scene("TestScene", 1);

        saveGameManager.saveGame(USERNAME, SAVE_NAME, scene);

        File saveFile = new File("saves/" + USERNAME + "/" + SAVE_NAME + ".json");
        assertTrue(saveFile.exists(), "Il file di salvataggio dovrebbe essere creato.");
    }

    @Test
    void testLoadGame_ReturnsCorrectData() {
        Scene scene = new Scene("TestScene", 1);

        saveGameManager.saveGame(USERNAME, SAVE_NAME, scene);

        GameStateMemento loadedMemento = saveGameManager.loadGame(USERNAME, SAVE_NAME);

        assertNotNull(loadedMemento, "Il caricamento del salvataggio non dovrebbe restituire null.");
        assertEquals("TestScene", loadedMemento.getSceneName());
    }

    @Test
    void testDeleteSave_DeletesFile() {
        Scene scene = new Scene("TestScene", 1);
        saveGameManager.saveGame(USERNAME, SAVE_NAME, scene);

        boolean deleted = saveGameManager.deleteSave(USERNAME, SAVE_NAME);
        assertTrue(deleted, "Il file di salvataggio dovrebbe essere eliminato.");

        File saveFile = new File("saves/" + USERNAME + "/" + SAVE_NAME + ".json");
        assertFalse(saveFile.exists(), "Il file di salvataggio non dovrebbe più esistere.");
    }
}
