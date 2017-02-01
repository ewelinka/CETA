package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.game.objects.TreeArrow;
import ceta.game.game.objects.TreeGear;
import ceta.game.util.Constants;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
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
    private TreeGear level1gear, level2gear,level3gear,level4gear, level5gear, level6gear;
    private TreeArrow arrow;
    private int[][] gearsPositions;
    private int[][] arrowPositions;

    private boolean gameInit;

    public TreeScreen(DirectedGame game) {
        this(game, false);
    }

    public TreeScreen(DirectedGame game, boolean gameInit) {
        super(game);
        this.gameInit = gameInit;
        gearsPositions = new int[][]{
                {228, 437},
                {162,354},
                {238,329},
                {304,328},
                {324,357},
                {329,482}
        };

        arrowPositions = new int[][]{
                {86,433},
                {90,336},
                {210,275},
                {320,280},
                {437,328},
                {444,425}
        };
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
        Gdx.app.log(TAG," build stageeeee ...... !!");
        imgBackground = new Image(Assets.instance.tree.tree);
        imgBackground.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        imgBackground.setPosition(0, 0);
        stage.addActor(imgBackground);

        level1gear = new TreeGear(Assets.instance.tree.gear3,Assets.instance.tree.gear3inactive);
        level2gear = new TreeGear(Assets.instance.tree.gear2,Assets.instance.tree.gear2inactive);
        level3gear = new TreeGear(Assets.instance.tree.gear4,Assets.instance.tree.gear4inactive);
        level4gear = new TreeGear(Assets.instance.tree.gear6,Assets.instance.tree.gear6inactive);
        level5gear = new TreeGear(Assets.instance.tree.gear1,Assets.instance.tree.gear1inactive);
        level6gear = new TreeGear(Assets.instance.tree.gear5,Assets.instance.tree.gear5inactive);

        level1gear.setPosition(gearsPositions[0][0], gearsPositions[0][1]);
        stage.addActor(level1gear);

        level2gear.setPosition(gearsPositions[1][0], gearsPositions[1][1]);
        stage.addActor(level2gear);

        level3gear.setPosition(gearsPositions[2][0], gearsPositions[2][1]);
        stage.addActor(level3gear);

        level4gear.setPosition(gearsPositions[3][0], gearsPositions[3][1]);
        stage.addActor(level4gear);

        level5gear.setPosition(gearsPositions[4][0], gearsPositions[4][1]);
        stage.addActor(level5gear);

        level6gear.setPosition(gearsPositions[5][0], gearsPositions[5][1]);
        stage.addActor(level6gear);

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
        if(nowLevel < Constants.L6_COMPLETED_NR){
            level6gear.setActive(false);
            arrow.setPosition(arrowPositions[5][0],arrowPositions[5][1]); // pos 6
            // Gdx.app.log(TAG, "5 gray");
            newActivated = 5;
        }
        if(nowLevel < Constants.L5_COMPLETED_NR){
            level5gear.setActive(false);
            arrow.setPosition(arrowPositions[4][0],arrowPositions[4][1]); // pos 5
           // Gdx.app.log(TAG, "5 gray");
            newActivated = 4;
        }
        if(nowLevel < Constants.L4_COMPLETED_NR){
            level4gear.setActive(false);
            arrow.setPosition(arrowPositions[3][0],arrowPositions[3][1]); // pos 4
           // Gdx.app.log(TAG, " 4 gray");
            newActivated = 3;
        }
        if(nowLevel < Constants.L3_COMPLETED_NR){
            level3gear.setActive(false);
            arrow.setPosition(arrowPositions[2][0],arrowPositions[2][1]); // pos 3
           // Gdx.app.log(TAG, "3 gray");
            newActivated = 2;
        }
        if(nowLevel < Constants.L2_COMPLETED_NR){
            level2gear.setActive(false);
            arrow.setPosition(arrowPositions[1][0],arrowPositions[1][1]); // pos 2
           // Gdx.app.log(TAG, "2 gray");
            newActivated = 1;
        }
        if(nowLevel < Constants.L1_COMPLETED_NR){
            level1gear.setActive(false);
            arrow.setPosition(arrowPositions[0][0],arrowPositions[0][1]); // pos 1
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
                arrow.setPosition(arrowPositions[0][0],arrowPositions[0][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[1][0],arrowPositions[1][1],2f)));
                level1gear.setActive(false);
                level1gear.activateGear();
                break;
            case 2:
                arrow.setPosition(arrowPositions[1][0],arrowPositions[1][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[2][0],arrowPositions[2][1],2f)));
                level2gear.setActive(false);
                level2gear.activateGear();
                break;
            case 3:
                arrow.setPosition(arrowPositions[2][0],arrowPositions[2][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[3][0],arrowPositions[3][1],2f)));
                level3gear.setActive(false);
                level3gear.activateGear();
                break;
            case 4:
                arrow.setPosition(arrowPositions[3][0],arrowPositions[3][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[4][0],arrowPositions[4][1],2f)));
                level4gear.setActive(false);
                level4gear.activateGear();
                break;
            case 5:
                arrow.setPosition(arrowPositions[4][0],arrowPositions[4][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[5][0],arrowPositions[5][1],2f)));
                level5gear.setActive(false);
                level5gear.activateGear();
                break;
            case 6:
                arrow.setPosition(arrowPositions[5][0],arrowPositions[5][1]);
                arrow.addAction(sequence(delay(1.5f),Actions.alpha(0,1f)));
                level6gear.setActive(false);
                level6gear.activateGear();
                // TODO thats all falks!
                break;

        }

    }

    private void onPlayClicked () {
        game.getLevelsManager().goToFirstUncompletedLevelFromTree();

    }

}
