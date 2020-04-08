package ceta.game.game.controllers;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1Vertical;
import ceta.game.managers.BrunosManager;
import ceta.game.managers.VirtualBlocksManagerTutorial;
import ceta.game.screens.CongratulationsScreenTutorial;
import ceta.game.screens.DirectedGame;
import ceta.game.transitions.ScreenTransitionFade;

/**
 * Created by ewe on 2/8/17.
 */
public class Level1VerticalControllerTutorial extends Level1VerticalController {
    private static final String TAG = Level1VerticalControllerTutorial.class.getName();
    private Image arrow,arrow2;

    public Level1VerticalControllerTutorial(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);

    }

    @Override
    protected void localInit () {
        level = new Level1Vertical(stage, levelParams, this);
        level.price.forcePrice(new int[]{1});
        virtualBlocksManager = new VirtualBlocksManagerTutorial(stage);
        brunosManager = new BrunosManager(stage);
        float changeSpeed = 0.8f;

        score = 0;
        virtualBlocksManager.init();
        brunosManager.init();
        operationsNumberToPassToNext = 1;

        levelParams.operationsToFinishLevel= 1;

        arrow = new Image(Assets.instance.tree.arrowWhite);

        arrow.setColor(1,1,0,0);
        arrow.setPosition(219,-117);
        arrow.setRotation(85);
        arrow.addAction(sequence(
                delay(1.0f),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed)

        ));

        stage.addActor(arrow);

        arrow2 = new Image(Assets.instance.tree.arrowWhite);
        arrow2.setColor(1,1,0,0);
        arrow2.setPosition(-136,-60);
        arrow2.setRotation(-85);
        arrow2.setTouchable(Touchable.enabled);
        // to move around
        arrow2.addListener(new ActorGestureListener() {

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                arrow2.setPosition(arrow2.getX()+deltaX,arrow2.getY()+deltaY);
                Gdx.app.log(TAG," x "+arrow2.getX()+" y "+arrow2.getY());
            }


        });

        arrow2.addAction(sequence(
                delay(1.0f),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed)

        ));

        stage.addActor(arrow2);

    }

    @Override
    public void goToCongratulationsScreen () {

        game.setScreen(new CongratulationsScreenTutorial(game,score), ScreenTransitionFade.init(1));

    }
}
