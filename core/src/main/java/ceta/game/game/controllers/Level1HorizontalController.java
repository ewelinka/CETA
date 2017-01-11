package ceta.game.game.controllers;

import ceta.game.game.levels.Level1Horizontal;
import ceta.game.game.objects.ArmPiece;
import ceta.game.managers.RoboticArmManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1HorizontalController extends NoCvController{
    private static final String TAG = Level1HorizontalController.class.getName();
    private RoboticArmManager roboticArmManager;
   // protected VirtualBlocksManager virtualBlocksManager;

    public Level1HorizontalController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }


    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        roboticArmManager = new RoboticArmManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);

        level = new Level1Horizontal(stage, levelParams);

        virtualBlocksManager.init();
        roboticArmManager.init();
    }

    @Override
    protected void updateDigitalRepresentations() {
        updateArmPieces(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
    }

    @Override
    protected void testCollisionsInController(boolean isDynamic) {
       // Gdx.app.log(TAG," testCollisionsStatic in controller "+virtualBlocksManager.isWaitForFirstMove());
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

