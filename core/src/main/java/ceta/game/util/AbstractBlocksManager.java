package ceta.game.util;

import com.badlogic.gdx.Gdx;

import java.util.Arrays;

/**
 * Created by ewe on 8/11/16.
 */
public abstract class AbstractBlocksManager {
    public static final String TAG = AbstractBlocksManager.class.getName();
    private short [] detected_blocks = {0,0,0,0,0};



    public short[] getDetectedBlocks() {
        return Arrays.copyOf(detected_blocks,detected_blocks.length);
    }

    public void logDetected(short val){
        Gdx.app.log(TAG," "+val);
    }

    public void blockAdded(short val){
        // if we detected new block of value 4, we add up 1 in index 3(!)
        Gdx.app.log(TAG,"added: "+val+" at position "+(val-1));
        detected_blocks[val-1]+=1;
    }

    public void addBlock(short val){
        // if we detected new block of value 4, we add up 1 in index 3(!)
        //Gdx.app.log(TAG,"detected: "+val+" at position "+(val-1));
        detected_blocks[val-1]+=1;
    }

    public void blockRemoved(short val){
        Gdx.app.log(TAG,"removed: "+val+" at position "+(Math.abs(val)-1));
        detected_blocks[Math.abs(val)-1]-=1;
    }

    public void updateDetected(){
        // ask tablet camera what it saw

    }

    public void resetDetected(){
        for (int i = 0;i<detected_blocks.length;i++)
            detected_blocks[i] = 0;
    }

}
