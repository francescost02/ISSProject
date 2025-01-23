
import static org.mockito.Mockito.*;

import io.ISSProject.game.controller.mainMenuCommand.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import io.ISSProject.game.controller.GameContext;

public class MainMenuCommandTest {

    @Mock
    private MainMenuReceiver mainMenuReceiver;

    @Mock
    private GameContext gameContext;

    private NewGameCommand newGameCommand;
    private LoadGameCommand loadGameCommand;
    private ExitGameCommand exitGameCommand;
    private SettingsCommand settingsCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        newGameCommand = new NewGameCommand(mainMenuReceiver);
        loadGameCommand = new LoadGameCommand(mainMenuReceiver);
        exitGameCommand = new ExitGameCommand(mainMenuReceiver);
        settingsCommand = new SettingsCommand(mainMenuReceiver);
    }

    @Test
    public void testNewGameCommand() {
        // Esegui il comando
        newGameCommand.execute();

        // Verifica che il metodo createNewGame sia stato chiamato
        verify(mainMenuReceiver, times(1)).createNewGame();
    }

    @Test
    public void testLoadGameCommand() {
        // Esegui il comando
        loadGameCommand.execute();

        // Verifica che il metodo loadGame sia stato chiamato
        verify(mainMenuReceiver, times(1)).loadGame();
    }

    @Test
    public void testExitGameCommand() {
        // Esegui il comando
        exitGameCommand.execute();

        // Verifica che il metodo exitGame sia stato chiamato
        verify(mainMenuReceiver, times(1)).exitGame();
    }

    @Test
    public void testSettingsCommand() {
        // Esegui il comando
        settingsCommand.execute();

        // Verifica che il metodo openSettings sia stato chiamato
        verify(mainMenuReceiver, times(1)).openSettings();
    }
}
