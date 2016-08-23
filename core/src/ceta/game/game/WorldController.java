package ceta.game.game;

import ceta.game.game.levels.Level;
import ceta.game.game.levels.LevelOne;
import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Latter;
import ceta.game.screens.DirectedGame;
import ceta.game.util.CameraHelper;
import ceta.game.util.Constants;
import ceta.game.util.LatterManager;
import ceta.game.util.VirtualBlocksManager;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;

import java.util.Arrays;


/**
 * Created by ewe on 7/25/16.
 */
public class WorldController extends InputAdapter implements Disposable {
    private static final String TAG = WorldController.class.getName();
    private DirectedGame game;
    public CameraHelper cameraHelper;
    public LevelOne level;
    //public Level level;
    // Rectangles for collision detection
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private LatterManager latterManager;
    private VirtualBlocksManager virtualBlocksManager;

    private short [] detected_numbers;
    private short [] previous_detected;
    private short [] remove;
    private short [] add;
    private short toRemove;
    private short toAdd;
    private short currentDiff;

    private Stage stage;

    public WorldController (DirectedGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        latterManager = new LatterManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);
        init();
    }

    private void init () {
        cameraHelper = new CameraHelper();
        initLevel();
        initValues();
        virtualBlocksManager.init();
        latterManager.init();
    }

    private void initLevel(){
        //it will load level data
        level = new LevelOne(stage);
        cameraHelper.setTarget(null);

    }

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

    public void update (float deltaTime) {
        handleDebugInput(deltaTime);
        level.update(deltaTime);
        virtualBlocksManager.updateDetected();
        //previous_detected = previous_detected;
        previous_detected = Arrays.copyOf(detected_numbers, detected_numbers.length);
        // System.arraycopy( detected_numbers, 0, previous_detected, 0, detected_numbers.length );
        detected_numbers = virtualBlocksManager.getDetectedBlocks();
        findDifferences();
        updateLatters();

        // TODO check if it should goes to the end
        testCollisions();

        cameraHelper.update(deltaTime);

    }

    @Override
    public void dispose() {

    }

    private void testCollisions () {
        // TODO check for top Y and x range
        // now if the coin passes by the middle if also works (make sense! but not for us!)
        Latter l = getLastLatter();
        if(l != null){
            r1.set(l.getX(), l.getY()+l.getHeight() - 4, l.getWidth(), 8);
            //Gdx.app.debug(TAG, "latter! ");
            r2.set(level.coin.getX(), level.coin.getY(), level.coin.bounds.width, level.coin.bounds.height);

            if (r1.overlaps(r2))
                Gdx.app.debug(TAG, "collision coin - latter! ");
        }

    }

    private void findDifferences(){
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

    public Latter getLastLatter(){
        return latterManager.getLastLatter();
    }

    private void updateLatters(){
        // set "to add" and "to remove" in latter manager
        latterManager.update(toRemove,toAdd,remove,add);
    }

    private void moveCamera (float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
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

    private void handleDebugInput (float deltaTime) {
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


}
