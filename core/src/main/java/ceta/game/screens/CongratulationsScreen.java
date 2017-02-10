package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.Gear;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
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


    private ImageButton btnStartGame;
    private Image imgBackground;
    private Image congrats;
    private Image thumb;
    //private Gear gear;
    private int gearsNr = 5;
    private boolean moveToNextLevel;


    public CongratulationsScreen(DirectedGame game, int score) {
        super(game);
        game.getLevelsManager().onLevelCompleted(score);
    }


    private void buildStage() {

        //Table layerBackground = buildBackgroundLayer();
        //Table playMenu = buildPlayMenu();
        moveToNextLevel =false;
        stage.clear();
        buildBackgroundLayer();
        //stage.addActor(playMenu);
    }

    public void render (float deltaTime){
        if(moveToNextLevel){
            game.getLevelsManager().goToFirstUncompletedLevel();
            moveToNextLevel = false;
        }
        //blue!
        //Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
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
    }
    public void hide (){

        stage.dispose();

    }
    public void pause (){};

    private void onPlayClicked () {
//        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
//        game.setScreen(new Level1HorizontalScreen(game), transition);
        game.getLevelsManager().goToFirstUncompletedLevel();
    }

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
                        //game.getLevelsManager().goToFirstUncompletedLevel();
                        //game.setScreen(new TreeScreen(game), ScreenTransitionFade.init(1.75f));
                        //game.getLevelsManager().goToNextLevel();
                    }
                })
        ));

        thumb = new Image(Assets.instance.finishBackGround.thumbUp);
        stage.addActor(thumb);
        thumb.setOrigin(thumb.getWidth() / 2, thumb.getHeight() / 2);
        thumb.setPosition(0 - thumb.getWidth(),400);
        thumb.addAction(moveTo(-20, 100, 1.5f, Interpolation.bounceOut));


        //return layer;
    }

    private Table buildPlayMenu () {
        /// ------------------ start -- just to create a simple button!! what a caos!!

        btnStartGame = new ImageButton(Assets.instance.buttons.playButtonStyle);

        Table tbl = new Table();
        //tbl.left().bottom();
        tbl.add(btnStartGame);
        // center of the screen
        // tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth() , Constants.VIEWPORT_HEIGHT/2);
        tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth() , 100);
        btnStartGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        return tbl;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }


    }