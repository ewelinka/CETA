package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1Vertical;
import ceta.game.game.levels.Level3Horizontal;
import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.managers.BrunosManager;
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

import static ceta.game.util.Constants.DETECTION_ZONE_END;

/**
 * Created by ewe on 12/2/16.
 */
public class Level3HorizontalCvController extends CvController { // TODO should inherit from 2 classes: Level3HC y L1VCvC
    private static final String TAG = Level3HorizontalCvController.class.getName();
    private float xZero;

    public Level3HorizontalCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }


    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        level = new Level3Horizontal(stage, levelParams);
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

    @Override
    protected void testCollisions () {

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
        level.bruno.moveMeToAndSetTerminalX(currentTerminalX + howMany*Constants.BASE, Constants.DETECTION_ZONE_END);

    }
}