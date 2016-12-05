package ceta.game.game.controllers;

import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.objects.ArmPiece;
import ceta.game.managers.CVBlocksManager;
import ceta.game.managers.RoboticArmManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 12/5/16.
 */
public class Level1HorizontalCvController extends CvController {
    private static final String TAG = Level1HorizontalCvController.class.getName();
    private RoboticArmManager roboticArmManager;

    public Level1HorizontalCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }


    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        roboticArmManager = new RoboticArmManager(stage);
        cvBlocksManager = new CVBlocksManager(game,stage);

        level = new LevelHorizontal(stage, levelParams);

        cvBlocksManager.init();
        roboticArmManager.init();
    }

    @Override
    protected void updateDigitalRepresentations() {
        updateArmPieces(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemove());
    }

    @Override
    protected void testCollisions () {
        ArmPiece lastArm = getLastArmPiece();
        if (!roboticArmManager.isUpdatingArmPiecesPositions()) {
            testCollisionsHorizontal(lastArm);

        }
    }

    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }


    protected void updateArmPieces(ArrayList toAdd, ArrayList toRemove){
        roboticArmManager.update(toAdd,toRemove);
    }
}
