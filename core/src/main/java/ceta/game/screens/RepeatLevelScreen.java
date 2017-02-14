package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.Gear;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by ewe on 2/10/17.
 */
public class RepeatLevelScreen extends AbstractGameScreen {
    private Image repeat,brunoHead,brunoBody,shadow;
    private Group bruno;
    private int gearsNr = 5;
    private boolean moveToNextLevel;

    public RepeatLevelScreen(DirectedGame game) {
        super(game);
    }

    private void buildStage() {

        //Table layerBackground = buildBackgroundLayer();
        //Table playMenu = buildPlayMenu();
        moveToNextLevel =false;
        stage.clear();

        buildBackgroundLayer();
        buildBruno();

        //stage.addActor(playMenu);
    }

    @Override
    public void render(float deltaTime) {
        if(moveToNextLevel){
            game.getLevelsManager().goToUncompletedLevel();
            moveToNextLevel = false;
        }
        //blue!
        //Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClearColor(98 / 255.0f, 151/255.0f , 173/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        buildStage();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        Gdx.input.setCatchBackKey(true); // from congrats you con go to menu
        buildStage();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    private void buildBackgroundLayer () {

        repeat = new Image(Assets.instance.finishBackGround.repeat);
        for(int i = 0; i< gearsNr;i++){
            //gear = new Gear();
            stage.addActor(new Gear(i));
        }


        stage.addActor(repeat);
        repeat.setOrigin(repeat.getWidth() / 2, repeat.getHeight() / 2);
        repeat.setPosition(0,600);
        repeat.addAction(sequence(
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



        //return layer;
    }


    private void buildBruno(){
        bruno = new Group();
        brunoBody = new Image(Assets.instance.bruno.initBody);
        brunoHead = new Image(Assets.instance.bruno.initHead);
        shadow = new Image(Assets.instance.background.shadow);

        // brunoBody.setOrigin(brunoBody.getWidth() / 2, brunoBody.getHeight() / 2);

        bruno.addActor(shadow);
        bruno.addActor(brunoBody);
        bruno.addActor(brunoHead);
        // brunoBody.setOrigin(brunoBody.getWidth() / 2, brunoBody.getHeight() / 2);
        shadow.setSize(105*2,12*2);
        shadow.setPosition(-18*2,-3*2);

        brunoBody.setSize(76*2,64*2);
        brunoHead.setSize(76*2,56*2);
        //
        brunoBody.setPosition(0,0);
        brunoHead.setPosition(0,brunoBody.getHeight()-10*2);

        bruno.setPosition(-200,360);

        bruno.addAction(sequence(
                delay(0.5f),
                moveTo(610,360,2f)
//                Actions.rotateBy(50,1.0f),
//                Actions.rotateBy(-50,0.8f),

        ));

        stage.addActor(bruno);
    }
}
