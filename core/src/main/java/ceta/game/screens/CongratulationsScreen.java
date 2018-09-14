package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.Gear;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

/**
 * Created by ewe on 9/21/16.
 */
public class CongratulationsScreen extends AbstractGameScreen {
    private static final String TAG = CongratulationsScreen.class.getName();

    private Image congrats;
    private Image thumb;
    private int gearsNr = 5;
    protected boolean moveToNextLevel;


    public CongratulationsScreen(DirectedGame game, int score) {
        super(game);
        saveLevelAndScore(score);
    }

    protected void saveLevelAndScore(int score){
        game.getLevelsManager().onLevelCompleted(score);
    }


    private void buildStage() {
        moveToNextLevel =false;
        stage.clear();
        buildBackgroundLayer();
    }

    public void render (float deltaTime){
        if(moveToNextLevel){
            game.getLevelsManager().goToFirstUncompletedLevel(false);
            moveToNextLevel = false;
        }
        Gdx.gl.glClearColor(184 / 255.0f, 1 , 226/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();

    }
    public void resize (int width, int height){
        stage.getViewport().update(width, height, true);
        buildStage();
    }
    public void show (){
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        Gdx.input.setCatchBackKey(true); // from congrats you con go to menu
        buildStage();
        playLevelPassed();
    }
    public void hide (){

        stage.dispose();

    }
    public void pause (){};

    private void buildBackgroundLayer () {
        congrats = new Image(Assets.instance.finishBackGround.excellentWork);
        for(int i = 0; i< gearsNr;i++){
            //gear = new Gear();
            stage.addActor(new Gear(i));
        }


        stage.addActor(congrats);
        congrats.setOrigin(congrats.getWidth() / 2, congrats.getHeight() / 2);
        congrats.setPosition(0,600);
        congrats.addAction(sequence(
                scaleTo(0, 0),
                delay(0.3f),
                parallel(moveBy(0, -100, 3.0f, Interpolation.swingOut),
                        scaleTo(1.0f, 1.0f, 0.25f, Interpolation.linear),
                        alpha(1.0f, 0.5f)),
//                delay(1.0f),
                run(new Runnable() {
                    public void run() {
                        moveToNextLevel = true;
                    }
                })
        ));

        thumb = new Image(Assets.instance.finishBackGround.thumbUp);
        stage.addActor(thumb);
        thumb.setOrigin(thumb.getWidth() / 2, thumb.getHeight() / 2);
        thumb.setPosition(0 - thumb.getWidth(),400);
        thumb.addAction(moveTo(-20, 100, 1.5f, Interpolation.bounceOut));

    }


    protected void playLevelPassed(){
        int whichLevelPassed = MathUtils.random(0,3);
        Sound wePassed = Assets.instance.sounds.levelPassed;
        switch (whichLevelPassed){
            case 1:
                wePassed = Assets.instance.sounds.levelPassed2;
                break;
            case 2:
                wePassed = Assets.instance.sounds.levelPassed3;
                break;
            case 3:
                wePassed = Assets.instance.sounds.levelPassed4;
                break;
        }

        if(game.levelsManager.getLastLevelCompleted() != 26)
            AudioManager.instance.playWithoutInterruptionLoud(wePassed);

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }


}

