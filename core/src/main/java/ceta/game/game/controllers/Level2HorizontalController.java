package ceta.game.game.controllers;

import ceta.game.game.levels.Level1;
import ceta.game.game.objects.ArmPieceAnimated;
import ceta.game.managers.AnimatedRoboticArmManager;
import ceta.game.managers.VirtualBlocksManagerOSC;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import ceta.game.managers.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 10/27/16.
 */
public class Level2HorizontalController extends Level1HorizontalController {
    private static final String TAG = Level2HorizontalController.class.getName();
    private AnimatedRoboticArmManager roboticArmManager;

    public Level2HorizontalController(DirectedGame game, Stage stage) {
        super(game,stage);
    }

    @Override
    protected void localInit () {
        roboticArmManager = new AnimatedRoboticArmManager(stage);
        virtualBlocksManager = new VirtualBlocksManagerOSC(stage);

        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        level = new Level1(stage, GamePreferences.instance.lastLevel);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();
        roboticArmManager.init();
    }


    @Override
    public void update (float deltaTime) {
        testCollisions(); // winning condition checked

        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()
        virtualBlocksManager.updateDetected(); // update virtual blocks

        if (GamePreferences.instance.actionSubmit) {
            // if we are counting
            if (countdownOn) {
                level.bruno.shake(); // we shake bruno
                if (countdownCurrentTime < 0) {  // if we reached the time
                    // remove values NOT ids
                    updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
                    virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }
        } else {
            updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
            virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
        }
        cameraHelper.update(deltaTime);
    }

    @Override
    public void dispose() {

    }

    @Override
    protected void updateArmPieces(ArrayList toAdd, ArrayList toRemoveValues){
        roboticArmManager.updateAnimated(toAdd,toRemoveValues);
    }

    @Override
    protected void testCollisions () {
        ArmPieceAnimated lastArm = getLastAnimatedArmPiece();
        if (lastArm != null && !roboticArmManager.isUpdatingArmPiecesPositions() && !roboticArmManager.isExpanding()) {
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


    public ArmPieceAnimated getLastAnimatedArmPiece(){
        return roboticArmManager.getLastAnimatedArmPiece();
    }


}
