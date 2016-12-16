package ceta.game.game.controllers;

import ceta.game.game.levels.Level2Vertical;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.EnergyUnit;
import ceta.game.managers.EnergyManager;
import ceta.game.managers.CVBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/5/16.
 */
public class Level2VerticalCvController extends CvController {
    private static final String TAG = Level2VerticalCvController.class.getName();

    private EnergyManager brunosManager;
    public Level2VerticalCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        brunosManager = new EnergyManager(stage);
        cvBlocksManager = new CVBlocksManager(game,stage);

        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        level = new Level2Vertical(stage,levelParams);
        cameraHelper.setTarget(null);
        score = 0;
        cvBlocksManager.init();
        brunosManager.init();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        brunosManager.updateAlpha(deltaTime);
    }


    @Override
    protected void updateDigitalRepresentations() {
        brunosManager.updateAnimated(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemoveValues());
    }


    @Override
    protected void testCollisionsInController (boolean isDynamic) {
        BrunoVertical lastPiece = getLastAnimatedBrunoPiece();
        if (!brunosManager.isUpdatingBrunosPositions()) {
            if(isDynamic)
                testCollisionsVerticalDynamic(lastPiece);
            else
                testCollisionsVerticalStatic(lastPiece);
        }
    }

    @Override
    protected void countdownMove() {
        //level.bruno.shake();
    }


    public BrunoVertical getLastAnimatedBrunoPiece(){
        return brunosManager.getBrunoVertical();
    }
}
