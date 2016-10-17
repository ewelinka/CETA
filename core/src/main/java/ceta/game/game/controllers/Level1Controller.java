package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level1;
import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.Price;
import ceta.game.screens.DirectedGame;
import ceta.game.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1Controller extends AbstractWorldController{
    private static final String TAG = Level1Controller.class.getName();
    private Rectangle r1 = new Rectangle();
    private Rectangle r2 = new Rectangle();

    private RoboticArmManager roboticArmManager;
    private VirtualBlocksManager virtualBlocksManager;
    private Stage stage;

    public Level1Controller (DirectedGame game, Stage stage) {
       // game = game;
        this.stage = stage;
        roboticArmManager = new RoboticArmManager(stage);
        // TODO change after wizard of oz
        virtualBlocksManager = new VirtualBlocksManager(stage);
        super.init(game);
        localInit();

    }

    private void localInit () {
        Gdx.app.log(TAG," local init");
        initLevel();
        virtualBlocksManager.init();
        roboticArmManager.init();
    }


    private void initLevel(){
        //it will load level data
        level = new Level1(stage);
        cameraHelper.setTarget(null);
        score = 0;
    }


    @Override
    public void update (float deltaTime) {
        // winning condition
        if (score >= GamePreferences.instance.collectedToWin) {
            // this will be checked in renderer
            //weWon = true;
            //goToFinalScreen();

        }
        handleDebugInput(deltaTime);
        level.update(deltaTime); //stage.act()

        if(GamePreferences.instance.actionSubmit){
            // if we are counting
            if(countdownOn){
                // we shake bruno
                level.bruno.shake();
                // if we reached the time
                if(coutdownCurrentTime < 0 ){
                    Gdx.app.log(TAG, "wowowoowow action submit!");
                    virtualBlocksManager.updateDetected();
                    // computer vision manager update detected
                    // cvManager.update();

                    updateArmPieces(virtualBlocksManager.getNewDetected(),virtualBlocksManager.getToRemove());
                    countdownOn = false;
                    coutdownCurrentTime = GamePreferences.instance.countdownMax;
                }
                else // we still count
                    coutdownCurrentTime-=deltaTime;
            }

        }else{
            virtualBlocksManager.updateDetected();
            // computer vision manager update detected
            // cvManager.update();
            updateArmPieces(virtualBlocksManager.getNewDetected(),virtualBlocksManager.getToRemove());
        }


        // TODO check if it should goes to the end
        testCollisions();

        cameraHelper.update(deltaTime);

    }

    @Override
    public void dispose() {

    }

    private void testCollisions () {
        // TODO check for top Y and x range
        // now if the coin passes by the middle if also works (make sense! but not for us!)
        ArmPiece lastArm = getLastArmPiece();
        if(lastArm != null){
            // we check first when the move action is over
            // if not we have problems with pieces passing thought the coin
            if (lastArm.getActions().size == 0){
                r1.set(lastArm.getX()+lastArm.getWidth(), lastArm.getY(), 2, lastArm.getHeight());

                r2.set(level.price.getX(), level.price.getY(), level.price.bounds.width, level.price.bounds.height);
                if (r1.overlaps(r2))
                    onCollisionBrunoWithGoldCoin(level.price);
            }
        }
    }

    private void onCollisionBrunoWithGoldCoin(Price goldcoin) {
        if(goldcoin.getActions().size == 0){ // we act just one time!
            AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
            Gdx.app.log(TAG,"score before: "+score);
            score += 1;
            Gdx.app.log(TAG,"score after: "+score);
            goldcoin.moveToNewPosition(level.bruno.getX()+level.bruno.getWidth());
        }

    };


    public ArmPiece getLastArmPiece(){
        return roboticArmManager.getLastArmPiece();
    }



    private void updateArmPieces(ArrayList toAdd, ArrayList toRemove){
        roboticArmManager.update(toAdd,toRemove);
    }

    public VirtualBlocksManagerOSC getVirtualBlocksManagerOSC(){
        return  new VirtualBlocksManagerOSC(stage);
        //return virtualBlocksManager;
    }


}

