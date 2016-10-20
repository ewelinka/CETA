package ceta.game.util;

import ceta.game.game.objects.VirtualBlock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ewe on 8/11/16.
 */
public abstract class AbstractBlocksManager {
    public static final String TAG = AbstractBlocksManager.class.getName();
   // private short [] detected_blocks = {0,0,0,0,0};
    // a pair of shorts (id and value)
    // Pair < Key , Value >
    protected ArrayList<Pair<Short, Short>> newDetectedIds = new ArrayList<Pair<Short, Short>>();
    protected ArrayList<Short> toRemoveFromDetectedIds = new ArrayList<Short>();


    public void init(){

    }

    public void addBlockWithId(short val, short id){
        newDetectedIds.add(new Pair<Short, Short>(id,val));
    }


    public void blockRemovedWithId(short id){
        //toRemoveFromDetectedIds.add(id);

        boolean inDetected = false;
        // TODO check if its not waiting to be added, add+remove = 0!
        for(int i =0; i<newDetectedIds.size();i++){
            Gdx.app.log(TAG," id "+id + " key: "+newDetectedIds.get(i).getKey());
            if(newDetectedIds.get(i).getKey() == id){
                newDetectedIds.remove(i);
                inDetected = true;
                break;
            }
        }
        if(!inDetected)
            toRemoveFromDetectedIds.add(id);

    }

    public void updateDetected(){


    }

    public ArrayList getToRemove(){
        // we return the array because until the update function we don't have to carry about this array
        return toRemoveFromDetectedIds;
    }

    public ArrayList getNewDetected(){
        return newDetectedIds;
    }

    public void resetDetectedAndRemoved(){
        resetDetectedIds();
        resetRemoveIds();

    }


    private void resetDetectedIds(){
        newDetectedIds.clear();
    }
    private void resetRemoveIds(){
        toRemoveFromDetectedIds.clear();
    }










}
