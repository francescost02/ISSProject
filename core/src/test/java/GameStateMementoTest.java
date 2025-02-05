import io.ISSProject.game.model.saveModel.GameStateMemento;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GameStateMementoTest {

    private GameStateMemento memento;

    @BeforeEach
    void setUp() {
        memento = new GameStateMemento("TestUser", "TestScene", List.of("Clue1", "Clue2"));
    }

    @Test
    void testGetUsername() {
        assertEquals("TestUser", memento.getUsername(), "Il nome utente dovrebbe essere 'TestUser'");
    }

    @Test
    void testSetUsername() {
        memento.setUsername("NewUser");
        assertEquals("NewUser", memento.getUsername(), "Il nome utente dovrebbe essere 'NewUser'");
    }

    @Test
    void testGetSceneName() {
        assertEquals("TestScene", memento.getSceneName(), "Il nome della scena dovrebbe essere 'TestScene'");
    }

    @Test
    void testSetSceneName() {
        memento.setSceneName("NewScene");
        assertEquals("NewScene", memento.getSceneName(), "Il nome della scena dovrebbe essere 'NewScene'");
    }

    @Test
    void testGetFoundClues() {
        List<String> clues = memento.getFoundClues();
        assertNotNull(clues, "La lista degli indizi non dovrebbe essere null");
        assertEquals(2, clues.size(), "La lista dovrebbe contenere esattamente 2 elementi");
        assertTrue(clues.contains("Clue1"), "Dovrebbe contenere 'Clue1'");
        assertTrue(clues.contains("Clue2"), "Dovrebbe contenere 'Clue2'");
    }

    @Test
    void testSetFoundClues() {
        memento.setFoundClues(List.of("Clue3", "Clue4"));
        List<String> clues = memento.getFoundClues();
        assertEquals(2, clues.size(), "La lista dovrebbe contenere esattamente 2 elementi");
        assertTrue(clues.contains("Clue3"), "Dovrebbe contenere 'Clue3'");
        assertTrue(clues.contains("Clue4"), "Dovrebbe contenere 'Clue4'");
    }

    @Test
    void testEmptyCluesList() {
        memento.setFoundClues(List.of());
        assertTrue(memento.getFoundClues().isEmpty(), "La lista degli indizi dovrebbe essere vuota");
    }
}
