package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level3Horizontal;
import ceta.game.managers.CVBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ewe on 12/2/16.
 */
public class Level3HorizontalCvController extends CvController {
    private static final String TAG = Level3HorizontalCvController.class.getName();
    private float xZero;

    public Level3HorizontalCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }


    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        level = new Level3Horizontal(stage, levelParams, this);
        cvBlocksManager = new CVBlocksManager(game,stage);

        xZero = Constants.HORIZONTAL_ZERO_X-level.bruno.getWidth()/2;

        cameraHelper.setTarget(null);
        score = 0;

        cvBlocksManager.init();


    }

    @Override
    protected void updateDigitalRepresentations() {
        updateBruno(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemoveValues());
    }

    @Override
    protected void countdownMove() {
        level.bruno.shake();
    }



    protected void testCollisionsStatic () {
        Gdx.app.log(TAG," testCollisionsStatic ======");
        if (!(level.bruno.getActions().size > 0)) { // we have to be sure that the move finished
            // we set 4px x 4px box at the middle end (X), in the top (Y)
            if(level.bruno.getTerminalX() != xZero ) {
                r1.set(level.bruno.getX()+level.bruno.getWidth()/2 - 2,
                        level.bruno.getY() ,
                        4, level.bruno.getHeight());
                r2.set(level.price.getX()+level.price.getWidth()/2 - 2,
                        Constants.GROUND_LEVEL,
                        4, Math.abs(Constants.GROUND_LEVEL) + level.price.getY() +   level.price.bounds.height);

                if (r1.overlaps(r2)) {
                    //onCollisionBrunoWithPrice(level.price);
                    onCollisionBrunoWithPriceOpenMouth(level.price, level.bruno);
                    moveMade = false;
                } else {
                    if (moveMade) {
                        playErrorSound();
                        moveMade = false;
                    }

                }
            }else{ // no blocks on the table
                if (moveMade) {
                    playErrorSound();
                    moveMade = false;
                }
            }
        }
    }

    @Override
    protected void testCollisionsInController(boolean isDynamic){
        if(isDynamic)
            testCollisionsDynamic();
        else
            testCollisionsStatic();
    }




    private void testCollisionsDynamic () {
        //Gdx.app.log(TAG," testCollisionsDynamic ======");
        testCollisionsDynamicL3H(xZero);
    }


    private void updateBruno(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveValues){
        int toAddNr = 0;
        int toRemoveNr = 0;
        Gdx.app.log(TAG, " update Bruno! to add "+Arrays.toString(toAdd.toArray())+" to remove "+Arrays.toString(toRemoveValues.toArray()));


        for(int i=0; i< toRemoveValues.size();i++) {
            toRemoveNr+= toRemoveValues.get(i);
        }
        for(int i=0; i< toAdd.size();i++) {
            toAddNr+=toAdd.get(i).getValue();

        }

        if((toAddNr - toRemoveNr) != 0)
            moveBruno(toAddNr - toRemoveNr);
    }

    private void moveBruno(int howMany){
        if(howMany>0)
            level.bruno.setLookingLeft(false);
        else
            level.bruno.setLookingLeft(true);
        Gdx.app.log(TAG, " move bruno "+howMany);
        float currentTerminalX = level.bruno.getTerminalX();
        level.bruno.moveMeToAndSetTerminalX(currentTerminalX + howMany*Constants.BASE, Constants.GROUND_LEVEL);

    }
}
