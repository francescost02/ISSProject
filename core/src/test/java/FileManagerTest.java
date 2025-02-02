

import io.ISSProject.game.model.saveModel.FileManager;
import io.ISSProject.game.model.saveModel.GameStateMemento;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    private static final String TEST_FILE_PATH = "test_save.json";
    private FileManager fileManager;
    private GameStateMemento testMemento;

    @BeforeEach
    void setUp() {
        fileManager = new FileManager();
        List<String> foundClues = Arrays.asList("Indizio1", "Indizio2", "Indizio3");
        testMemento = new GameStateMemento("testUser", "TestScene", foundClues);
    }

    @AfterEach
    void tearDown() {
        File file = new File(TEST_FILE_PATH);
        if (file.exists()) {
            assertTrue(file.delete(), "Il file di test non è stato eliminato correttamente");
        }
    }

    @Test
    void testSaveToFileAndLoadFromFile() {
        // Salvataggio su file
        fileManager.saveToFile(testMemento, TEST_FILE_PATH);

        // Controllo che il file sia stato creato
        File file = new File(TEST_FILE_PATH);
        assertTrue(file.exists(), "Il file di salvataggio non è stato creato");

        // Caricamento dal file
        GameStateMemento loadedMemento = fileManager.loadFromFile(TEST_FILE_PATH);
        assertNotNull(loadedMemento, "Il memento caricato è nullo");

        // Verifica dei dati
        assertEquals(testMemento.getUsername(), loadedMemento.getUsername(), "Lo username non corrisponde");
        assertEquals(testMemento.getSceneName(), loadedMemento.getSceneName(), "Il nome della scena non corrisponde");
        assertEquals(testMemento.getFoundClues(), loadedMemento.getFoundClues(), "Gli indizi trovati non corrispondono");
    }

    @Test
    void testLoadFromFile_FileNotFound() {
        GameStateMemento loadedMemento = fileManager.loadFromFile("non_existing_file.json");
        assertNull(loadedMemento, "Il caricamento di un file inesistente dovrebbe restituire null");
    }
}
