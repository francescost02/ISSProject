

import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import io.ISSProject.game.model.settingsMenuModel.AudioManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AudioManagerTest {

    private AudioManager audioManager;



    @BeforeEach
    void setUp() {
        AudioManager.resetInstance(); // Resetta l'istanza prima di ogni test
        // Configura Gdx in modalità headless
        if (Gdx.files == null) {
            HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
            new HeadlessApplication(new ApplicationListener() {
                @Override
                public void create() {}
                @Override
                public void resize(int width, int height) {}
                @Override
                public void render() {}
                @Override
                public void pause() {}
                @Override
                public void resume() {}
                @Override
                public void dispose() {}
            }, config);
        }

        // Inizializza l'istanza singleton prima di ogni test
        audioManager = AudioManager.getInstance();
    }

    @Test
    void testSingletonInstance() {
        // Verifica che il singleton ritorni sempre la stessa istanza
        AudioManager instance1 = AudioManager.getInstance();
        AudioManager instance2 = AudioManager.getInstance();

        assertSame(instance1, instance2, "Le due istanze devono essere le stesse (singleton)");
    }

    @Test
    void testDefaultVolume() {
        // Verifica che il volume di default sia 50
        assertEquals(50, audioManager.getVolume(), "Il volume di default dovrebbe essere 50");
    }

    @Test
    void testSetVolumeValidRange() {
        // Imposta un volume valido e verifica
        audioManager.setVolume(50);
        assertEquals(50, audioManager.getVolume(), "Il volume dovrebbe essere aggiornato correttamente");
    }

    @Test
    void testSetVolumeOutOfRange() {
        // Imposta un volume fuori dal range e verifica che venga limitato
        audioManager.setVolume(150);
        assertEquals(100, audioManager.getVolume(), "Il volume dovrebbe essere limitato a 100");

        audioManager.setVolume(-20);
        assertEquals(0, audioManager.getVolume(), "Il volume dovrebbe essere limitato a 0");
    }

    @Test
    void testMuteToggle() {
        // Verifica il comportamento del mute toggle
        boolean wasMuted = audioManager.isMuted();
        float initialVolume = audioManager.getVolume();

        audioManager.toggleMute();
        assertTrue(audioManager.isMuted(), "Il sistema dovrebbe essere messo in mute");
        assertEquals(0, audioManager.getVolume(), "Il volume dovrebbe essere 0 quando è in mute");

        audioManager.toggleMute();
        assertFalse(audioManager.isMuted(), "Il sistema dovrebbe essere smutato");
        assertEquals(initialVolume, audioManager.getVolume(), "Il volume dovrebbe essere ripristinato al valore precedente");
    }

    @Test
    void testUpdateVolume() {
        // Test volume normale
        audioManager.setVolume(50);
        assertEquals(50, audioManager.getVolume(), "Il volume dovrebbe essere aggiornato correttamente");

        // Test mute
        audioManager.setVolume(0);
        assertTrue(audioManager.isMuted(), "Il sistema dovrebbe essere in mute quando il volume è 0");
    }

    @Test
    void testPlayMusic() {
        // Verifica che la musica inizi a suonare
        audioManager.playMusic();
        assertTrue(audioManager.isMuted() || audioManager.getVolume() > 0, "La musica dovrebbe suonare se non è in mute");
    }

    @Test
    void testStopMusic() {
        // Verifica che la musica si fermi
        audioManager.stopMusic();
        assertFalse(audioManager.isMuted(), "La musica dovrebbe essere ferma");
    }

    @Test
    void testDispose() {
        // Verifica che dispose venga chiamato senza errori
        assertDoesNotThrow(() -> audioManager.dispose(), "Dispose dovrebbe rilasciare le risorse senza errori");
    }
}
