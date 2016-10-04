package ceta.game.game.controllers;

/**
 * Created by ewe on 8/23/16.
 */

import ceta.game.game.levels.Test;
import ceta.game.game.objects.Latter;
import ceta.game.screens.DirectedGame;
import ceta.game.util.CameraHelper;
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
public class WorldController extends AbstractWorldController {
    private static final String TAG = WorldController.class.getName();
    //private DirectedGame game;
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private LatterManager latterManager;
    private VirtualBlocksManager virtualBlocksManager;

    private Stage stage;

    public WorldController (DirectedGame game, Stage stage) {
        //this.game = game;
        this.stage = stage;
        latterManager = new LatterManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);
        super.init(game);
        localInit();
    }

    private void localInit(){
        initLevel();
        virtualBlocksManager.init();
        latterManager.init();
    }

    private void initLevel(){
        //it will load level data
        level = new Test(stage);
        cameraHelper.setTarget(null);

    }

    @Override
    public void update (float deltaTime) {
        handleDebugInput(deltaTime);
        level.update(deltaTime);
        virtualBlocksManager.updateDetected();
        //previous_detected = previous_detected;
        previous_detected = Arrays.copyOf(detected_numbers, detected_numbers.length);
        // System.arraycopy( detected_numbers, 0, previous_detected, 0, detected_numbers.length );
        detected_numbers = virtualBlocksManager.getDetectedBlocks();
        findDifferences(previous_detected,detected_numbers);
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


    public Latter getLastLatter(){
        return latterManager.getLastLatter();
    }

    private void updateLatters(){
        // set "to add" and "to remove" in latter manager
        latterManager.update(toRemove,toAdd,remove,add);
    }




}