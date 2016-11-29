package ceta.game.game.controllers;

import ceta.game.CetaGame;
import ceta.game.game.Assets;
import ceta.game.game.levels.Level1Vertical;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.Price;
import ceta.game.managers.BrunosManager;
import ceta.game.managers.CVBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 10/19/16.
 */
public class TestController extends AbstractWorldController {
    private static final String TAG = Level1VerticalController.class.getName();
    protected CVBlocksManager cvBlocksManager;
    private BrunosManager brunosManager;
    private boolean moveMade;


    public TestController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        level = new Level1Vertical(stage, levelParams);
        cvBlocksManager = new CVBlocksManager(game,stage);
        brunosManager = new BrunosManager(stage);

        score = 0;
        cvBlocksManager.init();
        brunosManager.init();
        //cameraHelper.addZoom(0.3f);
        moveMade = false;

    }

    @Override
    public void update(float deltaTime) {
        testCollisions(); // winning condition checked
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

        // we start to act after kids move
        if(!cvBlocksManager.isWaitForFirstMove()) {
            if (cvBlocksManager.getTimeWithoutChange() > Constants.ACTION_SUBMIT_WAIT) {
                if (!countdownOn)
                    setCountdownOn(true);
            } else {
                setCountdownOn(false); // if somebody moved a block
            }
        }

        if(countdownOn){
            ((Level1Vertical)(level)).getTube().shake();
            if (countdownCurrentTime < 0) {
                brunosManager.update(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemove());
                moveMade = true;
                cvBlocksManager.resetDetectedAndRemoved();
                resetCountdown();
                cvBlocksManager.setWaitForFirstMove(true);
            }
            else{
                countdownCurrentTime -= deltaTime;
            }
        }
        cameraHelper.update(deltaTime);

    }

    @Override
    public void dispose() {

    }


    @Override
    protected void testCollisions () {
        BrunoVertical lastBruno = getLastBruno();
        if (!brunosManager.isUpdatingBrunosPositions()) { // we have to be sure that the move finished
            // we set 4px x 4px box at the middle end (X), in the top (Y)
            if(lastBruno != null ) {
                r1.set(lastBruno.getX() + lastBruno.getWidth() / 2 - 2,
                        lastBruno.getY() + lastBruno.getHeight(), // two pixels below the middle
                        4, 4);
                r2.set(level.price.getX() + level.price.getWidth() / 2 - 2,
                        level.price.getY() + level.price.getHeight() / 2 - 2,
                        4, 4);

                if (r1.overlaps(r2)) {
                    onCollisionBrunoWithPrice(level.price);
                    moveMade = false;
                } else {
                    if (moveMade) {
                        AudioManager.instance.play(Assets.instance.sounds.liveLost);
                        moveMade = false;
                    }

                }
            }else{
                if (moveMade) {
                    AudioManager.instance.play(Assets.instance.sounds.liveLost);
                    moveMade = false;
                }
            }
        }
    }

    public BrunoVertical getLastBruno(){
        return brunosManager.getLastBruno();
    }

    protected void onCollisionBrunoWithPrice(Price goldcoin) {
        Gdx.app.log(TAG, "NO updates in progress and collision!");
        if (goldcoin.getActions().size == 0) { // we act just one time!
            AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
            score += 1;
            //TODO some nice yupi animation
            if (score < levelParams.operationsNumberToPass) {
                goldcoin.wasCollected();

            } else
                goToCongratulationsScreen();
        }
    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button)
//    {
//        Gdx.app.log(TAG, "screen touched! "+screenY);
//        if(screenY< 200){
//            takeCapture =true;
//        }
//        return true;
//    }
}
