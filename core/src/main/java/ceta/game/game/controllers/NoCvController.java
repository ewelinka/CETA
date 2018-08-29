package ceta.game.game.controllers;

import ceta.game.managers.AbstractBlocksManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

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
    public boolean isPlayerInactive() {
        return ((virtualBlocksManager.getTimeWithoutChange() > Constants.INACTIVITY_LIMIT) && (currentErrors >= Constants.ERRORS_FOR_HINT));
    }

    @Override
    public void update(float deltaTime) {
        globalUpdate(deltaTime);

        virtualBlocksManager.updateDetected();

        if(isPlayerInactive()){
            //Gdx.app.log(TAG, " INACTIVITY_LIMIT !");
            setPlayerInactive(true);
        }else{
            setPlayerInactive(false);
        }


        if (!virtualBlocksManager.isWaitForFirstMove()) { // just for action submit!
            if (virtualBlocksManager.getTimeWithoutChange() > timeToWait) {
                if (!countdownOn) {
                   // Gdx.app.log(TAG, "NOT waiting OVER limit NOT counting -> set TRUE");
                    setCountdownOn(true); //if we are not counting, we start!
                }
            } else {
                if (true) {
                   // Gdx.app.log(TAG, "NOT waiting NOT-OVER limit NOT counting -> set FALSE");
                    setCountdownOn(false); // if somebody moved a block
                }
            }
        }
        else{
            setCountdownOn(false);
        }

        // if we are counting
        if (countdownOn) {
            // we shake bruno
            countdownMove();
            // if we reached the time
            if (countdownCurrentTime < 0) {
                updateDigitalRepresentations(); // ACTION SUBMIT !
                readDetectedSaveIntentAndLastSolution();
                moveMade = true;
                setCountdownOn(false);
                virtualBlocksManager.setWaitForFirstMove(true);
                virtualBlocksManager.resetDetectedAndRemoved();
            } else // we still count
                countdownCurrentTime -= deltaTime;
        }


        cameraHelper.update(deltaTime);


    }

    @Override
    public int getNowDetectedSum(){
        ArrayList<Integer> toReadVals = virtualBlocksManager.getNowDetectedVals();
        int sum = 0;
        for( Integer i : toReadVals ) {
            sum += i;
        }
        return sum;

    }


    @Override
    protected void readDetectedSaveIntentAndLastSolution(){
        ArrayList<Integer> toReadVals = virtualBlocksManager.getNowDetectedVals();
        readDetectedAndSaveIntentGeneric(toReadVals);
        saveLastSolution(virtualBlocksManager.getNowDetectedBlocks());
        checkIfTableCleaned();
    }

    @Override

    protected void readDetectedAndSaveIntentGeneric(ArrayList<Integer> toReadValues){
        int sum = 0;
        for( Integer v : toReadValues ) {
            sum += v;
        }
        AudioManager.instance.readNumber(levelParams.numberMin+ sum);
        addIntentToResults(sum,level.price.getCorrectAnswerToPut(), level.price.getDisplayNumber(), toReadValues);

    }

    @Override
    public boolean isWaitForFirstMove(){
        return virtualBlocksManager.isWaitForFirstMove();
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
