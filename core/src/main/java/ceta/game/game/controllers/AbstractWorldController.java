package ceta.game.game.controllers;

import java.util.Date;

import ceta.game.game.levels.Level;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.FinalScreen;
import ceta.game.screens.MenuScreen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.CameraHelper;

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

    public short [] detected_numbers;
    public short [] previous_detected;
    public short [] last_sent;
    public short [] remove;
    public short [] add;
    public short toRemove;
    public short toAdd;
    public short currentDiff;

    protected boolean countdownOn;
    protected float countdownMax;
    protected float coutdownCurrentTime;
    protected boolean actionSubmitDelayMode;

    public void init (DirectedGame game) {
        cameraHelper = new CameraHelper();
        this.game = game;
        initValues();
        actionSubmitInit();
        adjustCamera();
    }

    private void adjustCamera(){
        if (Gdx.app.getType() == Application.ApplicationType.Android){
            // moveCamera(0, -512/2);
            // cameraHelper.addZoom(-0.3f);
        }
    }

    public abstract void update(float delta);

    private void initValues(){
        detected_numbers = new short [5];
        previous_detected = new short [5];
        last_sent = new short [5];
        remove = new short [5];
        add = new short [5];

        for(short i = 0; i<5;i++){
            detected_numbers[i] = 0;
            previous_detected[i] = 0;
            last_sent[i] = 0;
            remove[i] = 0;
            add[i] = 0;
        }

        toRemove = 0;
        toAdd = 0;
        currentDiff = 0;
    }

    private void actionSubmitInit(){
        countdownOn = false;
        countdownMax = 5f;
        coutdownCurrentTime = countdownMax;
        actionSubmitDelayMode = true;


    }

    public void findDifferences(short [] p_detected, short [] detected){
        // ojo!!! lo que se corresponde a la pieza 1 esta en la posicion 0 !!
        toRemove = 0;
        toAdd = 0;
        for(short i = 0; i<5;i++){
            //if(i==0) Gdx.app.debug(TAG,previous_detected[i]+" "+detected_numbers[i]);
            currentDiff = (short)(p_detected[i] - detected[i]);
            if ( currentDiff < 0){
                Gdx.app.debug(TAG,"we should add "+currentDiff+" to:"+i+" position");
                add[i] = (short)Math.abs(currentDiff);
                remove[i] = 0;
                toAdd +=1;
            }
            else if (currentDiff > 0){
                Gdx.app.debug(TAG,"we should remove "+currentDiff+" to:"+i+" position");
                remove[i] = currentDiff;
                add[i] = 0;
                toRemove +=1;
            }
            else{
                remove[i] = 0;
                add[i] = 0;
            }

        }

    }

    @Override
    public boolean keyUp (int keycode) {
        // Reset game world
        if (keycode == Input.Keys.R) {
            initValues();
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

    public boolean hasActionSubmitDelay(){
        return actionSubmitDelayMode;
    }

    public boolean getCountdownOn(){
        return countdownOn;
    }

    public int getCoutdownCurrentTime(){
        return (int)(coutdownCurrentTime);
    }



    public void setCountdownOn(boolean isOn){

        countdownOn = isOn;
        coutdownCurrentTime = countdownMax;
    }
    
    
}
