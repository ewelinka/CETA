package ceta.game.util;

import ceta.game.game.Assets;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by ewe on 9/21/16.
 */

public class AudioManager {
    public static final AudioManager instance = new AudioManager();
    private Music playingMusic;
    private Sound currentSound;
    private float defaultVolSound = 0.3f;
    private SequenceAction readMe;
    private Actor reader;
    private Stage stage;

    // singleton: prevent instantiation from other classes
    private AudioManager () { }

    public void setStage(Stage stage){
        this.stage = stage;
        reader = new Actor();
        stage.addActor(reader);
        readMe = new SequenceAction();
    }

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

    public void playWithoutInterruption(Sound sound) {
        sound.play(defaultVolSound , 1, 1);
    }

    public void playWithoutInterruptionLoud(Sound sound) {
        sound.play(1 , 1, 1);
    }

    public void addToPlay (int nr) {

        switch (nr){
            case 1:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.one);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 2:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.two);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 3:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.three);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 4:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.four);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 5:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.five);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 6:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.three);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 7:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.three);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 8:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.three);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 9:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.three);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
            case 10:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        playWithoutInterruption(Assets.instance.sounds.three);
                    }
                }));
                readMe.addAction(delay(1.0f));
                break;
        }
    }


    public void readTheSum(ArrayList<Integer> numbers){

        readMe.reset();
        for(int i = 0; i< numbers.size();i++){
            addToPlay(numbers.get(i));
        }

        reader.addAction(readMe);

    }

    public void readNumber(int nr){
        switch (nr){
            case 1:
                playWithoutInterruptionLoud(Assets.instance.sounds.one);
                break;
            case 2:
                playWithoutInterruptionLoud(Assets.instance.sounds.two);
                break;
            case 3:
                playWithoutInterruptionLoud(Assets.instance.sounds.three);
                break;
            case 4:
                playWithoutInterruptionLoud(Assets.instance.sounds.four);
                break;
            case 5:
                playWithoutInterruptionLoud(Assets.instance.sounds.five);
                break;
            case 6:
                playWithoutInterruptionLoud(Assets.instance.sounds.six);
                break;
            case 7:
                playWithoutInterruptionLoud(Assets.instance.sounds.seven);
                break;
            case 8:
                playWithoutInterruptionLoud(Assets.instance.sounds.eight);
                break;
            case 9:
                playWithoutInterruptionLoud(Assets.instance.sounds.nine);
                break;
            case 10:
                playWithoutInterruptionLoud(Assets.instance.sounds.ten);
                break;
            case 11:
                playWithoutInterruptionLoud(Assets.instance.sounds.eleven);
                break;
            case 12:
                playWithoutInterruptionLoud(Assets.instance.sounds.twelve);
                break;
            case 13:
                playWithoutInterruptionLoud(Assets.instance.sounds.thirteen);
                break;
        }

    }

    public void stopSound(){
        if(currentSound!= null) currentSound.stop();
    }

    public void play (Music music) {
        stopMusic();
        playingMusic = music;

        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();

    }
    public void stopMusic () {
        if (playingMusic != null) playingMusic.stop();
    }

}
