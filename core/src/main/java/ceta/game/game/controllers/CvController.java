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



    public CvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);


    }

    @Override
    public void update(float deltaTime) {
        globalUpdate(deltaTime);
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
        if(isPlayerInactive()){
            Gdx.app.log(TAG, " INACTIVITY_LIMIT !");
            setPlayerInactive(true);
            //cvBlocksManager.resetNoChangesSince();
        }else{
            setPlayerInactive(false);
        }

        // we start to act after kids move
        if(!cvBlocksManager.isWaitForFirstMove()) {
            if (cvBlocksManager.getTimeWithoutChange() > timeToWait) {
                if (!countdownOn) {//if we are not counting, we start!
                    Gdx.app.log(TAG,"NOT waiting OVER limit NOT counting -> set TRUE");
                    setCountdownOn(true);
                }
            } else {
                if(true) {
                    //setPlayerInactive(false);
                    Gdx.app.log(TAG,"NOT waiting NOT-OVER limit NOT counting -> set FALSE");
                    setCountdownOn(false); // if somebody moved a block

                }
            }
        }

        if(countdownOn){
            countdownMove();
            //setPlayerInactive(false);
            if (countdownCurrentTime < 0) {
                updateDigitalRepresentations();
                moveMade = true;
                cvBlocksManager.resetDetectedAndRemoved();
                Gdx.app.log(TAG,"countdown ON , countdownCurrentTime < 0 -> set FALSE");
                setCountdownOn(false);
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
    protected boolean isPlayerInactive() {
        //TODO control current errors in action submit
        if((cvBlocksManager.getTimeWithoutChange() > Constants.INACTIVITY_LIMIT) && (currentErrors > Constants.errorsForHint)) {
            setPlayerInactive(true);
        }
        return playerInactive;


    }

    @Override
    public void dispose() {

    }
}
