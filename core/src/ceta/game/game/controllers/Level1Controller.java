package ceta.game.game.controllers;

import ceta.game.game.levels.Level1;
import ceta.game.game.objects.ArmPiece;
import ceta.game.screens.DirectedGame;
import ceta.game.util.CameraHelper;
import ceta.game.util.RoboticArmManager;
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
 * Created by ewe on 8/23/16.
 */
public class Level1Controller extends AbstractWorldController{
    private static final String TAG = Level1Controller.class.getName();
    private DirectedGame game;

    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private RoboticArmManager roboticArmManager;
    private VirtualBlocksManager virtualBlocksManager;


    private Stage stage;

    public Level1Controller (DirectedGame game, Stage stage) {
        this.game = game;
        this.stage = stage;
        roboticArmManager = new RoboticArmManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);
        localInit();
    }

    private void localInit () {
        super.init();
        initLevel();
        virtualBlocksManager.init();
        roboticArmManager.init();
    }


    private void initLevel(){
        //it will load level data
        level = new Level1(stage);
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
        findDifferences();
        updateArmPieces();

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
        ArmPiece l = getLastArmPiece();
        if(l != null){
            r1.set(l.getX()+l.getWidth()- 4, l.getY(), 8, l.getHeight());
            //Gdx.app.debug(TAG, "latter! ");
            r2.set(level.coin.getX(), level.coin.getY(), level.coin.bounds.width, level.coin.bounds.height);

            if (r1.overlaps(r2))
                Gdx.app.debug(TAG, "collision coin - arm! ");
        }

    }



    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }

    private void updateArmPieces(){
        // set "to add" and "to remove" in arm pieces manager
        roboticArmManager.update(toRemove,toAdd,remove,add);
    }


}

