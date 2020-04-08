package ceta.game.managers;

import ceta.game.game.objects.VirtualBlock;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

/**
 * Created by ewe on 8/11/16.
 */
public abstract class AbstractBlocksManager {
    public static final String TAG = AbstractBlocksManager.class.getName();
   // private int [] detected_blocks = {0,0,0,0,0};
    // a pair of ints (id and value)
    // Pair < Key , Value >
    protected ArrayList<Pair> newDetectedPairs = new ArrayList<Pair>(); //id and value
    protected ArrayList<Integer> toRemoveFromDetectedIds = new ArrayList<Integer>();
    protected ArrayList<Integer> toRemoveFromDetectedValues = new ArrayList<Integer>();
    protected boolean waitForFirstMove;
    protected long noChangesSince;
    protected ArrayList<VirtualBlock> virtualBlocksOnStage;
    ArrayList<Integer> nowDetectedVals;
    ArrayList<VirtualBlock> nowDetectedBlocks;


    public abstract void init();
    public abstract void updateDetected();


    public void addBlockWithId(int val, int id){
        Gdx.app.log(TAG, "adding block with id "+id+" and val "+val);
        newDetectedPairs.add(new Pair(id,val));
    }

    public void blockRemovedWithIdAndValue(int id, int value){
        //toRemoveFromDetectedIds.add(id);

        boolean inDetected = false;
        // TODO check if its not waiting to be added, add+remove = 0!
        for(int i = 0; i< newDetectedPairs.size(); i++){
            Gdx.app.log(TAG," id "+id + " key: "+ newDetectedPairs.get(i).getKey());
            if(newDetectedPairs.get(i).getKey() == id){
                newDetectedPairs.remove(i);
                inDetected = true;
                break;
            }
        }
        if(!inDetected) {
            toRemoveFromDetectedIds.add(id);
            toRemoveFromDetectedValues.add(value);
        }
    }



    public ArrayList<Integer> getToRemove(){
        // we return the array because until the update function we don't have to carry about this array
        return toRemoveFromDetectedIds;
    }

    public ArrayList<Integer> getToRemoveValues(){
        return toRemoveFromDetectedValues;
    }

    public ArrayList<Pair> getNewDetected(){
        return newDetectedPairs;
    }

    public void resetDetectedAndRemoved(){
        resetDetectedIds();
        resetRemoveIds();

    }


    private void resetDetectedIds(){
        newDetectedPairs.clear();
    }
    private void resetRemoveIds(){
        toRemoveFromDetectedIds.clear();
        toRemoveFromDetectedValues.clear();
    }

    public boolean isWaitForFirstMove(){
        return waitForFirstMove;
    }

    public void setWaitForFirstMove(boolean shouldWait){
        waitForFirstMove = shouldWait;

    }

    public long getTimeWithoutChange(){
      //  Gdx.app.log(TAG," no changes since "+noChangesSince+" in millis "+TimeUtils.timeSinceMillis(noChangesSince));
        return TimeUtils.timeSinceMillis(noChangesSince);
    }

    public void resetNoChangesSince(){
        noChangesSince = TimeUtils.millis(); //new change!
       // Gdx.app.log(TAG, "no changes since now! "+noChangesSince);
    }

    public synchronized ArrayList<Integer> getNowDetectedVals(){
        return nowDetectedVals;
    }

    public synchronized ArrayList<VirtualBlock> getNowDetectedBlocks(){
        return nowDetectedBlocks;
    }


    public static int getValueById(int id){
        switch(id){
            case 31:
            case 61:
            case 103:
            case 179:
            case 227:
            case 271:
            case 283:
            case 355:
            case 391:
            case 453:
                return 1;
            case 93:
            case 117:
            case 185:
            case 203:
            case 793:
                return 2;
            case 563:
            case 651:
            case 361:
            case 309:
                return 3;
            case 171:
            case 555:
            case 421:
                return 4;
            case 1173:
            case 1189:
            case 677:
                return 5;
            default:
                return 0;
        }
    }







}
