package ceta.game.game.controllers;

import ceta.game.game.levels.Level1Horizontal;
import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.objects.ArmPiece;
import ceta.game.managers.CVBlocksDesktopManager;
import ceta.game.managers.CVBlocksManager;
import ceta.game.managers.RoboticArmManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 12/5/16.
 */
public class Level1HorizontalDesktopCvController extends CvController {
    private static final String TAG = Level1HorizontalDesktopCvController.class.getName();
    private RoboticArmManager roboticArmManager;

    public Level1HorizontalDesktopCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }


    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        roboticArmManager = new RoboticArmManager(stage);
        cvBlocksManager = new CVBlocksDesktopManager(game,stage);

        level = new Level1Horizontal(stage, levelParams, this);

        cvBlocksManager.init();
        roboticArmManager.init();
    }

    @Override
    protected void updateDigitalRepresentations() {
        updateArmPieces(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemove());
    }

    @Override
    protected void testCollisionsInController(boolean isDynamic) {
        Gdx.app.log(TAG," testCollisionsStatic ");
        ArmPiece lastArm = getLastArmPiece();
        if (!roboticArmManager.isUpdatingArmPiecesPositions()) {
            if(isDynamic)
                testCollisionsHorizontalDynamic(lastArm);
            else
                testCollisionsHorizontalStatic(lastArm);

        }
    }


    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }


    protected void updateArmPieces(ArrayList<Pair> toAdd, ArrayList<Integer> toRemove){
        roboticArmManager.update(toAdd,toRemove);
    }
}
