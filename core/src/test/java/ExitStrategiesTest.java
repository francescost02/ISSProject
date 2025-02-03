import io.ISSProject.game.controller.exitMenuStrategy.CancelStrategy;
import io.ISSProject.game.controller.exitMenuStrategy.CloseWithoutSavingStrategy;
import io.ISSProject.game.controller.exitMenuStrategy.SaveAndCloseStrategy;
import io.ISSProject.game.controller.gameState.ExitMenuState;
import io.ISSProject.game.controller.gameState.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

class ExitStrategiesTest {

    private Application mockApp;

    @BeforeEach
    void setUp() {
        // Mock di Gdx.app per evitare dipendenze reali
        mockApp = mock(Application.class);
        Gdx.app = mockApp;
    }

    @Test
    void testSaveAndCloseStrategy() {
        // Strategia da testare

        // Esecuzione
        saveAndCloseStrategy.execute();

        // Verifica che il metodo exit() sia stato chiamato
        verify(mockApp).exit();
    }

    @Test
    void testCloseWithoutSavingStrategy() {
        // Strategia da testare
        CloseWithoutSavingStrategy closeWithoutSavingStrategy = new CloseWithoutSavingStrategy();

        // Esecuzione
        closeWithoutSavingStrategy.execute();

        // Verifica che il metodo exit() sia stato chiamato
        verify(mockApp).exit();
    }

    @Test
    void testCancelStrategy() {
        // Mock dello stato ExitMenuState
        ExitMenuState mockExitMenuState = mock(ExitMenuState.class);
        when(mockExitMenuState.getPreviousState()).thenReturn(mock(GameState.class));

        // Strategia da testare
        CancelStrategy cancelStrategy = new CancelStrategy(mockExitMenuState);

        // Esecuzione
        cancelStrategy.execute();

        // Verifica che il metodo getPreviousState() sia stato chiamato
        verify(mockExitMenuState, times(1)).getPreviousState();
    }
}
