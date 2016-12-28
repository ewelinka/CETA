package ceta.game.util;

import ceta.game.game.Assets;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by ewe on 9/21/16.
 */

public class AudioManager {
    public static final AudioManager instance = new AudioManager();
    private Music playingMusic;
    private Sound currentSound;
    private float defaultVolSound = 0.2f;
    private SequenceAction readMe;
    private Actor reader;
    private Stage stage;

    // singleton: prevent instantiation from other classes
    private AudioManager () { }

    public void setStage(Stage stage){
        this.stage = stage;
        reader = new Actor();
        stage.addActor(reader);
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

    public void play (int nr) {

        switch (nr){
            case 1:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.one);
                    }
                }));
                readMe.addAction( delay(1.0f));
                break;
            case 2:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.two);
                    }
                }));
                readMe.addAction( delay(1.0f));
                break;
            case 3:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
            case 4:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
            case 5:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
            case 6:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
            case 7:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
            case 8:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
            case 9:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
            case 10:
                readMe.addAction(run(new Runnable() {
                    public void run() {
                        play(Assets.instance.sounds.three);
                        delay(1.0f);
                    }
                }));
                break;
        }
    }


    public void readTheSum(int[] numbers){
        readMe = new SequenceAction();
        //readMe.reset();
        for(int i = 0; i< numbers.length;i++){
            play(numbers[i]);
        }
        reader.addAction(readMe);

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
