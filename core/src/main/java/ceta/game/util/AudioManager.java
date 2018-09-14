package ceta.game.util;

import ceta.game.game.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;
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
    private SequenceAction readMe, readNumberWithDelay, readFeedbackAction;
    private Actor reader;
    private Stage stage;
    private Sound[] positiveNormalSounds = new Sound[]{Assets.instance.sounds.a1, Assets.instance.sounds.a2, Assets.instance.sounds.a3};

    // singleton: prevent instantiation from other classes
    private AudioManager () { }

    public void setStage(Stage stage){
        this.stage = stage;
        reader = new Actor();
        stage.addActor(reader);
        readMe = readNumberWithDelay = readFeedbackAction = new SequenceAction();
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
        sound.play(defaultVolSound , 1, 0);
    }

    public void playWithoutInterruption(Music m) {
        m.play();
    }


    public void playWithoutInterruptionLoud(Sound sound) {
        sound.play(1 , 1, 0);
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
        Sound toRead = Assets.instance.sounds.buzz;
        switch (nr){
            case 0:
                toRead = Assets.instance.sounds.zero;
                break;
            case 1:
                toRead = Assets.instance.sounds.one;
                break;
            case 2:
                toRead = Assets.instance.sounds.two;
                break;
            case 3:
                toRead = Assets.instance.sounds.three;
                break;
            case 4:
                toRead = Assets.instance.sounds.four;
                break;
            case 5:
                toRead = Assets.instance.sounds.five;
                break;
            case 6:
                toRead = Assets.instance.sounds.six;
                break;
            case 7:
                toRead = Assets.instance.sounds.seven;
                break;
            case 8:
                toRead = Assets.instance.sounds.eight;
                break;
            case 9:
                toRead = Assets.instance.sounds.nine;
                break;
            case 10:
                toRead = Assets.instance.sounds.ten;
                break;
            case 11:
                toRead = Assets.instance.sounds.eleven;
                break;
            case 12:
                toRead = Assets.instance.sounds.twelve;
                break;
            case 13:
                toRead = Assets.instance.sounds.thirteen;
                break;
            case 14:
                toRead = Assets.instance.sounds.fourteen;
                break;
            case 15:
                toRead = Assets.instance.sounds.fifteen;
                break;
            case 16:
                toRead = Assets.instance.sounds.s16;
                break;
            case 17:
                toRead = Assets.instance.sounds.s17;
                break;
            case 18:
                toRead = Assets.instance.sounds.s18;
                break;
            case 19:
                toRead = Assets.instance.sounds.s19;
                break;
            case 20:
                toRead = Assets.instance.sounds.s20;
                break;
            case 21:
                toRead = Assets.instance.sounds.s21;
                break;
            case 22:
                toRead = Assets.instance.sounds.s22;
                break;
            case 23:
                toRead = Assets.instance.sounds.s23;
                break;
        }
        readNumberWithDelay.reset();
        readNumberWithDelay.addAction(delay(1.0f));
        final Sound finalToRead = toRead;
        readNumberWithDelay.addAction(run(new Runnable() {
            public void run() {
                playWithoutInterruption(finalToRead);
            }
        }));
        reader.addAction(readNumberWithDelay);

    }

    public void stopSound(){
        if(currentSound!= null) currentSound.stop();
    }

    public void play (Music music) {

        if(music != playingMusic){
            stopMusic();
            playingMusic = music;
            music.setVolume(0.15f);
            music.setLooping(true);
           // music.setVolume(1.0f);
            music.play();
        }else{
            if(!music.isPlaying()){
                music.setVolume(0.15f);
                music.play();

            }
        }
    }

    public void setMusicVol(float val){
        playingMusic.setVolume(val);
    }

    public float getMusicVolume(){
        return playingMusic.getVolume();
    }
    public void stopMusic () {
        if (playingMusic != null) playingMusic.stop();
    }

    public void playCreepy(){
        Music creepy = Assets.instance.sounds.creepy;
        creepy.setVolume(0.25f);
        playWithoutInterruption(creepy);

    }

    public Sound getPositiveFeedbackForIslandRandom(int islandNr){
        float decideFeedback = MathUtils.random();
        int decideWhichNormal = MathUtils.random(0,2);
        Sound positiveSound = positiveNormalSounds[decideWhichNormal];

        if(decideFeedback >= 0.8){
            switch(islandNr){
                case 2:
                    positiveSound = Assets.instance.sounds.b2;
                    break;
                case 3:
                    positiveSound = Assets.instance.sounds.b3;
                    break;
                case 4:
                    positiveSound = Assets.instance.sounds.b4;
                    break;
                case 5:
                    positiveSound = Assets.instance.sounds.b5;
                    break;
                case 6:
                    positiveSound = Assets.instance.sounds.b6;
                    break;
            }
        }
        return positiveSound;

    }

    public Sound getPositiveFeedbackForIsland(int islandNr) {
        float decideFeedback = MathUtils.random();
        //a1- 35%, a2- 30%, a3- 25%, bx- 10%
        Sound positiveSound = Assets.instance.sounds.a1; // 0-35%
        if(decideFeedback <= 0.35){
            positiveSound = Assets.instance.sounds.a1;
        }
        else if ( decideFeedback < 0.65){ //35%-65%
            positiveSound = Assets.instance.sounds.a2;
        }else if(decideFeedback < 0.9){ // 65% - 90%
            positiveSound = Assets.instance.sounds.a3;
        }else { //90%-100%
            switch(islandNr){
                case 2:
                    positiveSound = Assets.instance.sounds.b2;
                    break;
                case 3:
                    positiveSound = Assets.instance.sounds.b3;
                    break;
                case 4:
                    positiveSound = Assets.instance.sounds.b4;
                    break;
                case 5:
                    positiveSound = Assets.instance.sounds.b5;
                    break;
                case 6:
                    positiveSound = Assets.instance.sounds.b6;
                    break;
            }
        }

        return positiveSound;

    }

    public void playErrorSound(){
        readFeedbackAction.reset();
        readFeedbackAction.addAction(delay(0.7f));
        readFeedbackAction.addAction(run(new Runnable() {
            public void run() {
                AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.error);
            }
        }));
        reader.addAction(readFeedbackAction);
    }

    public void playPositiveSound(final Sound toPlay){
        readFeedbackAction.reset();
        readFeedbackAction.addAction(delay(0.7f));
        readFeedbackAction.addAction(run(new Runnable() {
            public void run() {
                AudioManager.instance.playWithoutInterruption(toPlay);
            }
        }));
        reader.addAction(readFeedbackAction);
    }

}
