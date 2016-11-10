package ceta.game.game.controllers;

import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.levels.LevelVertical;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 11/9/16.
 */
public class Level3VerticalController extends Level1VerticalController {
    private static final String TAG = Level3VerticalController.class.getName();

    public Level3VerticalController(DirectedGame game, Stage stage) {
        super(game, stage);
    }

    @Override
    protected void localInit () {
        virtualBlocksManager = new VirtualBlocksManager(stage);
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        level = new LevelVertical(stage, GamePreferences.instance.lastLevel);
        level.bruno.setSize(Constants.BASE*1,Constants.BASE*1);
        level.bruno.setPosition(-280,-Constants.BASE/2);
        level.bruno.setTerminalX(-280);
        level.bruno.setTerminalY(-Constants.BASE/2);
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
                    updateBrunoVertical(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
                    virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }
        } else {
            updateBrunoVertical(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
            virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
        }
        cameraHelper.update(deltaTime);
    }


    @Override
    protected void testCollisions () {

        if (!(level.bruno.getActions().size > 0)) { // if bruno is not moving
            // we set 4px x 4px box at the right end (X), in the middle (Y)
            r1.set(level.bruno.getX() + level.bruno.getWidth()/2 - 2,
                    level.bruno.getY()+ level.bruno.getHeight()/2 -2, // two pixels below the middle
                    4, 4);
            r2.set(level.price.getX(),
                    level.price.getY() + level.price.getHeight() / 2 - 2,
                    level.price.bounds.width, 4);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPrice(level.price);
            }
        }
    }

    private void updateBrunoVertical(ArrayList<Pair> toAdd, ArrayList<Short> toRemoveValues){
        short toAddNr = 0;
        short toRemoveNr = 0;

        for(short i=0; i< toRemoveValues.size();i++) {
            toRemoveNr+= toRemoveValues.get(i);
        }
        for(short i=0; i< toAdd.size();i++) {
            toAddNr+=toAdd.get(i).getValue();

        }

        if((toAddNr - toRemoveNr) != 0)
            moveBrunoVertical((short)(toAddNr - toRemoveNr));
    }

    private void moveBrunoVertical(short howMany){

        Gdx.app.log(TAG, " move bruno "+howMany);
        float currentTerminalY = level.bruno.getTerminalY();
        ((BrunoVertical)(level.bruno)).moveMeToAndSetTerminalY(-280, currentTerminalY + howMany* Constants.BASE);

    }

}
