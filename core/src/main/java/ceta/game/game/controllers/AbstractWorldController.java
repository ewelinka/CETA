package ceta.game.game.controllers;

import java.util.Date;

import ceta.game.game.levels.Level;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.FinalScreen;
import ceta.game.screens.MenuScreen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.CameraHelper;

import ceta.game.util.GamePreferences;
import ceta.game.util.VirtualBlocksManagerOSC;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

/**
 * Created by ewe on 8/23/16.
 */
public abstract class  AbstractWorldController extends InputAdapter implements Disposable, OSCListener {
    private static final String TAG = AbstractWorldController.class.getName();
    public CameraHelper cameraHelper;
    public Level level;
    public short score;
    public DirectedGame game;


    protected boolean countdownOn;
    protected float coutdownCurrentTime;

    public boolean weWon;


    public void init (DirectedGame game) {
        cameraHelper = new CameraHelper();
        this.game = game;

        actionSubmitInit();
        adjustCamera();
        weWon = false;
    }

    private void adjustCamera(){
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            // moveCamera(0, -512/2);
            // cameraHelper.addZoom(-0.3f);
        }
    }

    public abstract void update(float delta);



    private void actionSubmitInit(){
        countdownOn = false;
        coutdownCurrentTime = GamePreferences.instance.countdownMax;
    }



    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {

            Gdx.app.debug(TAG, "Game world resetted");
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

    private void backToMenu () {
        // switch to menu screen
        ScreenTransition transition = ScreenTransitionFade.init(1);

        game.setScreen(new MenuScreen(game), transition);

    }

    public void goToFinalScreen () {
        // switch to menu screen
        ScreenTransition transition = ScreenTransitionFade.init(1);

        game.setScreen(new FinalScreen(game), transition);

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
    
    
    
    
    
    @Override
	public void acceptMessage(Date arg0, OSCMessage arg1) {
	  Gdx.app.log(TAG, "message received!!!");
	
		for(int i =0;i< arg1.getArguments().size();i++){
			 Gdx.app.log(TAG,"arg("+i+")="+arg1.getArguments().get(i));
		}
		 Gdx.app.log(TAG, "----------- end of message ------------");
	}



    public boolean getCountdownOn(){
        return countdownOn;
    }

    public int getCoutdownCurrentTime(){
        return (int)(coutdownCurrentTime);
    }



    public void setCountdownOn(boolean isOn){

        countdownOn = isOn;
        coutdownCurrentTime = GamePreferences.instance.countdownMax;
    }

    public boolean getWeWon(){
        return weWon;
    }

    public void setWeWon(boolean toSet){
        weWon = toSet;
    }

    public void resetScore(){
        score = 0;
    }
    
}
