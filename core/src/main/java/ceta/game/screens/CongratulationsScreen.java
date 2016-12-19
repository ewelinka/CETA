package ceta.game.screens;

import ceta.game.CetaGame;
import ceta.game.game.Assets;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;

/**
 * Created by ewe on 9/21/16.
 */
public class CongratulationsScreen extends AbstractGameScreen {
    private static final String TAG = CongratulationsScreen.class.getName();


    private ImageButton btnStartGame;
    private Image imgBackground;
    private Image congrats;


    public CongratulationsScreen(DirectedGame game) {
        super(game,1);
    }


    public void buildStage() {

        Table layerBackground = buildBackgroundLayer();
        Table playMenu = buildPlayMenu();

        stage.addActor(layerBackground);
        stage.addActor(playMenu);
    }

    public void render (float deltaTime){
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();

    };
    public void resize (int width, int height){
        stage.getViewport().update(width, height, true);
        buildStage();
    };
    public void show (){
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));

        buildStage();
    };
    public void hide (){

        stage.dispose();

    };
    public void pause (){};

    private void onPlayClicked () {
//        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
//        game.setScreen(new Level1HorizontalScreen(game), transition);
        game.getLevelsManager().goToNextLevel();
    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        imgBackground = new Image(Assets.instance.finishBackGround.finishBack);
        congrats = new Image(Assets.instance.finishBackGround.excelentWork);

        layer.addActor(imgBackground);
        imgBackground.setOrigin(imgBackground.getWidth() / 2, imgBackground.getHeight() / 2);
        imgBackground.setPosition((Constants.VIEWPORT_WIDTH-imgBackground.getWidth())/2,
                40);
        //imgBackground.setHeight(Constants.VIEWPORT_HEIGHT/2);

        layer.addActor(congrats);
        congrats.setOrigin(congrats.getWidth() / 2, congrats.getHeight() / 2);
        congrats.setPosition(0,400);
        congrats.addAction(sequence(
                scaleTo(0, 0),
                delay(0.3f),
                parallel(moveBy(0, 100, 1.5f, Interpolation.swingOut),
                        scaleTo(1.0f, 1.0f, 0.25f, Interpolation.linear),
                        alpha(1.0f, 0.5f))));
        return layer;
    }

    private Table buildPlayMenu () {
        /// ------------------ start -- just to create a simple button!! what a caos!!

        btnStartGame = new ImageButton(Assets.instance.buttons.playButtonStyle);

        Table tbl = new Table();
        //tbl.left().bottom();
        tbl.add(btnStartGame);
        // center of the screen
        // tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth() , Constants.VIEWPORT_HEIGHT/2);
        tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth() , 100);
        btnStartGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        return tbl;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }


    }