package ceta.game.game.controllers;

import java.util.Date;

import ceta.game.game.levels.AbstractLevel;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.FinalScreen;
import ceta.game.screens.MenuScreen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.CameraHelper;

import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;


/**
 * Created by ewe on 8/23/16.
 */
public abstract class  AbstractWorldController extends InputAdapter implements Disposable {
    private static final String TAG = AbstractWorldController.class.getName();
    protected Rectangle r1 = new Rectangle();
    protected Rectangle r2 = new Rectangle();
    protected Stage stage;
    public CameraHelper cameraHelper;
    public AbstractLevel level;
    public short score;
    public DirectedGame game;
    protected boolean countdownOn;
    protected float countdownCurrentTime;
    private ScreenTransition oneSegFadeIn;


    public abstract void update(float delta);
    protected abstract void testCollisions();

    protected abstract void localInit();

    public void init (DirectedGame game) {
        this.game = game;
        cameraHelper = new CameraHelper();
        cameraHelper.setTarget(null);
        score = 0;
        oneSegFadeIn = ScreenTransitionFade.init(1);

        actionSubmitInit();
        adjustCamera();
    }

    private void adjustCamera(){
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            // moveCamera(0, -512/2);
            // cameraHelper.addZoom(-0.3f);
        }
    }

    private void actionSubmitInit(){
        countdownOn = false;
        countdownCurrentTime = GamePreferences.instance.countdownMax;
    }


    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            setCountdownOn(true);
            Gdx.app.debug(TAG, "Action submit");
        }
        // Toggle camera follow
        else if (keycode == Input.Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.bruno);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        // Back to Menu
        else if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
            backToMenu();
        }
        return false;
    }

    public void backToMenu () {
        // switch to menu screen
       // ScreenTransition transition = ScreenTransitionFade.init(1);
        game.setScreen(new MenuScreen(game), oneSegFadeIn);
    }

    public void goToFinalScreen () {
        // switch to final screen
       // ScreenTransition transition = ScreenTransitionFade.init(1);

        game.setScreen(new FinalScreen(game), oneSegFadeIn);

    }

    public void handleDebugInput (float deltaTime) {
        if (Gdx.app.getType() != Application.ApplicationType.Desktop) return;
        if (!cameraHelper.hasTarget(level.bruno)) {
            // Camera Controls (move)
            float camMoveSpeed = 100 * deltaTime;

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) moveCamera(0, camMoveSpeed);
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) moveCamera(0, -camMoveSpeed);
            // default
            if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);
        }
        // Camera Controls (zoom)
        float camZoomSpeed = 1 * deltaTime;
        // mas lejos estamos
        if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
        // mas cerca
        if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
        // default zoom
        if (Gdx.input.isKeyPressed(Input.Keys.SLASH)) cameraHelper.setZoom(1);
    }


    private void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }



    public boolean getCountdownOn(){
        return countdownOn;
    }

    public int getCountdownCurrentTime(){
        return (int)(countdownCurrentTime);
    }

    public void setCountdownOn(boolean isOn){
        countdownOn = isOn;
        countdownCurrentTime = GamePreferences.instance.countdownMax;
    }


    public void resetScore(){
        score = 0;
    }

    public int getLevelMinimumNumber(){
        return level.getMinimumNumber();
    }

    public boolean isLevelNumberLineVisible(){
        return level.isNumberLineVisible();
    }
    
}
