package io.ISSProject.game.model.settingsMenuModel;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public class AudioManager {

    private static AudioManager instance;
    private int volume = 50; // Volume di default
    private boolean muted = false;
    private int previousVolume = 50;
    private Music backgroundMusic;
    private Sound clickSound;
    private Sound clickSoundNotClue;

    private AudioManager() {
        // Carica una traccia audio di esempio
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/gameSound.mp3"));
        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/clickSound.wav"));
        clickSoundNotClue = Gdx.audio.newSound(Gdx.files.internal("audio/clickSound2.mp3"));
        backgroundMusic.setLooping(true); // Imposta il loop
        updateVolume();
    }

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    public void playClickSound() {
        if (clickSound != null) {
            clickSound.play(1.0f); // Volume da 0.0f (muto) a 1.0f (massimo)
        }
        if (clickSoundNotClue != null) {
            clickSoundNotClue.play(1.0f); // Volume da 0.0f (muto) a 1.0f (massimo)
        }
    }

    public void playClickSound2() {
        if (clickSoundNotClue != null) {
            clickSoundNotClue.play(1.0f); // Volume da 0.0f (muto) a 1.0f (massimo)
        }
    }

    public void setVolume(int volume) {
        // Imposta il volume solo se non è già mute
        if (!muted) {
            this.volume = Math.max(0, Math.min(volume, 100)); // Mantieni il volume tra 0 e 100
        }
        if (this.volume == 0){
            muted = true;
        }
        updateVolume();
    }


    public int getVolume() {
        return volume;
    }

    public void toggleMute() {
        // Se il mute è attivo, salva il volume corrente e impostalo a 0
        if (muted) {
            muted = false;
            volume = previousVolume;
            //backgroundMusic.setVolume(0);
        } else {
            previousVolume = volume;
            muted = true;
            volume = 0;
        }
        updateVolume(); // Ripristina il volume precedente
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

    public void reloadSounds() {
        // Ricarica tutte le risorse audio
        if (clickSound != null) clickSound.dispose();
        if (clickSoundNotClue != null) clickSoundNotClue.dispose();
        //if (backgroundMusic != null) backgroundMusic.dispose();

        clickSound = Gdx.audio.newSound(Gdx.files.internal("audio/clickSound.wav"));
        clickSoundNotClue = Gdx.audio.newSound(Gdx.files.internal("audio/clickSound2.mp3"));
        //backgroundMusic = Gdx.audio.newSound(Gdx.files.internal("sounds/background.mp3"));
    }

    public static void resetInstance() {
        instance = null;
    }

}
