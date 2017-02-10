package ceta.game.game.controllers;

import ceta.game.game.levels.Level1Vertical;
import ceta.game.managers.BrunosManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.managers.VirtualBlocksManagerTutorial;
import ceta.game.screens.DirectedGame;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 2/8/17.
 */
public class Level1VerticalControllerTutorial extends Level1VerticalController {
    private static final String TAG = Level1VerticalControllerTutorial.class.getName();

    public Level1VerticalControllerTutorial(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }

    @Override
    protected void localInit () {
        level = new Level1Vertical(stage, levelParams, this);
        virtualBlocksManager = new VirtualBlocksManagerTutorial(stage);
        brunosManager = new BrunosManager(stage);

        score = 0;
        virtualBlocksManager.init();
        brunosManager.init();

    }
}
