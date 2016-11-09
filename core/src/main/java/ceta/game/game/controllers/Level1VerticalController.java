package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.LevelHorizontal;
import ceta.game.game.levels.LevelVertical;
import ceta.game.game.objects.ArmPieceAnimated;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.Price;
import ceta.game.managers.BrunosManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.GamePreferences;
import ceta.game.managers.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 10/19/16.
 */
public class Level1VerticalController extends AbstractWorldController {
    private static final String TAG = Level1VerticalController.class.getName();
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();
    private VirtualBlocksManager virtualBlocksManager;
    private BrunosManager brunosManager;
    private Stage stage;

    public Level1VerticalController(DirectedGame game, Stage stage) {
        this.stage = stage;
        // TODO we init vertical-representation-manager [not yet implemented]

        virtualBlocksManager = new VirtualBlocksManager(stage);
        brunosManager = new BrunosManager(stage);
        super.init(game);
        localInit();
    }

    @Override
    public void update(float deltaTime) {
        testCollisions(); // winning condition checked
        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()
        virtualBlocksManager.updateDetected();

        if (GamePreferences.instance.actionSubmit) {
            // if we are counting
            if (countdownOn) {
                // we shake bruno
                level.bruno.shake();
                // if we reached the time
                if (countdownCurrentTime < 0) {
                    Gdx.app.log(TAG, "wowowoowow action submit!");
                    brunosManager.update(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
                    virtualBlocksManager.resetDetectedAndRemoved();
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }
        } else {
            brunosManager.update(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemove());
            virtualBlocksManager.resetDetectedAndRemoved();

        }
        cameraHelper.update(deltaTime);

    }

    @Override
    public void dispose() {

    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init going to level vertical");
        level = new LevelVertical(stage, GamePreferences.instance.lastLevel);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();
        brunosManager.init();

    }

    @Override
    protected void testCollisions () {
        BrunoVertical lastBruno = getLastBruno();
        if (lastBruno != null && !brunosManager.isUpdatingBrunosPositions()) {
            // we set 4px x 4px box at the midlle end (X), in the top (Y)
            r1.set(lastBruno.getX() + lastBruno.getWidth()/2 - 2,
                    lastBruno.getY()+lastBruno.getHeight(), // two pixels below the middle
                    4, 4);
            r2.set(level.price.getX() + level.price.getWidth()/2 - 2,
                    level.price.getY() + level.price.getHeight() / 2 - 2,
                    4, 4);

            if (r1.overlaps(r2)) {
                onCollisionBrunoWithPrice(level.price);
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
            if (score < level.getOperationsNumberToPass()) {
                goldcoin.wasCollected();

            } else
                goToFinalScreen();
        }
    }
}
