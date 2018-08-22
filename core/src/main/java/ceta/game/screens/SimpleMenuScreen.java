package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 6/21/18.
 */
public class SimpleMenuScreen extends AbstractGameScreen {
    private static final String TAG = SimpleMenuScreen.class.getName();
    private Image imgBackground;
    private ImageButton btnMenuPlay, btnMenuExit, btnMenuReset, btnMenuTutorial;


    public SimpleMenuScreen(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        buildStage();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/2));
        Gdx.input.setCatchBackKey(false);

        //AudioManager.instance.playWithoutInterruptionLoud(Assets.instance.sounds.thirteen);
        buildStage();
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }


    public void buildStage() {
        Table layerBackground = buildBackgroundLayer();
        Table buttons = buildButtonsLayer();

        stage.clear();
        Stack stack = new Stack();
        stack.setSize(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/2);

        stack.add(layerBackground);

        stack.add(buttons);

        stage.addActor(stack);
    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        imgBackground = new Image(Assets.instance.staticBackground.backStart);
        //imgBackground.setSize(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        imgBackground.setOrigin(imgBackground.getWidth() / 2, imgBackground.getHeight() / 2);
        imgBackground.setPosition((Constants.VIEWPORT_WIDTH-imgBackground.getWidth())/2, 0);
        //
        layer.add(imgBackground);
        return layer;
    }

    private Table buildButtonsLayer () {
        Table layer = new Table();
        layer.center().center();


        btnMenuPlay = new ImageButton(Assets.instance.buttons.playButtonStyle);
        // table.add (btnB).size (150, 200);
        layer.add(btnMenuPlay).size(88,60).padBottom(10);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        layer.row();
        // + Reset Button
        btnMenuReset = new ImageButton(Assets.instance.buttons.resetButtonStyle);
        layer.add(btnMenuReset).size(110,60).padBottom(10);
        btnMenuReset.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onResetClicked();
            }
        });
        layer.row();
        // + Tutorial Button
        btnMenuTutorial = new ImageButton(Assets.instance.buttons.tutorialButtonStyle);
        layer.add(btnMenuTutorial).size(110,60).padBottom(10);
        btnMenuTutorial.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onTutorialClicked();
            }
        });
        layer.row();

        // + Exit Button
        btnMenuExit = new ImageButton(Assets.instance.buttons.exitButtonStyle);
        layer.add(btnMenuExit).size(45,55).padBottom(10);
        btnMenuExit.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onExitClicked();
            }
        });
        layer.row();
        // + Options Button

        layer.row();
        layer.row();

        return layer;
    }

    private void onPlayClicked () {
        game.getLevelsManager().goToFirstUncompletedLevel(true);
    }

    private void onExitClicked () {
        Gdx.app.exit();
    }

    private void onResetClicked(){
        game.getLevelsManager().forceLevel(1);
        game.getLevelsManager().goToFirstUncompletedLevel(true);
    }

    private void onTutorialClicked(){
        if(Constants.WITH_CV)
            game.setScreen(new TutorialCvScreen(game), ScreenTransitionFade.init(1));
        else
            game.setScreen(new TutorialScreen(game), ScreenTransitionFade.init(1));

    }

}
