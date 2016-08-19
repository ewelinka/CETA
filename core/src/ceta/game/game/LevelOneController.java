package ceta.game.game;

import ceta.game.game.objects.Latter;
import ceta.game.util.LatterManager;
import ceta.game.util.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by ewe on 8/11/16.
 */
public class LevelOneController {
    public static final String TAG = LevelOneController.class.getName();
    private LatterManager latterManager;
    private VirtualBlocksManager virtualBlocksManager;

    private short [] detected_numbers;
    private short [] previous_detected;
    private short [] remove;
    private short [] add;
    private short toRemove;
    private short toAdd;
    private short currentDiff;

    public LevelOneController(Stage stage){
        latterManager = new LatterManager(stage);
        virtualBlocksManager = new VirtualBlocksManager(stage);
        init();

    }

    public void init(){
        initValues();
        virtualBlocksManager.init();
        latterManager.init();

    }

    private void initValues(){
        detected_numbers = new short [5];
        previous_detected = new short [5];
        remove = new short [5];
        add = new short [5];

        for(short i = 0; i<5;i++){
            detected_numbers[i] = 0;
            previous_detected[i] = 0;
            remove[i] = 0;
            add[i] = 0;
        }

        toRemove = 0;
        toAdd = 0;
        currentDiff = 0;
    }

    public void update(){
        virtualBlocksManager.updateDetected();
        //previous_detected = previous_detected;
        previous_detected = Arrays.copyOf(detected_numbers, detected_numbers.length);
       // System.arraycopy( detected_numbers, 0, previous_detected, 0, detected_numbers.length );
        detected_numbers = virtualBlocksManager.getDetectedBlocks();
        findDifferences();
        updateLatters();
    }

    private void findDifferences(){
        // ojo!!! lo que se corresponde a la pieza 1 esta en la posicion 0 !!
        toRemove = 0;
        toAdd = 0;
        for(short i = 0; i<5;i++){
            //if(i==0) Gdx.app.debug(TAG,previous_detected[i]+" "+detected_numbers[i]);
            currentDiff = (short)(previous_detected[i] - detected_numbers[i]);
            if ( currentDiff < 0){
                Gdx.app.debug(TAG,"we should add "+currentDiff+" to:"+i+" position");
                add[i] = (short)Math.abs(currentDiff);
                remove[i] = 0;
                toAdd +=1;
            }
            else if (currentDiff > 0){
                Gdx.app.debug(TAG,"we should remove "+currentDiff+" to:"+i+" position");
                remove[i] = currentDiff;
                add[i] = 0;
                toRemove +=1;
            }
            else{
                remove[i] = 0;
                add[i] = 0;
            }

        }

    }

    public Latter getLastLatter(){
        return latterManager.getLastLatter();
    }

    private void updateLatters(){
        // set "to add" and "to remove" in latter manager
        latterManager.update(toRemove,toAdd,remove,add);
    }


}
