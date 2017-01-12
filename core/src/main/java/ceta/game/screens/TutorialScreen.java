package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;

/**
 * Created by ewe on 1/11/17.
 */
public class TutorialScreen  extends AbstractGameScreen {
    private static final String TAG = TutorialScreen.class.getName();
    private ImageButton btnStartGame;
    private Image imgBackground;

    public TutorialScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
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
        stage.addActor(playMenu);

    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        imgBackground = new Image(Assets.instance.background.back2);

        layer.addActor(imgBackground);
        imgBackground.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        imgBackground.setOrigin(imgBackground.getWidth() / 2, imgBackground.getHeight() / 2);
        imgBackground.setPosition((Constants.VIEWPORT_WIDTH-imgBackground.getWidth())/2, 0);



        return layer;
    }

    private Table buildPlayMenu () {
        // TODO button for "skip tutorial"
        btnStartGame = new ImageButton(Assets.instance.buttons.playButtonStyle);

        Table tbl = new Table();
        tbl.add(btnStartGame);
        tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth()/2 , 100);
        btnStartGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        return tbl;
    }

    private void onPlayClicked () {
        game.setScreen(new TreeScreen(game,true), ScreenTransitionFade.init(1));
    }
}
