package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1Horizontal;
import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.levels.LevelParams;
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

//    @Override
//    protected void testCollisions () {
//        ArmPiece lastArm = getLastArmPiece();
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



    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }


    protected void updateArmPieces(ArrayList toAdd, ArrayList toRemove){
        roboticArmManager.update(toAdd,toRemove);
    }

    public VirtualBlocksManagerOSC getVirtualBlocksManagerOSC(){
        return  new VirtualBlocksManagerOSC(stage);
        //return virtualBlocksManager;
    }


}

