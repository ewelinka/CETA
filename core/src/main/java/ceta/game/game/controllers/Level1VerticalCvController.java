package ceta.game.game.controllers;

import ceta.game.CetaGame;
import ceta.game.game.Assets;
import ceta.game.game.levels.Level1Vertical;
import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.Price;
import ceta.game.managers.BrunosManager;
import ceta.game.managers.CVBlocksManager;
import ceta.game.screens.AbstractGameScreen;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 10/19/16.
 */
public class Level1VerticalCvController extends CvController {
    private static final String TAG = Level1VerticalController.class.getName();

    private BrunosManager brunosManager;



    public Level1VerticalCvController(DirectedGame game, Stage stage, int levelNr) {
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


    }

    @Override
    protected void updateDigitalRepresentations() {
        brunosManager.update(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemove());
    }

    @Override
    protected void countdownMove() {
        ((Level1Vertical)(level)).getTube().shake();
    }



    @Override
    protected void testCollisionsInController (boolean isDynamic) {
        Gdx.app.log(TAG," testCollisionsInController ");
        BrunoVertical lastBruno = getLastBruno();
        if (!brunosManager.isUpdatingBrunosPositions()) { // we have to be sure that the move finished
            // we set 4px x 4px box at the middle end (X), in the top (Y)
            if(isDynamic)
                testCollisionsVerticalDynamic(lastBruno);
            else
                testCollisionsVerticalStatic(lastBruno);
        }
    }


    public BrunoVertical getLastBruno(){
        return brunosManager.getLastBruno();
    }

}
