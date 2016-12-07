package ceta.game.game.controllers;

import ceta.game.game.levels.Level2Vertical;
import ceta.game.game.levels.LevelVertical;
import ceta.game.game.objects.BrunoPieceAnimatedVertical;
import ceta.game.managers.AnimatedBrunoManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;

/**
 * Created by ewe on 11/9/16.
 */
public class Level2VerticalController extends NoCvController {
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
        level = new Level2Vertical(stage,levelParams);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();
        brunosManager.init();
    }

    @Override
    public void update(float deltaTime) {
       // Gdx.app.log(TAG," update me and my alpha");
        super.update(deltaTime);
        brunosManager.updateAlpha(deltaTime);
    }


    @Override
    protected void updateDigitalRepresentations() {
        brunosManager.updateAnimated(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
    }

    @Override
    protected void countdownMove() {
        //level.bruno.shake();
    }


//    @Override
//    protected void testCollisions () {
//        BrunoPieceAnimatedVertical lastPiece = getLastAnimatedBrunoPiece();
//        if (lastPiece != null && !brunosManager.isUpdatingBrunosPositions()) {
//
//            r1.set(lastPiece.getX() + lastPiece.getWidth()/2 - 2,
//                    lastPiece.getY()+lastPiece.getHeight(), // two pixels below the middle
//                    4 + Constants.PRICE_X_OFFSET, 4);
//            r2.set(level.price.getX() + level.price.getWidth()/2 - 2,
//                    level.price.getY() + level.price.getHeight() / 2 - 2,
//                    4, 4);
//
//            if (r1.overlaps(r2)) {
//                onCollisionBrunoWithPriceVertical(level.price, lastPiece);
//            }
//        }
//    }



    @Override
    protected void testCollisionsInController (boolean isDynamic) {
        BrunoPieceAnimatedVertical lastPiece = getLastAnimatedBrunoPiece();
        if (!brunosManager.isUpdatingBrunosPositions()) {
            if(isDynamic)
                testCollisionsVerticalDynamic(lastPiece);
            else
                testCollisionsVerticalStatic(lastPiece);
        }
    }


    public BrunoPieceAnimatedVertical getLastAnimatedBrunoPiece(){
        return brunosManager.getLastAnimatedBrunoPiece();
    }
}
