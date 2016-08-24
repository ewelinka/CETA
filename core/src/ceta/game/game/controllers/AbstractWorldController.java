package ceta.game.game.controllers;

import ceta.game.game.levels.Level;
import ceta.game.util.CameraHelper;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ewe on 8/23/16.
 */
public abstract class  AbstractWorldController extends InputAdapter implements Disposable {
    private static final String TAG = AbstractWorldController.class.getName();
    public CameraHelper cameraHelper;
    public Level level;

    public short [] detected_numbers;
    public short [] previous_detected;
    public short [] remove;
    public short [] add;
    public short toRemove;
    public short toAdd;
    public short currentDiff;

    public void init () {
        cameraHelper = new CameraHelper();
        initValues();
    }

    public abstract void update(float delta);

    private void initValues(){
        detected_numbers = new short [5];
        previous_detected = new short [5];
        remove = new short [5];
        add = new short [5];

        for(short i = 0; i<5;i++){
            detected_numbers[i] = 0;
            previous_detected[i] = 0;
            remove[i] = 0;
            add[i] = 0;
        }

        toRemove = 0;
        toAdd = 0;
        currentDiff = 0;
    }

    public void findDifferences(){
        // ojo!!! lo que se corresponde a la pieza 1 esta en la posicion 0 !!
        toRemove = 0;
        toAdd = 0;
        for(short i = 0; i<5;i++){
            //if(i==0) Gdx.app.debug(TAG,previous_detected[i]+" "+detected_numbers[i]);
            currentDiff = (short)(previous_detected[i] - detected_numbers[i]);
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
            init();
            Gdx.app.debug(TAG, "Game world resetted");
        }
        // Toggle camera follow
        else if (keycode == Input.Keys.ENTER) {
            cameraHelper.setTarget(cameraHelper.hasTarget() ? null: level.bruno);
            Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
        }
        // Back to Menu
//        else if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
//            backToMenu();
//        }
        return false;
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
}
