package io.ISSProject.game.model.settingsMenuModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;

public class AudioManager {

    private static AudioManager instance;
    private int volume = 50; // Volume di default
    private boolean muted = false;
    private Music backgroundMusic;

    private AudioManager() {
        // Carica una traccia audio di esempio
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/game-menu-sound.mp3"));

        backgroundMusic.setLooping(true); // Imposta il loop
        updateVolume();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void setVolume(int volume) {
        // Imposta il volume solo se non è già mute
        if (!muted) {
            this.volume = Math.max(0, Math.min(volume, 100)); // Mantieni il volume tra 0 e 100
        }
        updateVolume();
    }


    public int getVolume() {
        return volume;
    }

    public void toggleMute() {
        muted = !muted;

        // Se il mute è attivo, salva il volume corrente e impostalo a 0
        if (muted) {
            backgroundMusic.setVolume(0);
        } else {
            updateVolume(); // Ripristina il volume precedente
        }
    }


    public boolean isMuted() {
        return muted;
    }

    private void updateVolume() {
        if (muted) {
            backgroundMusic.setVolume(0);
        } else {
            backgroundMusic.setVolume(volume / 100f); // LibGDX usa un intervallo di 0.0f - 1.0f
        }
    }


    public void playMusic() {
        if (!backgroundMusic.isPlaying()) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        backgroundMusic.stop();
    }

    public void dispose() {
        backgroundMusic.dispose(); // Rilascia le risorse
    }
}
