package ceta.game.game.controllers;

import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.objects.TubePieceAnimated;
import ceta.game.managers.AnimatedRoboticTubeManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 10/27/16.
 */
public class Level2HorizontalController extends Level1HorizontalController {
    private static final String TAG = Level2HorizontalController.class.getName();
    private AnimatedRoboticTubeManager roboticArmManager;

    public Level2HorizontalController(DirectedGame game, Stage stage, int levelNr) {
        super(game,stage,levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        roboticArmManager = new AnimatedRoboticTubeManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);

        level = new LevelHorizontal(stage, levelParams, this);
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
    protected void updateArmPieces(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveValues){
        roboticArmManager.updateAnimated(toAdd,toRemoveValues);
    }

//    @Override
//    protected void testCollisions () {
//        TubePieceAnimated lastArm = getLastAnimatedArmPiece();
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
        TubePieceAnimated lastArm = getLastAnimatedArmPiece();
        if (!roboticArmManager.isUpdatingArmPiecesPositions()) {
            if(isDynamic)
                testCollisionsHorizontalDynamic(lastArm);
            else
                testCollisionsHorizontalStatic(lastArm);
        }
    }



    public TubePieceAnimated getLastAnimatedArmPiece(){
        return roboticArmManager.getLastAnimatedArmPiece();
    }


}
