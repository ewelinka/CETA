package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/**
 * Created by ewe on 12/2/16.
 */
public class NoCvController extends AbstractWorldController {
    private static final String TAG = NoCvController.class.getName();
    protected VirtualBlocksManager virtualBlocksManager;

    public NoCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }


    @Override
    protected void countdownMove() {
        level.bruno.shake();
    }

    @Override
    protected boolean isPlayerInactive() {
       //TODO add errors check and set if too much or too less
        return (virtualBlocksManager.getTimeWithoutChange() > Constants.INACTIVITY_LIMIT);
    }

    @Override
    public void update(float deltaTime) { // TODO move to no-cv-controller that will be the father of all no-cv controlers
        if(screenFinished) {
            //Gdx.app.log(TAG, "SCREEN FINISHED! "+timeLeftScreenFinishedDelay);
            timeLeftScreenFinishedDelay -= deltaTime;
            if (timeLeftScreenFinishedDelay < 0)
                goToCongratulationsScreen();
        }
        else{
            testCollisions(); // winning condition checked
        }

        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()
        virtualBlocksManager.updateDetected();

        // we start to act after kids move
        if(!virtualBlocksManager.isWaitForFirstMove()) { // just for action submit!

            if (virtualBlocksManager.getTimeWithoutChange() > Constants.ACTION_SUBMIT_WAIT) {
                if (!countdownOn) {
                   // Gdx.app.log(TAG, "-->   go countdown!");
                    setCountdownOn(true); //if we are not counting, we start!

                }
            } else {
                if(true) {
                    //setPlayerInactive(false);
                    setCountdownOn(false); // if somebody moved a block


                }
            }
        }

        // if we are counting
        if (countdownOn) {
            // we shake bruno
            countdownMove();
            // if we reached the time
            if (countdownCurrentTime < 0) {
                Gdx.app.log(TAG, "wowowoowow action submit!");
//                int[] a =  {1,2};
//                AudioManager.instance.readTheSum(a);
                updateDigitalRepresentations();
                moveMade = true;
                setCountdownOn(false);
               // setCountdownOn(false);
                virtualBlocksManager.setWaitForFirstMove(true);
                virtualBlocksManager.resetDetectedAndRemoved();
            } else // we still count
                countdownCurrentTime -= deltaTime;
        }

        cameraHelper.update(deltaTime);


    }

    @Override
    protected void testCollisionsInController(boolean isDynamic) {

    }


    @Override
    protected void testCollisions() {
        //Gdx.app.log(TAG," testCollisions ---  ");
        super.testCollisions();
    }

    @Override
    protected void localInit() {

    }

    @Override
    protected void updateDigitalRepresentations() {

    }

    @Override
    public void dispose() {

    }
}
