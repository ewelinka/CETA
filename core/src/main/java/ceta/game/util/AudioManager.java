package ceta.game.util;

import ceta.game.game.Assets;
import com.badlogic.gdx.Gdx;
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
    public static final String TAG = AudioManager.class.getName();

    public static final AudioManager instance = new AudioManager();
    private Music playingMusic;
    private Sound currentSound;
    private float defaultVolSound = 0.6f;
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

    public void playWithoutInterruption(Music m) {
        m.play();
    }


    public void playWithoutInterruptionLoud(Sound sound) {
        sound.play(1 , 1, 1);
    }

    public void playWithoutInterruptionLoud(Music m) {
        m.play();
    }
    private void addMusicToPlay(final Music toPlay, float delayTime){
        readMe.addAction(run(new Runnable() {
            public void run() {
                playWithoutInterruptionLoud(toPlay);
            }
        }));
        Gdx.app.log(TAG,"delay "+delayTime);
        readMe.addAction(delay(delayTime));

    }


    private void addSoundToPlay(final Sound toPlay, float delayTime){
        readMe.addAction(run(new Runnable() {
            public void run() {
                playWithoutInterruption(toPlay);
            }
        }));
        Gdx.app.log(TAG,"delay "+delayTime);
        readMe.addAction(delay(delayTime));

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

    public void readInitStory(){
        readMe.reset();
        addMusicToPlay(Assets.instance.sounds.erase,12.0f);
        addMusicToPlay(Assets.instance.sounds.malvado,7.0f);
        addMusicToPlay(Assets.instance.sounds.agarrar,0.0f);
        reader.addAction(readMe);

    }

    public void readTutorial(){
        readMe.reset();
        readMe.addAction(delay(1.5f));
        addMusicToPlay(Assets.instance.sounds.fichas,8.0f);
        addMusicToPlay(Assets.instance.sounds.inTheZone,0.0f);
        reader.addAction(readMe);

    }

    public void readCV(){
        readMe.reset();
        readMe.addAction(delay(1.0f));
        addMusicToPlay(Assets.instance.sounds.estirarse,4.0f);
        addMusicToPlay(Assets.instance.sounds.poniendo,4.0f);
        //addMusicToPlay(Assets.instance.sounds.inTheZone,0.0f);
        reader.addAction(readMe);

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
            case 14:
                playWithoutInterruptionLoud(Assets.instance.sounds.fourteen);
                break;
            case 15:
                playWithoutInterruptionLoud(Assets.instance.sounds.fifteen);
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
        music.setVolume(0.15f);
        music.play();

    }

    public void setMusicVol(float val){
        playingMusic.setVolume(val);
    }
    public void stopMusic () {
        if (playingMusic != null) playingMusic.stop();
    }

    public void playCreepy(){
        Music creepy = Assets.instance.sounds.creepy;
        creepy.setVolume(0.25f);
        playWithoutInterruption(creepy);

    }

}
