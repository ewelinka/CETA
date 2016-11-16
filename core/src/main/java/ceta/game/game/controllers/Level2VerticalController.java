package ceta.game.game.controllers;

import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.levels.LevelVertical;
import ceta.game.game.objects.ArmPieceAnimated;
import ceta.game.game.objects.BrunoPieceAnimated;
import ceta.game.managers.AnimatedBrunoManager;
import ceta.game.managers.AnimatedRoboticArmManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 11/9/16.
 */
public class Level2VerticalController extends Level1VerticalController {
    private static final String TAG = Level2VerticalController.class.getName();
    private AnimatedBrunoManager brunosManager;

    public Level2VerticalController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        brunosManager = new AnimatedBrunoManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);

        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        level = new LevelVertical(stage,levelParams);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();
        brunosManager.init();
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
                    brunosManager.updateAnimated(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
                    virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }
        } else {
            brunosManager.updateAnimated(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
            virtualBlocksManager.resetDetectedAndRemoved(); // after the update we reset the detected blocks
        }
        cameraHelper.update(deltaTime);
    }

    @Override
    protected void testCollisions () {
        BrunoPieceAnimated lastPiece = getLastAnimatedBrunoPiece();
        if (lastPiece != null && !brunosManager.isUpdatingBrunosPositions()) {

            r1.set(lastPiece.getX() + lastPiece.getWidth()/2 - 2,
                    lastPiece.getY()+lastPiece.getHeight(), // two pixels below the middle
                    4, 4);
            r2.set(level.price.getX() + level.price.getWidth()/2 - 2,
                    level.price.getY() + level.price.getHeight() / 2 - 2,
                    4, 4);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPrice(level.price);
            }
        }
    }


    public BrunoPieceAnimated getLastAnimatedBrunoPiece(){
        return brunosManager.getLastAnimatedBrunoPiece();
    }
}
