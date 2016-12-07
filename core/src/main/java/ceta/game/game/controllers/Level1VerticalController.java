package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1Vertical;
import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.levels.LevelVertical;
import ceta.game.game.objects.ArmPieceAnimated;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.Price;
import ceta.game.managers.BrunosManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.managers.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 10/19/16.
 */
public class Level1VerticalController extends NoCvController {
    private static final String TAG = Level1VerticalController.class.getName();
//    protected VirtualBlocksManager virtualBlocksManager;
    private BrunosManager brunosManager;


    public Level1VerticalController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        level = new Level1Vertical(stage, levelParams);
        virtualBlocksManager = new VirtualBlocksManager(stage);
        brunosManager = new BrunosManager(stage);

        score = 0;
        virtualBlocksManager.init();
        brunosManager.init();

    }

    @Override
    protected void updateDigitalRepresentations() {
        brunosManager.update(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
    }

    @Override
    protected void countdownMove() {
        //level.bruno.shake();
    }



    @Override
    protected void testCollisions () {
        BrunoVertical lastBruno = getLastBruno();
        if (lastBruno != null && !brunosManager.isUpdatingBrunosPositions()) {
            r1.set(lastBruno.getX() + lastBruno.getWidth()/2 - 2,
                    lastBruno.getY()+lastBruno.getHeight(), // two pixels below the middle
                    4+Constants.PRICE_X_OFFSET, 4);
            r2.set(level.price.getX() + level.price.getWidth()/2 - 2,
                    level.price.getY() + level.price.getHeight() / 2 - 2,
                    4, 4);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPriceVertical(level.price, lastBruno);
            }
        }
    }

    public BrunoVertical getLastBruno(){
        return brunosManager.getLastBruno();
    }





}
