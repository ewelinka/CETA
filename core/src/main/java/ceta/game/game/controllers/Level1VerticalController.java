package ceta.game.game.controllers;

import ceta.game.game.levels.Level1;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import ceta.game.util.VirtualBlocksManager;
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
    private Stage stage;

    public Level1VerticalController(DirectedGame game, Stage stage) {
        this.stage = stage;
        // TODO we init vertical-representation-manager [not yet implemented]

        virtualBlocksManager = new VirtualBlocksManager(stage);
        super.init(game);
        localInit();
    }

    @Override
    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()
        if (GamePreferences.instance.actionSubmit) {
            // if we are counting
            if (countdownOn) {
                // we shake bruno
                level.bruno.shake();
                // if we reached the time
                if (countdownCurrentTime < 0) {
                    Gdx.app.log(TAG, "wowowoowow action submit!");
                    virtualBlocksManager.updateDetected();
                    // TODO update vertical-representation-manager [not yet implemented]
                    countdownOn = false;
                    countdownCurrentTime = GamePreferences.instance.countdownMax;
                } else // we still count
                    countdownCurrentTime -= deltaTime;
            }
        } else {
            virtualBlocksManager.updateDetected();
            // TODO update vertical-representation-manager [not yet implemented]
        }
        cameraHelper.update(deltaTime);

    }

    @Override
    public void dispose() {

    }

    private void localInit () {
        Gdx.app.log(TAG," local init");
        level = new Level1(stage, GamePreferences.instance.lastLevel);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();

    }
}
