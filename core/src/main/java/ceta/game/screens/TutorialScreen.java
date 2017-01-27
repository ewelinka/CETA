package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

/**
 * Created by ewe on 1/11/17.
 */
public class TutorialScreen  extends AbstractGameScreen {
    private static final String TAG = TutorialScreen.class.getName();
    private ImageButton btnStartGame;
    private Image imgBackground, imgBackground2;
    private boolean goingVisible;
    private float changeStay;
    private float tutorialTimeMillis;
    private int maxTutorialTimeSec;
    private boolean isChanging;

    public TutorialScreen(DirectedGame game) {
        super(game);
        goingVisible = true;
        changeStay = 1.5f;
        tutorialTimeMillis = 0;
        maxTutorialTimeSec = 4;
        isChanging = false;
    }

    @Override
    public void render(float deltaTime) {
        tutorialTimeMillis+=deltaTime;
        Gdx.app.log(TAG,"now passed "+tutorialTimeMillis);
        if((tutorialTimeMillis > maxTutorialTimeSec) && !isChanging) {
            game.setScreen(new TreeScreen(game, true), ScreenTransitionFade.init(1));
            isChanging = true;

        }


        if (!imgBackground.hasActions()) {
            if (goingVisible) {
                //imgBackground.addAction(Actions.alpha(0, changeStay));
                imgBackground.addAction(sequence(Actions.alpha(0), delay(changeStay)));

                goingVisible = false;

            } else {
                //imgBackground.addAction(Actions.alpha(1, changeStay));
                imgBackground.addAction(sequence(Actions.alpha(1), delay(changeStay)));

                goingVisible = true;
            }
        }


        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();




    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);


    }

    @Override
    public void show() {
        tutorialTimeMillis = 0;
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        buildStage();
    }

    @Override
    public void hide() {
        stage.dispose();

    }

    @Override
    public void pause() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    private void buildStage() {

        Table layerBackground = buildBackgroundLayer();
        Table playMenu = buildPlayMenu();

        stage.addActor(layerBackground);
        //stage.addActor(playMenu);

    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
//        imgBackground = new Image(Assets.instance.background.back2);

        imgBackground = new Image(Assets.instance.feedback.tutorial1);
        imgBackground2 = new Image(Assets.instance.feedback.tutorial2);

        layer.addActor(imgBackground2);
        imgBackground2.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        imgBackground2.setOrigin(imgBackground2.getWidth() / 2, imgBackground2.getHeight() / 2);
        imgBackground2.setPosition((Constants.VIEWPORT_WIDTH-imgBackground2.getWidth())/2, 0);

        layer.addActor(imgBackground);
        imgBackground.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        imgBackground.setOrigin(imgBackground.getWidth() / 2, imgBackground.getHeight() / 2);
        imgBackground.setPosition((Constants.VIEWPORT_WIDTH-imgBackground.getWidth())/2, 0);

        //imgBackground.addAction(Actions.alpha(1,changeStay));
        imgBackground.addAction(sequence(Actions.alpha(1),delay(changeStay*2)));

        return layer;
    }

    private Table buildPlayMenu () {
        // TODO button for "skip tutorial"
        btnStartGame = new ImageButton(Assets.instance.buttons.understoodButtonStyle);

        Table tbl = new Table();
        tbl.add(btnStartGame);
        tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth()/2 , 80);
        btnStartGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        return tbl;
    }

    private void onPlayClicked () {
        //game.setScreen(new SimpleMenuScreen(game), ScreenTransitionFade.init(1));
        game.setScreen(new TreeScreen(game,true), ScreenTransitionFade.init(1));
    }
}
