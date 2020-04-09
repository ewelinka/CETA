package ceta.game.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ceta.game.game.Assets;
import ceta.game.game.objects.TreeArrow;
import ceta.game.game.objects.TreeGear;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;

/**
 * Created by ewe on 1/9/17.
 */
public class TreeScreen extends AbstractGameScreen {
    private static final String TAG = TreeScreen.class.getName();
//    private Button btnMenuPlay;
    private Image imgBackground;
    protected TreeGear level1gear, level2gear,level3gear,level4gear, level5gear, level6gear;
    protected Image part1,part2,part3,part4,part5,part6;
    private Image b1,b2,b3,b4,b5;
    protected TreeArrow arrow;
    protected int[][] arrowPositions,partsPositions,gearsPositions;
    private float automaticPassTime;
    private boolean shouldPass;
    private boolean introStarted;
    private boolean shouldFadeOutMusic;
    protected boolean gameInit;


    public TreeScreen(DirectedGame game, boolean gameInit){
        this(game, gameInit, true); // all tree screens fade out music!
    }

    protected TreeScreen(DirectedGame game, boolean gameInit, boolean shouldFadeOutMusic) {
        super(game);
        automaticPassTime = Constants.AUTOMATIC_LEVEL_PASS;
        shouldPass = true;
        introStarted = false;
        this.gameInit = gameInit;
        this.shouldFadeOutMusic = shouldFadeOutMusic;
        gearsPositions = new int[][]{
                {228, 437},
                {162,354},
                {238,329},
                {304,328},
                {310,365},
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

        partsPositions = new int[][]{
                {111,559},
                {78,670},
                {139,900},
                {310,762},
                {502,833},
                {481,573}
        };



    }

    @Override
    public void render(float deltaTime) {
        if(shouldFadeOutMusic && AudioManager.instance.getMusicVolume() > 0){
            float newVol = AudioManager.instance.getMusicVolume() -  0.001f;
            if(newVol >= 0 )
                AudioManager.instance.setMusicVol(newVol);
            else{
                AudioManager.instance.stopMusic();
                shouldFadeOutMusic = false;
            }

        }
        if(automaticPassTime < 0 && shouldPass){
            game.getLevelsManager().goToUncompletedLevel();
            shouldPass = false;
        }
        else
            automaticPassTime-=deltaTime;
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
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
        Gdx.input.setCatchBackKey(true);
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

    protected void buildStage() {
        Gdx.app.log(TAG," build stageeeee ...... !! last level "+ game.levelsManager.getLastLevelCompleted()+" last -> "+Constants.LAST_LEVEL_NR );
        imgBackground = new Image(Assets.instance.tree.tree);
        imgBackground.setSize(Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        imgBackground.setPosition(0, 0);
        stage.addActor(imgBackground);

        addGears();
        addParts();
        enableGears();

        //addPlayButton();

    }

    protected void addGears(){


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

        arrow = new TreeArrow(Assets.instance.bruno.body01);
        stage.addActor(arrow); // now add to place the arrow most to front
    }

    private void addParts(){
        part1 = new Image(Assets.instance.tree.part1);
        part2 = new Image(Assets.instance.tree.part2);
        part3 = new Image(Assets.instance.tree.part3);
        part4 = new Image(Assets.instance.tree.part4);
        part5 = new Image(Assets.instance.tree.part5);
        part6 = new Image(Assets.instance.tree.part6);

        part1.setPosition(partsPositions[0][0], partsPositions[0][1]);
        part2.setPosition(partsPositions[1][0], partsPositions[1][1]);
        part3.setPosition(partsPositions[2][0], partsPositions[2][1]);
        part4.setPosition(partsPositions[3][0], partsPositions[3][1]);
        part5.setPosition(partsPositions[4][0], partsPositions[4][1]);
        part6.setPosition(partsPositions[5][0], partsPositions[5][1]);



        stage.addActor(part1);
        stage.addActor(part2);
        stage.addActor(part3);
        stage.addActor(part4);
        stage.addActor(part5);
        stage.addActor(part6);

    }

//    private void addPlayButton(){
//        btnMenuPlay = new ImageButton(Assets.instance.buttons.playButtonStyle);
//        stage.addActor(btnMenuPlay);
//        btnMenuPlay.setSize(88*2,60*2);
//        btnMenuPlay.setPosition(Constants.VIEWPORT_WIDTH/2 - 100, 100);
//        btnMenuPlay.addListener(new ChangeListener() {
//            @Override
//            public void changed (ChangeEvent event, Actor actor) {
//                onPlayClicked();
//            }
//        });
//
//    }


    protected void enableGears(){
        int nowLevel = game.getLevelsManager().getLastLevelCompleted();
        int newActivated = 6;
        Gdx.app.log(TAG, " current level "+nowLevel);


        if(nowLevel < Constants.L6_COMPLETED_NR){
            level6gear.setActive(false);
            part6.setColor(1,1,1,0);
            arrow.setPosition(arrowPositions[5][0],arrowPositions[5][1]); // pos 6
            // Gdx.app.log(TAG, "5 gray");
            newActivated = 5;
        }
        if(nowLevel < Constants.L5_COMPLETED_NR){
            level5gear.setActive(false);
            part5.setColor(1,1,1,0);
            arrow.setPosition(arrowPositions[4][0],arrowPositions[4][1]); // pos 5
           // Gdx.app.log(TAG, "5 gray");
            newActivated = 4;
        }
        if(nowLevel < Constants.L4_COMPLETED_NR){
            level4gear.setActive(false);
            part4.setColor(1,1,1,0);
            arrow.setPosition(arrowPositions[3][0],arrowPositions[3][1]); // pos 4
           // Gdx.app.log(TAG, " 4 gray");
            newActivated = 3;
        }
        if(nowLevel < Constants.L3_COMPLETED_NR){
            level3gear.setActive(false);
            part3.setColor(1,1,1,0);
            arrow.setPosition(arrowPositions[2][0],arrowPositions[2][1]); // pos 3
           // Gdx.app.log(TAG, "3 gray");
            newActivated = 2;
        }
        if(nowLevel < Constants.L2_COMPLETED_NR){
            level2gear.setActive(false);
            part2.setColor(1,1,1,0);
            arrow.setPosition(arrowPositions[1][0],arrowPositions[1][1]); // pos 2
           // Gdx.app.log(TAG, "2 gray");
            newActivated = 1;
        }
        if(nowLevel < Constants.L1_COMPLETED_NR){
            level1gear.setActive(false);
            part1.setColor(1,1,1,0);
            arrow.setPosition(arrowPositions[0][0],arrowPositions[0][1]); // pos 1
            newActivated = 0;
           // Gdx.app.log(TAG, "1 gray");
        }

        if(!gameInit)
            enableGearsWithAnimation(newActivated);
        else{
            switch(newActivated){
                case 0:
                    level1gear.setIsMoving(true,0.2f);
                    break;
                case 1:
                    level2gear.setIsMoving(true,0.2f);
                    break;
                case 2:
                    level3gear.setIsMoving(true,0.2f);
                    break;
                case 3:
                    level4gear.setIsMoving(true,0.2f);
                    break;
                case 4:
                    level5gear.setIsMoving(true,0.2f);
                    break;
                case 5:
                    level6gear.setIsMoving(true,0.2f);
                    break;
            }
        }
    }

    protected void enableGearsWithAnimation(int newActivated){
        Gdx.app.log(TAG," === enableGearsWithAnimation "+newActivated);

        switch(newActivated){
            case 0:
                level1gear.setIsMoving(true,0.2f);
                break;
            case 1:
                arrow.setPosition(arrowPositions[0][0],arrowPositions[0][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[1][0],arrowPositions[1][1],2f)));
                level1gear.setActive(false);
                level1gear.activateGear();
                part1.setColor(1,1,1,0);
                part1.addAction(sequence(delay(0.8f),alpha(1,0.5f),alpha(0,0.5f),alpha(1,0.2f),alpha(0,0.2f),alpha(1,0.1f)));
                level2gear.setIsMoving(true,3.5f);
                automaticPassTime = 8.0f+4.0f;
                if(!introStarted){
                    introStarted = true;
                    readIntroWithDelay(Assets.instance.sounds.intro2);
                }
                break;
            case 2:
                arrow.setPosition(arrowPositions[1][0],arrowPositions[1][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[2][0],arrowPositions[2][1],2f)));
                level2gear.setActive(false);
                level2gear.activateGear();
                part2.setColor(1,1,1,0);
                part2.addAction(sequence(delay(0.8f),alpha(1,0.5f),alpha(0,0.5f),alpha(1,0.2f),alpha(0,0.2f),alpha(1,0.1f)));
                level3gear.setIsMoving(true,3.5f);
                automaticPassTime = 7.0f+4.0f;
                if(!introStarted){
                    introStarted = true;
                    readIntroWithDelay(Assets.instance.sounds.intro3);
                }
                break;
            case 3:
                arrow.setPosition(arrowPositions[2][0],arrowPositions[2][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[3][0],arrowPositions[3][1],2f)));
                level3gear.setActive(false);
                part3.setColor(1,1,1,0);
                part3.addAction(sequence(delay(0.8f),alpha(1,0.5f),alpha(0,0.5f),alpha(1,0.2f),alpha(0,0.2f),alpha(1,0.1f)));
                level3gear.activateGear();
                level4gear.setIsMoving(true,3.5f);
                automaticPassTime = 14.0f+4.0f;
                if(!introStarted) {
                    introStarted = true;
                    readIntroWithDelay(Assets.instance.sounds.intro4);
                }
                break;
            case 4:
                arrow.setPosition(arrowPositions[3][0],arrowPositions[3][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[4][0],arrowPositions[4][1],2f)));
                level4gear.setActive(false);
                part4.setColor(1,1,1,0);
                part4.addAction(sequence(delay(0.8f),alpha(1,0.5f),alpha(0,0.5f),alpha(1,0.2f),alpha(0,0.2f),alpha(1,0.1f)));
                level4gear.activateGear();
                level5gear.setIsMoving(true,3.5f);
                automaticPassTime = 8.0f+4.0f;
                if(!introStarted){
                    introStarted = true;
                    readIntroWithDelay(Assets.instance.sounds.intro5);
                }
                break;
            case 5:
                arrow.setPosition(arrowPositions[4][0],arrowPositions[4][1]);
                arrow.addAction(sequence(delay(1.5f),moveTo(arrowPositions[5][0],arrowPositions[5][1],2f)));
                level5gear.setActive(false);
                part5.setColor(1,1,1,0);
                part5.addAction(sequence(delay(0.8f),alpha(1,0.5f),alpha(0,0.5f),alpha(1,0.2f),alpha(0,0.2f),alpha(1,0.1f)));
                level5gear.activateGear();
                level6gear.setIsMoving(true,3.5f);
                automaticPassTime = 9.0f+4.0f;
                if(!introStarted){
                    introStarted = true;
                    readIntroWithDelay(Assets.instance.sounds.intro6);
                }
                break;
            case 6:
                arrow.setPosition(arrowPositions[5][0],arrowPositions[5][1]);
                arrow.addAction(sequence(delay(1.5f),Actions.alpha(0,1f)));
                level6gear.setActive(false);
                part6.setColor(1,1,1,0);
                part6.addAction(sequence(delay(0.8f),alpha(1,0.5f),alpha(0,0.5f),alpha(1,0.2f),alpha(0,0.2f),alpha(1,0.1f)));
                level6gear.activateGear();
                // TODO that's all folks!
                Gdx.app.log(TAG,"something special for the end");
                addBrunos();
                automaticPassTime = 14.0f+4.0f;
                if(!introStarted){
                    introStarted = true;
                    readIntroWithDelay(Assets.instance.sounds.gracias);
                }
                break;

        }

    }

//    private void onPlayClicked () {
//        game.getLevelsManager().goToUncompletedLevel();
//
//    }

    private void addBrunos(){
        float s = 0.2f;
        b1 = new Image(Assets.instance.bruno.body01);
        b1.setPosition(-50,155);
        b1.addAction(sequence(
                delay(1.6f),
                moveTo(157,157,1.0f),
                delay(0.3f),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s)
        ));
        stage.addActor(b1);


        b2 = new Image(Assets.instance.bruno.body02);
        b2.setPosition(-150,132);
        b2.addAction(sequence(
                delay(1.6f),
                moveTo(120,150,1.5f),
                delay(0.3f),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s)
        ));
        stage.addActor(b2);

        b5 = new Image(Assets.instance.bruno.body05);
        b5.setPosition(670,155);
        b5.setScale(-1,1);
        b5.addAction(sequence(
                delay(1.6f),
                moveTo(470,147,1.2f),
                delay(0.3f),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s)
        ));
        stage.addActor(b5);

        b3 = new Image(Assets.instance.bruno.body03);
        b3.setPosition(670,155);
        b3.addAction(sequence(
                delay(1.6f),
                moveTo(430,137,1.0f),
                delay(0.3f),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s)
        ));
        b3.setScale(-1,1);
        stage.addActor(b3);

        b4 = new Image(Assets.instance.bruno.body04);
        b4.setPosition(-50,155);
        b4.addAction(sequence(
                delay(1.6f),
                moveTo(90,137,0.9f),
                delay(0.3f),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,30,s),
                moveBy(0,-30,s),
                moveBy(0,40,s),
                moveBy(0,-40,s),
                moveBy(0,40,s),
                moveBy(0,-40,s)
        ));
        stage.addActor(b4);

    }

    private void readIntroWithDelay(final Sound introSound){
        Actor reader = new Actor();
        stage.addActor(reader);

        reader.addAction(sequence(
                delay(2.0f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        AudioManager.instance.playWithoutInterruptionLoud(introSound);
                    }
                })

        ));
    }

    private void readIntroWithDelay(final Music introSound) {
        Actor reader = new Actor();
        stage.addActor(reader);

        reader.addAction(sequence(
                delay(2.0f),
                run(new Runnable() {
                    @Override
                    public void run() {
                        AudioManager.instance.playWithoutInterruptionLoud(introSound);
                    }
                })

        ));
    }


}
