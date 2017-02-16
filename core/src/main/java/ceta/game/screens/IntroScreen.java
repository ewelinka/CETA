package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 2/14/17.
 */
public class IntroScreen extends AbstractGameScreen {
    private Image imgBackground,b1,b2,b3,b4,b5,magnet,black,g1,g2,g3,g4;
    private int[][] gearsPos;

    public IntroScreen(DirectedGame game) {
        super(game);
        gearsPos = TreeScreen.gearsPositions;
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
        moveBrunos();

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
        addTouch(b1);
        stage.addActor(b1);

        b2.setPosition(-80,166);
        b2.setScale(factor,factor);
        addTouch(b2);
        stage.addActor(b2);

        b3.setPosition(665,172);
        b3.setScale(-factor, factor);
        b3.setColor(1,1,1,0.9f);
        addTouch(b3);
        stage.addActor(b3);

        b4.setPosition(-80,166);
        b4.setScale(factor);
        addTouch(b4);
        stage.addActor(b4);

        b5.setPosition(-80,166);
        b5.setScale(factor);
        addTouch(b5);
        stage.addActor(b5);

    }

    private void addBlack(){

    }

    private void moveBrunos(){
        // right side
        b1.addAction(sequence(
                delay(1.4f),
                moveTo(596,172,0.8f),
                delay(1.0f),
                moveTo(482,193,0.8f),
                delay(0.4f),
                moveTo(410,191,0.8f),
                delay(0.4f),
                moveTo(397,183,0.8f)
        ));

        b3.addAction(sequence(
                delay(2.8f),
                moveTo(597,172,1.2f),
                delay(1.9f),
                moveTo(482,193,1.4f)
        ));

        //left side
        b2.addAction(sequence(
                delay(0.4f),
                moveTo(-14,166,0.9f),
                delay(1.0f),
                moveTo(44,202,0.9f),
                delay(0.4f),
                moveTo(210,187,0.9f)
        ));

        b4.addAction(sequence(
                delay(1.0f),
                moveTo(-14,166,0.9f),
                delay(1.0f),
                moveTo(44,202,0.9f),
                delay(0.4f),
                moveTo(180,208,0.9f)
        ));

        b5.addAction(sequence(
                delay(1.8f),
                moveTo(-14,166,0.9f),
                delay(1.0f),
                moveTo(44,202,0.9f),
                delay(0.4f),
                moveTo(142,187,0.9f)
        ));


    }

    private void addTouch(final Image img){
        img.setTouchable(Touchable.enabled);
        // to move around
        img.addListener(new ActorGestureListener() {

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                img.setPosition(img.getX()+deltaX,img.getY()+deltaY);
                Gdx.app.log(TAG," === x "+img.getX()+" y "+img.getY());
            }


        });
    }
}
