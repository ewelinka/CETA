package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.TreeArrow;
import ceta.game.game.objects.TreeGear;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 1/9/17.
 */
public class TreeScreen extends AbstractGameScreen {
    private static final String TAG = TreeScreen.class.getName();
    private Button btnMenuPlay;
    private Image imgBackground;
    private TreeGear level1gear, level2gear,level3gear,level4gear, level5gear;
    private TreeArrow arrow;

    private boolean gameInit;

    public TreeScreen(DirectedGame game) {
        this(game, false);
    }

    public TreeScreen(DirectedGame game, boolean gameInit) {
        super(game);
        this.gameInit = gameInit;

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

        level1gear = new TreeGear(Assets.instance.tree.gear1,Assets.instance.tree.gear1inactive);
        level1gear.setPosition(300, 340);
        stage.addActor(level1gear);

        level2gear = new TreeGear(Assets.instance.tree.gear2,Assets.instance.tree.gear2inactive);
        level2gear.setPosition(160, 357);
        stage.addActor(level2gear);

        level3gear = new TreeGear(Assets.instance.tree.gear3,Assets.instance.tree.gear3inactive);
        level3gear.setPosition(224, 437);
        stage.addActor(level3gear);

        level4gear = new TreeGear(Assets.instance.tree.gear4,Assets.instance.tree.gear4inactive);
        level4gear.setPosition(238, 320);
        stage.addActor(level4gear);

        level5gear = new TreeGear(Assets.instance.tree.gear5,Assets.instance.tree.gear5inactive);
        level5gear.setPosition(335, 481);
        stage.addActor(level5gear);

        arrow = new TreeArrow(Assets.instance.tree.arrow);
        stage.addActor(arrow); // now add to place the arrow most to front

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
        int newActivated = 0;
        Gdx.app.log(TAG, " current level "+nowLevel);
        if(nowLevel < Constants.L5_COMPLETED_NR){
            level5gear.setActive(false);
            arrow.setPosition(444,425); // pos 5
           // Gdx.app.log(TAG, "5 gray");
            newActivated = 4;
        }
        if(nowLevel < Constants.L4_COMPLETED_NR){
            level4gear.setActive(false);
            arrow.setPosition(257,273); // pos 4
           // Gdx.app.log(TAG, " 4 gray");
            newActivated = 3;
        }
        if(nowLevel < Constants.L3_COMPLETED_NR){
            level3gear.setActive(false);
            arrow.setPosition(86,433); // pos 3
           // Gdx.app.log(TAG, "3 gray");
            newActivated = 2;
        }
        if(nowLevel < Constants.L2_COMPLETED_NR){
            level2gear.setActive(false);
            arrow.setPosition(90,336); // pos 2
           // Gdx.app.log(TAG, "2 gray");
            newActivated = 1;
        }
        if(nowLevel < Constants.L1_COMPLETED_NR){
            level1gear.setActive(false);
            arrow.setPosition(434,328); // pos 1
           // Gdx.app.log(TAG, "1 gray");
        }

        if(!gameInit)
            enableGearsWithAnimation(newActivated);
    }

    private void enableGearsWithAnimation(int newActivated){
        switch(newActivated){
            case 0:
                break;
            case 1:
                arrow.setPosition(434,328);
                arrow.addAction(sequence(delay(1.5f),moveTo(90,336,2f)));
                level1gear.setActive(false);
                level1gear.activateGear();
                break;
            case 2:
                arrow.setPosition(90,336);
                arrow.addAction(sequence(delay(1.5f),moveTo(86,433,2f)));
                level2gear.setActive(false);
                level2gear.activateGear();
                break;
            case 3:
                arrow.setPosition(86,433);
                arrow.addAction(sequence(delay(1.5f),moveTo(257,273,2f)));
                level3gear.setActive(false);
                level3gear.activateGear();
                break;
            case 4:
                arrow.setPosition(257,273);
                arrow.addAction(sequence(delay(1.5f),moveTo(444,425,2f)));
                level4gear.setActive(false);
                level4gear.activateGear();
                break;
            case 5:
                arrow.setPosition(444,425);
                arrow.addAction(Actions.alpha(0,1f));
                level5gear.setActive(false);
                level5gear.activateGear();
                break;

        }

    }

    private void onPlayClicked () {
        game.getLevelsManager().goToFirstUncompletedLevelFromTree();

    }

}
