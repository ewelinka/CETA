package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level2Vertical;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.EnergyUnit;
import ceta.game.managers.EnergyManager;
import ceta.game.managers.CVBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/5/16.
 */
public class Level2VerticalCvController extends CvController {
    private static final String TAG = Level2VerticalCvController.class.getName();
    private EnergyManager energyManager;

    public Level2VerticalCvController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        energyManager = new EnergyManager(stage);
        cvBlocksManager = new CVBlocksManager(game,stage);

        Gdx.app.log(TAG," local init with last level: "+ GamePreferences.instance.lastLevel);
        level = new Level2Vertical(stage,levelParams);
        cameraHelper.setTarget(null);
        score = 0;
        cvBlocksManager.init();
        energyManager.init();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        energyManager.updateAlpha(deltaTime);
    }


    @Override
    protected void updateDigitalRepresentations() {
        energyManager.updateAnimated(cvBlocksManager.getNewDetected(), cvBlocksManager.getToRemoveValues());
    }


    @Override
    protected void testCollisionsInController (boolean isDynamic) {
        BrunoVertical lastPiece = energyManager.getBrunoVertical();
        if (!energyManager.isUpdatingBrunosPositions()) {
            if(isDynamic)
                testCollisionsVerticalDynamic(lastPiece);
            else
                testCollisionsVerticalStatic(lastPiece);
        }
    }

    @Override
    protected void testCollisionsVerticalStatic(BrunoVertical objectToCheck){
        if(objectToCheck != null ) {
            r1.set(objectToCheck.getX() ,
                    objectToCheck.getY(), // two pixels below the middle
                    objectToCheck.getWidth()+ Constants.PRICE_X_OFFSET , objectToCheck.getHeight()/2);
            Gdx.app.log(TAG," x "+objectToCheck.getX()+" y "+(objectToCheck.getY()+ objectToCheck.getHeight() - 4)+" w "+(objectToCheck.getWidth()+ Constants.PRICE_X_OFFSET )+ " h "+4);
            r2.set(level.price.getX(),
                    level.price.getY(),
                    level.price.getWidth()/2, level.price.getHeight()/2);
            Gdx.app.log(TAG,"price x "+level.price.getX()+" y "+level.price.getY()+" w "+level.price.getWidth()/2+" h "+level.price.getHeight()/2);
            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPriceVertical(level.price, objectToCheck);
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

    @Override
    protected void countdownMove() {
        //level.bruno.shake();
        energyManager.getBrunoVertical().shake();
    }
}
