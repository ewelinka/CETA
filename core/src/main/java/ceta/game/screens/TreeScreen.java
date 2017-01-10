package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.TreeGear;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 1/9/17.
 */
public class TreeScreen extends AbstractGameScreen {
    private static final String TAG = TreeScreen.class.getName();
    private Button btnMenuPlay;
    private Image imgBackground;
    private TreeGear level1gear, level2gear,level3gear,level4gear, level5gear;
    private int level1completedNr, level2completedNr, level3completedNr, level4completedNr,level5completedNr;

    public TreeScreen(DirectedGame game) {
            super(game,1);
            initLevelCompleted();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
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

    private void buildStage() {
        imgBackground = new Image(Assets.instance.tree.tree);
        imgBackground.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        imgBackground.setPosition(0, 0);
        stage.addActor(imgBackground);

        level1gear = new TreeGear(Assets.instance.tree.gear1);
        level1gear.setPosition(300, 340);
        stage.addActor(level1gear);

        level2gear = new TreeGear(Assets.instance.tree.gear2);
        level2gear.setPosition(160, 357);
        stage.addActor(level2gear);

        level3gear = new TreeGear(Assets.instance.tree.gear3);
        level3gear.setPosition(224, 437);
        stage.addActor(level3gear);

        level4gear = new TreeGear(Assets.instance.tree.gear4);
        level4gear.setPosition(238, 320);
        stage.addActor(level4gear);

        level5gear = new TreeGear(Assets.instance.tree.gear5);
        level5gear.setPosition(335, 481);
        stage.addActor(level5gear);

        enableGears();

        btnMenuPlay = new ImageButton(Assets.instance.buttons.playButtonStyle);
        stage.addActor(btnMenuPlay);
        btnMenuPlay.setSize(88*2,60*2);
        btnMenuPlay.setPosition(Constants.VIEWPORT_WIDTH/2 - 100, 100);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });


    }

    private void enableGears(){
        int nowLevel = game.getLevelsManager().getLastLevelCompleted();
        Gdx.app.log(TAG, " current level "+nowLevel);
        if(nowLevel < level5completedNr){
            level5gear.setActive(false);
            Gdx.app.log(TAG, "5 gray");
        }
        if(nowLevel < level4completedNr){
            level4gear.setActive(false);
            Gdx.app.log(TAG, " 4 gray");
        }
        if(nowLevel < level3completedNr){
            level3gear.setActive(false);
            Gdx.app.log(TAG, "3 gray");
        }
        if(nowLevel < level2completedNr){
            level2gear.setActive(false);
            Gdx.app.log(TAG, "2 gray");
        }
        if(nowLevel < level1completedNr){
            level1gear.setActive(false);
            Gdx.app.log(TAG, "1 gray");
        }

    }

    private void onPlayClicked () {
        game.getLevelsManager().goToFirstUncompletedLevel();

    }

    private void initLevelCompleted(){
        level1completedNr = 1;
        level2completedNr = 2;
        level3completedNr = 3;
        level4completedNr = 4;
        level5completedNr = 5;
    }


}
