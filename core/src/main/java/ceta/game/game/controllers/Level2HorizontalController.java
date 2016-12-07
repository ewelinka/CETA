package ceta.game.game.controllers;

import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.objects.ArmPieceAnimated;
import ceta.game.managers.AnimatedRoboticArmManager;
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

    public Level2HorizontalController(DirectedGame game, Stage stage, int levelNr) {
        super(game,stage,levelNr);
    }

    @Override
    protected void localInit () { // TODO extract digital-representation thing and make generic part
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        roboticArmManager = new AnimatedRoboticArmManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);

        level = new LevelHorizontal(stage, levelParams);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();
        roboticArmManager.init();
    }


    @Override
    protected void updateDigitalRepresentations() {
        updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
    }


    @Override
    protected void updateArmPieces(ArrayList toAdd, ArrayList toRemoveValues){
        roboticArmManager.updateAnimated(toAdd,toRemoveValues);
    }

//    @Override
//    protected void testCollisions () {
//        ArmPieceAnimated lastArm = getLastAnimatedArmPiece();
//        if (lastArm != null && !roboticArmManager.isUpdatingArmPiecesPositions()) {
//            // we set 4px x 4px box at the right end (X), in the middle (Y)
//            r1.set(lastArm.getX() + lastArm.getWidth(),
//                    lastArm.getY()+lastArm.getHeight()/2 - 2, // two pixels below the middle
//                    4, 4);
//            r2.set(level.price.getX(),
//                    level.price.getY() + level.price.getHeight() / 2 - 2,
//                    level.price.bounds.width, 4);
//
//            if (r1.overlaps(r2)) {
//                onCollisionBrunoWithPrice(level.price);
//            }
//        }
//    }

    @Override
    protected void testCollisionsInController (boolean isDynamic) {
        ArmPieceAnimated lastArm = getLastAnimatedArmPiece();
        if (!roboticArmManager.isUpdatingArmPiecesPositions()) {
            if(isDynamic)
                testCollisionsHorizontalDynamic(lastArm);
            else
                testCollisionsHorizontalStatic(lastArm);
        }
    }



    public ArmPieceAnimated getLastAnimatedArmPiece(){
        return roboticArmManager.getLastAnimatedArmPiece();
    }


}
