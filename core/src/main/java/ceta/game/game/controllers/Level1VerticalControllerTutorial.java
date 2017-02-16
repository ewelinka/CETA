package ceta.game.game.controllers;

import ceta.game.game.levels.Level1Vertical;
import ceta.game.managers.BrunosManager;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.managers.VirtualBlocksManagerTutorial;
import ceta.game.screens.CongratulationsScreen;
import ceta.game.screens.CongratulationsScreenTutorial;
import ceta.game.screens.DirectedGame;
import ceta.game.transitions.ScreenTransitionFade;
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
        level.price.forcePrice(new int[]{1});
        virtualBlocksManager = new VirtualBlocksManagerTutorial(stage);
        brunosManager = new BrunosManager(stage);

        score = 0;
        virtualBlocksManager.init();
        brunosManager.init();
        operationsNumberToPassToNext = 1;

        levelParams.operationsToFinishLevel= 1;

    }

    @Override
    public void goToCongratulationsScreen () {

        game.setScreen(new CongratulationsScreenTutorial(game,score), ScreenTransitionFade.init(1));

    }
}
