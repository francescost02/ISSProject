import io.ISSProject.game.model.Diary.DetectiveDiary;
import io.ISSProject.game.model.Diary.DiaryEntry;
import io.ISSProject.game.model.Diary.DiaryObservers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiaryTest {

    private DetectiveDiary diary;

    @Mock
    private DiaryObservers mockObserver;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        diary = DetectiveDiary.getInstance();
        diary.clear();
        diary.addObserver(mockObserver);
    }

    @Test
    void testSingletonInstance() {
        // Verifica che il singleton ritorni sempre la stessa istanza
        DetectiveDiary instance1 = DetectiveDiary.getInstance();
        DetectiveDiary instance2 = DetectiveDiary.getInstance();

        assertSame(instance1, instance2, "Le due istanze devono essere le stesse (singleton)");
    }

    @Test
    void testAddEntry() {
        String title = "Test Title";
        String description = "Test Description";

        diary.addEntry(title, description);

        List<DiaryEntry> entries = diary.getEntriesChronological();
        assertFalse(entries.isEmpty(), "Il diario non dovrebbe essere vuoto dopo l'aggiunta");

        DiaryEntry lastEntry = entries.get(entries.size() - 1);
        assertEquals(title, lastEntry.getTitle(), "Il titolo dell'indizio dovrebbe corrispondere");
        assertEquals(description, lastEntry.getContent(), "La descrizione dell'indizio dovrebbe corrispondere");
    }

    @Test
    void testObserverNotification() {
        String title = "Test Title";
        String description = "Test Description";

        diary.addEntry(title, description);

        // Verifica che l'osservatore sia stato notificato
        verify(mockObserver, times(1)).onDiaryUpdated(any(DiaryEntry.class));
    }

    @Test
    void testChronologicalOrder() throws InterruptedException{
        diary.addEntry("First Entry", "Description 1");
        Thread.sleep(1);
        diary.addEntry("Second Entry", "Description 2");
        Thread.sleep(1);
        diary.addEntry("Third Entry", "Description 3");
        Thread.sleep(1);

        List<DiaryEntry> entries = diary.getEntriesChronological();

        assertEquals(3, entries.size(), "Dovrebbero esserci 3 indizi");

        // Verifica l'ordine cronologico
        for (int i = 1; i < entries.size(); i++) {
            assertTrue(entries.get(i-1).getTimestamp()
                    .compareTo(entries.get(i).getTimestamp()) <= 0,
                "Gli indizi dovrebbero essere in ordine cronologico");
        }
    }

    @Test
    void testRemoveObserver() {
        diary.removeObserver(mockObserver);
        diary.addEntry("Test", "Description");

        // Verifica che l'osservatore non sia più notificato dopo la rimozione
        verify(mockObserver, never()).onDiaryUpdated(any(DiaryEntry.class));
    }
}
