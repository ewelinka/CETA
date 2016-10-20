package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1;
import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Price;
import ceta.game.screens.DirectedGame;
import ceta.game.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1HorizontalController extends AbstractWorldController{
    private static final String TAG = Level1HorizontalController.class.getName();
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private RoboticArmManager roboticArmManager;
    private VirtualBlocksManagerOSC virtualBlocksManager;
    private Stage stage;

    public Level1HorizontalController(DirectedGame game, Stage stage) {
        this.stage = stage;
        roboticArmManager = new RoboticArmManager(stage);
        // TODO change after wizard of oz
        virtualBlocksManager = new VirtualBlocksManagerOSC(stage);
        super.init(game);
        localInit();
    }

    private void localInit () {
        Gdx.app.log(TAG," local init");
        level = new Level1(stage, GamePreferences.instance.lastLevel);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();
        roboticArmManager.init();
    }


    @Override
    public void update (float deltaTime) {
        // winning condition
        testCollisions();
//        if (score >= level.getOperationsNumber()) {
//            Gdx.app.log(TAG," yupiiiiii we won!");
//            goToFinalScreen();
//        }

        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()

        if (GamePreferences.instance.actionSubmit) {
            // if we are counting
            if (countdownOn) {
                // we shake bruno
                level.bruno.shake();
                // if we reached the time
                if (countdownCurrentTime < 0) {
                    Gdx.app.log(TAG, "wowowoowow action submit!");
                    virtualBlocksManager.updateDetected();
                    // computer vision manager update detected
                    // cvManager.update();

                    updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }

        } else {
            virtualBlocksManager.updateDetected();
            // computer vision manager update detected
            // cvManager.update();
            updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
        }
        cameraHelper.update(deltaTime);
    }

    @Override
    public void dispose() {

    }

    private void testCollisions () {
        ArmPiece lastArm = getLastArmPiece();
        if (lastArm != null) {
            // we check first when the move action is over
            // if not we have problems with pieces passing thought the coin
            if (lastArm.getActions().size == 0) {
                r1.set(lastArm.getX() + lastArm.getWidth(), lastArm.getY(), 2, lastArm.getHeight());
                r2.set(level.price.getX(), level.price.getY() + level.price.getHeight() / 2, level.price.bounds.width, level.price.bounds.height);

                if (r1.overlaps(r2))
                    onCollisionBrunoWithGoldCoin(level.price);
            }
        }

    }


    private void onCollisionBrunoWithGoldCoin(Price goldcoin) {
        if(goldcoin.getActions().size == 0){ // we act just one time!
            AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
            score += 1;
            //TODO some nice yupi animation
            if(score<level.getOperationsNumber()) {
                if (level.isDynamic())
                    goldcoin.moveToNewPositionStartAbove(level.bruno.getX() + level.bruno.getWidth());
                else
                    goldcoin.moveToNewPosition(level.bruno.getX() + level.bruno.getWidth());
            }
            else
                goToFinalScreen();
        }

    };


    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }



    private void updateArmPieces(ArrayList toAdd, ArrayList toRemove){
        roboticArmManager.update(toAdd,toRemove);
    }

    public VirtualBlocksManagerOSC getVirtualBlocksManagerOSC(){
        //return  new VirtualBlocksManagerOSC(stage);
        return virtualBlocksManager;
    }


}

