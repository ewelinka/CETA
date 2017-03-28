package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 1/17/17.
 */
public class SimpleMenuScreen extends AbstractGameScreen {
    private ImageButton btnMenuPlay, btnMenuExit, btnLevels;
    private Image imgBackground, brunoHead,brunoBody,screw,shadow, logo;

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

    public void buildStage() {
        Table layerBackground = buildBackgroundLayer();
        Table buttons = buildButtonsLayer();
        Table bruno = buildBruno();
        stage.clear();
        Stack stack = new Stack();
        stack.setSize(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/2);
        //stack.setPosition(0,0);
        stack.add(layerBackground);

        //    stack.add(buttons);
        stack.add(bruno);
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
        // + Levels Button
        btnLevels = new ImageButton(Assets.instance.buttons.levelsButtonStyle);
        layer.add(btnLevels).size(110,60).padBottom(10);
        btnLevels.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onLevelsClicked();
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


        if (false) layer.debug();
        return layer;
    }

    private Table buildBruno(){
        Table layer = new Table();
        layer.left().bottom();


        brunoBody = new Image(Assets.instance.bruno.initBody);
        brunoHead = new Image(Assets.instance.bruno.initHead);
        shadow = new Image(Assets.instance.background.shadow);
        Gdx.app.log(TAG," body "+brunoBody.getImageWidth());
       // brunoBody.setOrigin(brunoBody.getWidth() / 2, brunoBody.getHeight() / 2);
        shadow.setSize(105,12);
        shadow.setPosition(32,37);

        brunoBody.setSize(76,64);
        brunoHead.setSize(76,56);
        //
        brunoBody.setPosition(50,40);
        brunoHead.setPosition(50,40+brunoBody.getHeight()-10);

        brunoHead.addAction(sequence(
                delay(5.0f),
                Actions.rotateBy(50,1.0f),
                Actions.rotateBy(-50,0.8f),
                delay(2.0f),
//                run(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(Constants.WITH_CV)
//                            game.setScreen(new TutorialCvScreen(game),ScreenTransitionFade.init(0.75f));
//                        else
//                            game.setScreen(new TutorialScreen(game),ScreenTransitionFade.init(0.75f));
//                    }
//                })
                run(new Runnable() {
                    @Override
                    public void run() {

                        game.setScreen(new IntroScreen(game),ScreenTransitionFade.init(0.75f));
                    }
                })
        ));




        screw = new Image(Assets.instance.background.bigScrew);
        screw.setSize(36,43);
        screw.setOrigin(screw.getWidth()/2,screw.getHeight()/2);
        screw.setPosition(Constants.VIEWPORT_WIDTH/2,50+brunoBody.getHeight()-10);
        screw.addAction(
                sequence(
                        delay(3.0f),
                        parallel(
                                Actions.rotateBy(640, 3.0f),
                                sequence(
                                        Actions.moveTo(brunoHead.getX() +40,screw.getY(),3.0f),
                                        parallel(
                                                Actions.moveTo(brunoHead.getX()-25,screw.getY()-20,0.3f),
                                                Actions.scaleTo(0,0,0.3f)
                                        )
                                )
                        )
                )
        );


        logo  = new Image(Assets.instance.menu.logoBruno);
        logo.setSize(566/2,562/2);
        logo.setPosition(10,Constants.VIEWPORT_HEIGHT/2);
        logo.addAction(
                    sequence(
                            delay(1.5f),
                            Actions.moveTo(logo.getX(),Constants.VIEWPORT_HEIGHT/2-281,3.0f, Interpolation.bounceOut)
                    )
        );

        layer.addActor(shadow);
        layer.addActor(brunoBody);
        layer.addActor(screw);
        layer.addActor(brunoHead);
        layer.addActor(logo);
        return layer;


    }



    private void onPlayClicked () {
        // ScreenTransition transition = ScreenTransitionFade.init(0.75f);
        //game.setScreen(screen1, transition);

        game.getLevelsManager().goToFirstUncompletedLevel();
//        game.getLevelsManager().goToNextLevel();
        //game.setScreen(new Level1VerticalScreen(game,1), transition);
    }

    private void onExitClicked () {


    }

    private void onLevelsClicked () {


    }
}
