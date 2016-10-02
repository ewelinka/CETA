package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1;
import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Coin;
import ceta.game.screens.DirectedGame;
import ceta.game.util.*;
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
    //private DirectedGame game;


    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private RoboticArmManager roboticArmManager;
    private VirtualBlocksManagerOSC virtualBlocksManager;


    private Stage stage;

    public Level1Controller (DirectedGame game, Stage stage) {
       // game = game;
        this.stage = stage;
        roboticArmManager = new RoboticArmManager(stage);
        // TODO change after wizard of oz
        virtualBlocksManager = new VirtualBlocksManagerOSC(stage);
        super.init(game);
        localInit();
    }

    private void localInit () {
        initLevel();
        virtualBlocksManager.init();
        roboticArmManager.init();
    }


    private void initLevel(){
        //it will load level data
        level = new Level1(stage);
        cameraHelper.setTarget(null);
        score = 0;

    }


    @Override
    public void update (float deltaTime) {
        // winning condition
        if (score >= 3) {
            goToFinalScreen();

        }
        handleDebugInput(deltaTime);
        level.update(deltaTime);
        virtualBlocksManager.updateDetected();
        previous_detected = Arrays.copyOf(detected_numbers, detected_numbers.length);
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
        ArmPiece lastArm = getLastArmPiece();
        if(lastArm != null){
            // we check first when the move action is over
            // if not we have problems with pieces passing thought the coin
            if (lastArm.getActions().size == 0){
                r1.set(lastArm.getX()+lastArm.getWidth(), lastArm.getY(), 2, lastArm.getHeight());

                r2.set(level.coin.getX(), level.coin.getY(), level.coin.bounds.width, level.coin.bounds.height);
                if (r1.overlaps(r2))
                    onCollisionBrunoWithGoldCoin(level.coin);
            }
        }
    }

    private void onCollisionBrunoWithGoldCoin(Coin goldcoin) {
        //goldcoin.collected = true;
        AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
        score += 1;
        goldcoin.setNewPosition(level.bruno.getX()+level.bruno.getWidth());
    };



    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }

    private void updateArmPieces(){
        // set "to add" and "to remove" in arm pieces manager
        roboticArmManager.update(toRemove,toAdd,remove,add);
    }

    public VirtualBlocksManagerOSC getVirtualBlocksManagerOSC(){
        return virtualBlocksManager;
    }


}

