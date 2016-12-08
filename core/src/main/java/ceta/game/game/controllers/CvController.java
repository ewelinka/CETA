package ceta.game.game.controllers;

import ceta.game.CetaGame;
import ceta.game.managers.CVBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/2/16.
 */
public class CvController extends AbstractWorldController {
    private static final String TAG = CvController.class.getName();
    protected CVBlocksManager cvBlocksManager;
    private int timeToWait;


    public CvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
        if(GamePreferences.instance.actionSubmit) {
            timeToWait = Constants.ACTION_SUBMIT_WAIT;
            Gdx.app.log(TAG, " time to wait!!! "+timeToWait);
        }
        else {
            timeToWait = 0;
            Gdx.app.log(TAG, "no time to wait!!!");
        }

    }

    @Override
    public void update(float deltaTime) {
        if(screenFinished) {
            timeLeftScreenFinishedDelay -= deltaTime;
            if (timeLeftScreenFinishedDelay < 0)
                goToCongratulationsScreen();
        }
        else{
            testCollisions(); // winning condition checked
        }

        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()

        /// detection-related start
        if(((CetaGame)game).hasNewFrame()) {
            Gdx.app.log(TAG," framerateeee" +Gdx.graphics.getFramesPerSecond());
            cvBlocksManager.updateDetected();
        }
        if(cvBlocksManager.isDetectionReady()){
            cvBlocksManager.analyseDetected();
        }
        /// detection-related end


        /// inactivity?
        if(cvBlocksManager.getTimeWithoutChange() > Constants.INACTIVITY_LIMIT){
            Gdx.app.log(TAG, " INACTIVITY_LIMIT !");
            setPlayerInactive(true);
            //cvBlocksManager.resetNoChangesSince();
        }else{
            setPlayerInactive(false);
        }

        // we start to act after kids move
        if(!cvBlocksManager.isWaitForFirstMove()) {

            if (cvBlocksManager.getTimeWithoutChange() > timeToWait) {
                if (!countdownOn) //if we are not counting, we start!
                    setCountdownOn(true);
            } else {
                if(true) {
                    setPlayerInactive(false);
                    setCountdownOn(false); // if somebody moved a block

                }
            }
        }

        if(countdownOn){
            countdownMove();
            setPlayerInactive(false);
            if (countdownCurrentTime < 0) {
                updateDigitalRepresentations();
                moveMade = true;
                cvBlocksManager.resetDetectedAndRemoved();
                resetCountdown();
                cvBlocksManager.setWaitForFirstMove(true);
                cvBlocksManager.resetNoChangesSince();
            }
            else{
                countdownCurrentTime -= deltaTime;
            }
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
    protected void countdownMove() {
        level.bruno.shake();
    }

    @Override
    public void dispose() {

    }
}
