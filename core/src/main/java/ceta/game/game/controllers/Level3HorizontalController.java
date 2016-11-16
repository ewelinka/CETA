package ceta.game.game.controllers;

import ceta.game.game.levels.LevelHorizontal;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 11/4/16.
 */
public class Level3HorizontalController extends Level1HorizontalController  {
    private static final String TAG = Level3HorizontalController.class.getName();


    public Level3HorizontalController(DirectedGame game, Stage stage, int levelNr) {
        super(game,stage,levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);

        virtualBlocksManager = new VirtualBlocksManager(stage);

        level = new LevelHorizontal(stage,levelParams);
        level.bruno.setSize(Constants.BASE*1,Constants.BASE*2);
        level.bruno.setPosition(Constants.HORIZONTAL_ZERO_X-level.bruno.getWidth()/2,0);
        level.bruno.setTerminalX(Constants.HORIZONTAL_ZERO_X-level.bruno.getWidth()/2);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();

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
                    updateBruno(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
                    virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }
        } else {
            updateBruno(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
            virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
        }
        cameraHelper.update(deltaTime);
    }

    @Override
    public void dispose() {

    }


    @Override
    protected void testCollisions () {

        if (!(level.bruno.getActions().size > 0)) { // if bruno is not moving
            // we set 4px x 4px box at the right end (X), in the middle (Y)
            r1.set(level.bruno.getX()+level.bruno.getWidth()/2-2,
                    level.bruno.getY(),
                    4, level.bruno.getHeight());
            r2.set(level.price.getX(),
                    level.price.getY(),
                    level.price.bounds.width, level.price.bounds.height);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithGoldCoin(level.price);
            }
        }
    }

    private void updateBruno(ArrayList<Pair> toAdd, ArrayList<Short> toRemoveValues){
        short toAddNr = 0;
        short toRemoveNr = 0;

        for(short i=0; i< toRemoveValues.size();i++) {
            toRemoveNr+= toRemoveValues.get(i);
        }
        for(short i=0; i< toAdd.size();i++) {
            toAddNr+=toAdd.get(i).getValue();

        }

        if((toAddNr - toRemoveNr) != 0)
            moveBruno((short)(toAddNr - toRemoveNr));
    }

    private void moveBruno(short howMany){
        if(howMany>0)
            level.bruno.setLookingLeft(false);
        else
            level.bruno.setLookingLeft(true);
        Gdx.app.log(TAG, " move bruno "+howMany);
        float currentTerminalX = level.bruno.getTerminalX();
        level.bruno.moveMeToAndSetTerminalX(currentTerminalX + howMany*Constants.BASE, 0);

    }


}
