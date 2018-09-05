package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.BlackShadow;
import ceta.game.game.objects.TreeArrow;
import ceta.game.game.objects.TreeGear;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 2/14/17.
 */
public class IntroScreen extends AbstractGameScreen {
    private Image imgBackground,b1,b2,b3,b4,b5,magnet,black;
    private TreeGear g1,g2,g3,g4,g5,g6;
    private BlackShadow blackShadow;
    private int[][] gearsPositions;

    public IntroScreen(DirectedGame game) {
        super(game);
        gearsPositions = new int[][]{
                {302, 338},
                {258,292},
                {307,275},
                {351,284},
                {347,293},
                {368,375}
        };

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        buildIntro();

    }

    @Override
    public void show() {
        Gdx.app.log(TAG," we start the SHOW! "+Gdx.graphics.getWidth());
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        Gdx.input.setCatchBackKey(false);
        buildIntro();
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

    private void buildIntro(){
        imgBackground = new Image(Assets.instance.staticBackground.intro);
        imgBackground.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        imgBackground.setPosition(0, 0);
        stage.addActor(imgBackground);

        addBrunos();
        addBlack();
        addMagnet();
        addGears();
        moveBrunos();
        animateBlack();
        moveMagnet();
        moveGears();
        AudioManager.instance.setStage(stage);
        AudioManager.instance.readInitStory();



    }

    private void addBrunos(){
        b1 = new Image(Assets.instance.bruno.body01);
        b2 = new Image(Assets.instance.bruno.body02);
        b3 = new Image(Assets.instance.bruno.body03);
        b4 = new Image(Assets.instance.bruno.body04);
        b5 = new Image(Assets.instance.bruno.body05);

        float factor = 0.7f;
        b1.setPosition(635,174);
        b1.setScale(-factor, factor);
        b1.setColor(1,1,1,0.9f);
        stage.addActor(b1);

        b2.setPosition(-80,166);
        b2.setScale(factor,factor);
        stage.addActor(b2);

        b3.setPosition(665,172);
        b3.setScale(-factor, factor);
        b3.setColor(1,1,1,0.9f);
        stage.addActor(b3);

        b4.setPosition(-80,166);
        b4.setScale(factor);
        stage.addActor(b4);

        b5.setPosition(-80,166);
        b5.setScale(factor);
        stage.addActor(b5);

    }

    private void addBlack(){
        blackShadow = new BlackShadow();
        //blackShadow.setColor(0,0,0,0);
        blackShadow.setPosition(0,0);
        blackShadow.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        blackShadow.setColor(1,1,1,0);
        stage.addActor(blackShadow);
    }

    private void moveBrunos(){
        float extraDelay = 1.7f;
        // right side
        b1.addAction(sequence(
                delay(1.4f+extraDelay),
                moveTo(596,172,0.8f),
                delay(1.0f),
                moveTo(482,193,0.8f),
                delay(0.4f),
                moveTo(410,191,0.8f),
                delay(0.4f),
                moveTo(397,183,0.8f)
        ));

        b3.addAction(sequence(
                delay(2.8f+extraDelay),
                moveTo(597,172,1.2f),
                delay(1.9f),
                moveTo(482,193,1.4f)
        ));

        //left side
        b2.addAction(sequence(
                delay(0.4f+extraDelay),
                moveTo(-14,166,0.9f),
                delay(1.0f),
                moveTo(44,202,0.9f),
                delay(0.4f),
                moveTo(210,187,0.9f)
        ));

        b4.addAction(sequence(
                delay(1.0f+extraDelay),
                moveTo(-14,166,0.9f),
                delay(1.0f),
                moveTo(44,202,0.9f),
                delay(0.4f),
                moveTo(180,208,0.9f)
        ));

        b5.addAction(sequence(
                delay(1.8f+extraDelay),
                moveTo(-14,166,0.9f),
                delay(1.0f),
                moveTo(44,202,0.9f),
                delay(0.4f),
                moveTo(142,187,0.9f)
        ));


    }
    private void addGears(){
        float gearScale=0.5f;

        g1 = new TreeGear(Assets.instance.tree.gear3,Assets.instance.tree.gear3inactive);
        g2 = new TreeGear(Assets.instance.tree.gear2,Assets.instance.tree.gear2inactive);
        g3 = new TreeGear(Assets.instance.tree.gear4,Assets.instance.tree.gear4inactive);
        g4 = new TreeGear(Assets.instance.tree.gear6,Assets.instance.tree.gear6inactive);
        g5 = new TreeGear(Assets.instance.tree.gear1,Assets.instance.tree.gear1inactive);
        g6 = new TreeGear(Assets.instance.tree.gear5,Assets.instance.tree.gear5inactive);

        g1.setPosition(gearsPositions[0][0], gearsPositions[0][1]);
        g1.setScale(gearScale);
        stage.addActor(g1);

        g2.setPosition(gearsPositions[1][0], gearsPositions[1][1]);
        g2.setScale(gearScale);
        stage.addActor(g2);

        g3.setPosition(gearsPositions[2][0], gearsPositions[2][1]);
        g3.setScale(gearScale);
        stage.addActor(g3);

        g4.setPosition(gearsPositions[3][0], gearsPositions[3][1]);
        g4.setScale(gearScale);
        stage.addActor(g4);

        g5.setPosition(gearsPositions[4][0], gearsPositions[4][1]);
        g5.setScale(gearScale);
        stage.addActor(g5);

        g6.setPosition(gearsPositions[5][0], gearsPositions[5][1]);
        g6.setScale(gearScale);
        stage.addActor(g6);


    }

    private void animateBlack(){
        blackShadow.addAction(sequence(
                delay(4.0f+7.0f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        AudioManager.instance.setMusicVol(0.05f);
                        AudioManager.instance.playCreepy();
                    }
                }),
                alpha(0.7f,1.7f),
                delay(0.4f),
                alpha(0.2f,0.5f),
                alpha(0.8f,0.7f),
                delay(0.3f),
                alpha(0.1f,0.3f),
                alpha(0.8f,0.1f),
                alpha(0.2f,0.1f),
                alpha(0.9f,0.1f),
                delay(3.0f),
                alpha(0.0f,0.1f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        AudioManager.instance.setMusicVol(0.15f);
                    }
                })
        ));
    }

    private void addMagnet(){
        magnet = new Image(Assets.instance.tree.magnet);
        magnet.setPosition(-magnet.getWidth(),Constants.VIEWPORT_HEIGHT-magnet.getWidth());
        stage.addActor(magnet);

    }

    private void moveMagnet(){
        magnet.addAction(sequence(
                delay(7.9f+7.0f),
                moveTo(50,magnet.getY(), 1.9f, Interpolation.bounceOut),
                delay(0.8f),
                moveBy(-500,0,0.6f),
                delay(2.0f+12.0f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        if(Constants.WITH_CV)
                            game.setScreen(new TutorialCvScreen(game), ScreenTransitionFade.init(1));
                        else
                            game.setScreen(new TutorialScreen(game), ScreenTransitionFade.init(1));
                        //game.setScreen(new TreeScreen(game,true), ScreenTransitionFade.init(1));
                    }
                })
        ));
    }

    private void moveGears(){
        int byX = -80;
        int byY = 380;
        float initDelay = 9.0f+7.0f;
        float delayAction = 0.3f;

        g1.addAction(sequence(
                delay(initDelay),
                moveBy(byX,byY,1.3f,Interpolation.bounce),
                delay(delayAction),
                moveBy(-500,0,0.6f)
        ));
        g2.addAction(sequence(
                delay(initDelay),
                moveBy(byX,byY,1.3f,Interpolation.bounce),
                delay(delayAction),
                moveBy(-500,0,0.6f)
        ));
        g3.addAction(sequence(
                delay(initDelay),
                moveBy(byX,byY,1.3f,Interpolation.bounce),
                delay(delayAction),
                moveBy(-500,0,0.6f)
        ));
        g4.addAction(sequence(
                delay(initDelay),
                moveBy(byX,byY,1.3f,Interpolation.bounce),
                delay(delayAction),
                moveBy(-500,0,0.6f)
        ));
        g5.addAction(sequence(
                delay(initDelay),
                moveBy(byX,byY,1.3f,Interpolation.bounce),
                delay(delayAction),
                moveBy(-500,0,0.6f)
        ));
        g6.addAction(sequence(
                delay(initDelay),
                moveBy(byX,byY,1.3f,Interpolation.bounce),
                delay(delayAction),
                moveBy(-500,0,0.6f)
        ));

    }
}
