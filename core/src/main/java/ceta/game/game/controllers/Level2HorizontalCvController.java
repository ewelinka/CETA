package ceta.game.game.controllers;

import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.objects.TubePieceAnimated;
import ceta.game.managers.AnimatedRoboticTubeManager;
import ceta.game.managers.CVBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 12/5/16.
 */
public class Level2HorizontalCvController extends Level1HorizontalCvController {
    private static final String TAG = Level2HorizontalCvController.class.getName();
    private AnimatedRoboticTubeManager roboticArmManager;

    public Level2HorizontalCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }


    @Override
    protected void localInit () { // TODO extract digital-representation thing and make generic part
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        roboticArmManager = new AnimatedRoboticTubeManager(stage);
        cvBlocksManager = new CVBlocksManager(game,stage);

        level = new LevelHorizontal(stage, levelParams);
        cameraHelper.setTarget(null);
        score = 0;
        cvBlocksManager.init();
        roboticArmManager.init();
    }

    @Override
    protected void updateDigitalRepresentations() {
        updateArmPieces(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemoveValues());
    }


    @Override
    protected void updateArmPieces(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveValues){
        roboticArmManager.updateAnimated(toAdd,toRemoveValues);
    }


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
