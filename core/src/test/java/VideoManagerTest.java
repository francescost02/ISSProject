import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import io.ISSProject.game.model.settingsMenuModel.VideoManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VideoManagerTest {

    private VideoManager videoManager;

    @BeforeEach
    void setUp() {
        if (Gdx.graphics == null) {
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

        videoManager = VideoManager.getInstance();
    }

    @Test
    void testSingletonInstance() {
        VideoManager instance1 = VideoManager.getInstance();
        VideoManager instance2 = VideoManager.getInstance();
        assertSame(instance1, instance2, "Le due istanze devono essere le stesse (singleton)");
    }

    @Test
    void testDefaultResolution() {
        assertEquals("1920x1080", videoManager.getResolution(), "La risoluzione di default dovrebbe essere 1920x1080");
    }

    @Test
    void testSetResolutionValid() {
        videoManager.setResolution("1280x720");
        assertEquals("1280x720", videoManager.getResolution(), "La risoluzione dovrebbe essere aggiornata a 1280x720");
    }

    @Test
    void testApplyResolutionFullScreen() {
        videoManager.setResolution("1920x1080");
        assertEquals("1920x1080", videoManager.getResolution(), "La risoluzione dovrebbe essere 1920x1080 in modalità fullscreen");
    }

    @Test
    void testApplyResolutionWindowed() {
        videoManager.setResolution("1024x768");
        assertEquals("1024x768", videoManager.getResolution(), "La risoluzione dovrebbe essere aggiornata a 1024x768 in modalità finestra");
    }
}
