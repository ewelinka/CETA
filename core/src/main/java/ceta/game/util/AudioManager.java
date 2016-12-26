package ceta.game.util;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by ewe on 9/21/16.
 */

public class AudioManager {
    public static final AudioManager instance = new AudioManager();
    private Music playingMusic;
    private Sound currentSound;
    private float defaultVolSound = 0.1f;
    // singleton: prevent instantiation from other classes
    private AudioManager () { }

    public void play (Sound sound) {
        play(sound, 1);
    }
    public void play (Sound sound, float volume) {
        play(sound, volume, 1);
    }
    public void play (Sound sound, float volume, float pitch) {
        play(sound, volume, pitch, 0);
    }
    public void play (Sound sound, float volume, float pitch, float pan) {
        currentSound = sound;
        currentSound.play(defaultVolSound * volume, pitch, pan);
    }

    public void stopSound(){
        if(currentSound!= null) currentSound.stop();
    }

    public void play (Music music) {
        stopMusic();
        playingMusic = music;

        music.setLooping(true);
        music.setVolume(defaultVolSound);
        music.play();

    }
    public void stopMusic () {
        if (playingMusic != null) playingMusic.stop();
    }

}
