package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1;
import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Price;
import ceta.game.managers.RoboticArmManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.managers.VirtualBlocksManagerOSC;
import ceta.game.screens.DirectedGame;
import ceta.game.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1HorizontalController extends AbstractWorldController{
    private static final String TAG = Level1HorizontalController.class.getName();
    private RoboticArmManager roboticArmManager;
    protected VirtualBlocksManagerOSC virtualBlocksManager;


    public Level1HorizontalController(DirectedGame game, Stage stage) {
        this.stage = stage;
        super.init(game);
        localInit();
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        roboticArmManager = new RoboticArmManager(stage);
        virtualBlocksManager = new VirtualBlocksManagerOSC(stage);

        level = new Level1(stage, GamePreferences.instance.lastLevel);

        virtualBlocksManager.init();
        roboticArmManager.init();
    }


    @Override
    public void update (float deltaTime) {

        testCollisions(); // winning condition checked

        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()
        virtualBlocksManager.updateDetected();

        if (GamePreferences.instance.actionSubmit) {
            if (countdownOn) { // if we are counting
                level.bruno.shake(); // we shake bruno
                if (countdownCurrentTime < 0) { // if we reached the time
                    Gdx.app.log(TAG, "wowowoowow action submit!");
                    updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
                    virtualBlocksManager.resetDetectedAndRemoved(); //reset detected
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }

        } else {
            updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
            virtualBlocksManager.resetDetectedAndRemoved();
        }
        cameraHelper.update(deltaTime);
    }

    @Override
    public void dispose() {

    }

    protected void testCollisions () {
        ArmPiece lastArm = getLastArmPiece();
        if (lastArm != null && !roboticArmManager.isUpdatingArmPiecesPositions()) {
            // we set 4px x 4px box at the right end (X), in the middle (Y)
            r1.set(lastArm.getX() + lastArm.getWidth(),
                    lastArm.getY()+lastArm.getHeight()/2 - 2, // two pixels below the middle
                    4, 4);
            r2.set(level.price.getX(),
                    level.price.getY() + level.price.getHeight() / 2 - 2,
                    level.price.bounds.width, 4);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithGoldCoin(level.price);
            }
        }
    }


    protected void onCollisionBrunoWithGoldCoin(Price goldcoin) {
        Gdx.app.log(TAG, "NO updates in progress and collision!");
        if (goldcoin.getActions().size == 0) { // we act just one time!
            AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
            score += 1;
            //TODO some nice yupi animation
            if (score < level.getOperationsNumberToPass()) {
                goldcoin.wasCollected();

            } else
                goToFinalScreen();
        }
    }


    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }


    protected void updateArmPieces(ArrayList toAdd, ArrayList toRemove){
        roboticArmManager.update(toAdd,toRemove);
    }

    public VirtualBlocksManagerOSC getVirtualBlocksManagerOSC(){
        //return  new VirtualBlocksManagerOSC(stage);
        return virtualBlocksManager;
    }


}

