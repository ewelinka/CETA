package ceta.game.game.controllers;

import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

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

        if (GamePreferences.instance.actionSubmit) {
            // if we are counting
            if (countdownOn) {
                // we shake bruno
                countdownMove();
                // if we reached the time
                if (countdownCurrentTime < 0) {
                    Gdx.app.log(TAG, "wowowoowow action submit!");
                    updateDigitalRepresentations();
                    virtualBlocksManager.resetDetectedAndRemoved();
                    resetCountdown();
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }
        } else {
            updateDigitalRepresentations();
            virtualBlocksManager.resetDetectedAndRemoved();

        }
        cameraHelper.update(deltaTime);


    }

    @Override
    protected void testCollisionsInController(boolean isDynamic) {

    }


    @Override
    protected void testCollisions() {
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
