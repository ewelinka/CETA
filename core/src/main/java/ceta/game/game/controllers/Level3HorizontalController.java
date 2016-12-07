package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level3Horizontal;
import ceta.game.game.levels.LevelHorizontal;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 11/4/16.
 */
public class Level3HorizontalController extends NoCvController  {
    private static final String TAG = Level3HorizontalController.class.getName();
    private float xZero;


    public Level3HorizontalController(DirectedGame game, Stage stage, int levelNr) {
        super(game,stage,levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);

        virtualBlocksManager = new VirtualBlocksManager(stage);

        level = new Level3Horizontal(stage,levelParams);
        xZero = Constants.HORIZONTAL_ZERO_X-level.bruno.getWidth()/2;
       // level.bruno.setSize(Constants.BASE*1,Constants.BASE*5);
//        level.bruno.setPosition(Constants.HORIZONTAL_ZERO_X-level.bruno.getWidth()/2,0);
//        level.bruno.setTerminalX(Constants.HORIZONTAL_ZERO_X-level.bruno.getWidth()/2);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();

    }


    @Override
    protected void updateDigitalRepresentations() {
        updateBruno(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
    }


    protected void testCollisionsStatic () {

        if (!(level.bruno.getActions().size > 0)) { // if bruno is not moving
            if(level.bruno.getTerminalX() != xZero ) {
                r1.set(level.bruno.getX()+level.bruno.getWidth()-4,
                        level.bruno.getY(),
                        4, level.bruno.getHeight());
                r2.set(level.price.getX(),
                        level.price.getY(),
                        level.price.bounds.width, level.price.bounds.height);

                if (r1.overlaps(r2)) {
                    onCollisionBrunoWithPrice(level.price);
                    moveMade = false;
                } else {
                    if (moveMade) {
                        AudioManager.instance.play(Assets.instance.sounds.liveLost);
                        moveMade = false;
                    }

                }
            }else{ // no blocks on the table
                if (moveMade) {
                    AudioManager.instance.play(Assets.instance.sounds.liveLost);
                    moveMade = false;
                }
            }
        }
    }

    protected void testCollisionsDynamic () {
        if (!(level.bruno.getActions().size > 0)) { // we have to be sure that the move finished
            // we set 4px x 4px box at the middle end (X), in the top (Y)
            if(level.bruno.getTerminalX() != xZero ) {
                r1.set(level.bruno.getX()+level.bruno.getWidth()-4,
                        level.bruno.getY(),
                        4, level.bruno.getHeight());
                r2.set(level.price.getX(),
                        level.price.getY(),
                        level.price.bounds.width, level.price.bounds.height);

                if (r1.overlaps(r2)) {
                    onCollisionBrunoWithPrice(level.price);
                    moveMade = false;
                } else{
                    //TODO check if the price number and number line position ==
                    // if == -> its a good answer
                    // if not -> error
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

    private void updateBruno(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveValues){
        int toAddNr = 0;
        int toRemoveNr = 0;

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
        level.bruno.moveMeToAndSetTerminalX(currentTerminalX + howMany*Constants.BASE, Constants.DETECTION_ZONE_END);

    }


}
