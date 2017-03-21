package ceta.game.game.controllers;

import ceta.game.game.Assets;
import ceta.game.game.levels.Level3Vertical;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.managers.VirtualBlocksManager;
import ceta.game.screens.DirectedGame;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

/**
 * Created by ewe on 11/9/16.
 */
public class Level3VerticalController extends NoCvController {
    private static final String TAG = Level3VerticalController.class.getName();
    private float yZero;

    public Level3VerticalController(DirectedGame game, Stage stage, int levelNr) {
        super(game, stage, levelNr);
    }

    @Override
    protected void localInit () {
        Gdx.app.log(TAG," local init with last level: "+GamePreferences.instance.lastLevel);
        virtualBlocksManager = new VirtualBlocksManager(stage);
        level = new Level3Vertical(stage,levelParams, this);

        // Bruno will be flying
        //level.bruno.setSize(Constants.BASE*1,Constants.BASE*1);
        yZero = Constants.GROUND_LEVEL - level.bruno.getHeight()/2;
//        level.bruno.setPosition(Constants.VERTICAL_MIDDLE_X - level.bruno.getWidth()/2 ,-Constants.BASE/2);
//        level.bruno.setTerminalX(Constants.VERTICAL_MIDDLE_X - level.bruno.getWidth()/2);
//        level.bruno.setTerminalY(-Constants.BASE/2);
        cameraHelper.setTarget(null);
        score = 0;
        virtualBlocksManager.init();

    }


    @Override
    protected void updateDigitalRepresentations() {
        updateBrunoVertical(virtualBlocksManager.getNewDetected(), virtualBlocksManager.getToRemoveValues());
    }

    @Override
    protected void testCollisionsInController(boolean isDynamic){
        if(isDynamic)
            testCollisionsDynamic();
        else
            testCollisionsStatic();
    }



    protected void testCollisionsStatic () {
        BrunoVertical brunoV = (BrunoVertical) level.bruno;
        if (!(brunoV.getActions().size > 0)) { // if bruno is not moving
            if(brunoV.getTerminalY() != yZero ) {
                r1.set(brunoV.getX() ,
                        brunoV.getY() ,
                        brunoV.getWidth()+Constants.PRICE_X_OFFSET, level.bruno.getHeight()/2);
                r2.set(level.price.getX(),
                        level.price.getY(),
                        level.price.getWidth()/2, level.price.getHeight()/2);

                if (r1.overlaps(r2)) {
                    onCollisionBrunoWithPriceOpenMouth(level.price, brunoV);
                    moveMade = false;
                } else {
                    if (moveMade) {
                        AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.liveLost);
                        moveMade = false;
                    }

                }
            }else{ // no blocks on the table
                if (moveMade) {
                    AudioManager.instance.playWithoutInterruption(Assets.instance.sounds.liveLost);
                    moveMade = false;
                }
            }
        }
    }

    protected void testCollisionsDynamic () {
        testCollisionsDynamicL3V(yZero);
    }

    private void updateBrunoVertical(ArrayList<Pair> toAdd, ArrayList<Integer> toRemoveValues){
        int toAddNr = 0;
        int toRemoveNr = 0;

        for(int i=0; i< toRemoveValues.size();i++) {
            toRemoveNr+= toRemoveValues.get(i);
        }
        for(int i=0; i< toAdd.size();i++) {
            toAddNr+=toAdd.get(i).getValue();

        }

        if((toAddNr - toRemoveNr) != 0)
            moveBrunoVertical(toAddNr - toRemoveNr);
    }

    private void moveBrunoVertical(int howMany){

        Gdx.app.log(TAG, " move bruno "+howMany);
        float currentTerminalY = level.bruno.getTerminalY();
//        ((BrunoVertical)(level.bruno)).moveMeToAndSetTerminalYWithBounce(Constants.VERTICAL_MIDDLE_X - level.bruno.getWidth()/2, currentTerminalY + howMany* Constants.BASE);
        ((BrunoVertical)(level.bruno)).moveMeToAndSetTerminalYWithBounce(level.bruno.getX(), currentTerminalY + howMany* Constants.BASE);

    }

}
