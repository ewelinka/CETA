package ceta.game.util;

import ceta.game.game.objects.VirtualBlock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import javafx.util.Pair;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

/**
 * Created by ewe on 10/2/16.
 */
public class VirtualBlocksManagerOSC extends VirtualBlocksManager  {
    public static final String TAG = VirtualBlocksManagerOSC.class.getName();

    private ArrayList<Pair<Short, Short>>  newDetectedOSC = new ArrayList<Pair<Short, Short>>();
    protected ArrayList<Short> toRemoveOSC = new ArrayList<Short>();

    public VirtualBlocksManagerOSC(Stage stage) {

        super(stage);
    }

    @Override
    public void updateDetected() {
        // detection via OSC
        //resetDetectedAndRemoved();
    }


    public void oscAdd(float blockToAddVal, int id, float px, float py, float rot) {

       // int s =virtualBlocksOnStage.size();
        //Gdx.app.log(TAG, "enter oscAdd "+ blockToAddVal +" "+px+" "+py+" "+rot+ " "+id);

        for (int i = 0; i < virtualBlocksOnStage.size(); i++) {
            VirtualBlock vBlock = virtualBlocksOnStage.get(i);

            if((vBlock.getBlockValue() == blockToAddVal) && vBlock.isAtHome()){ // pieces in "stand-by" are atHome
               // Gdx.app.log(TAG, "add block of value: "+ blockToAddVal +" that was at home with id "+id);
                vBlock.setWasDetected(true);
                vBlock.setBlockId((short)id);
                Gdx.app.log(TAG,"setting block id to "+id);
                vBlock.setAtHome(false);
                vBlock.addAction(parallel(
                        Actions.moveTo(px,py,1f),
                        Actions.rotateTo(rot,1f),
                        Actions.alpha(1,1f)
                ));

                //
               // addBlock(vBlock.getBlockValue());
                addBlockWithId(vBlock.getBlockValue(),vBlock.getBlockId());
                // new virtual block in empty space
                addVirtualBlockInEmptySpace(vBlock.getBlockValue());
                // we found block to move so we break for loop
                break;
            }
        }
    }

    public void oscRemove(int blockToRemoveId) {
        blockRemovedWithId((short)blockToRemoveId); //we report to manager to update the counters that will be used to update the arms
        removeFromStageById(blockToRemoveId); // remove from stage
    }

    public void oscUpdateBlock(int id, float px, float py, float rot){
        for (int i = 0; i < virtualBlocksOnStage.size(); i++) {
            VirtualBlock vBlock = virtualBlocksOnStage.get(i);
            if(vBlock.getBlockId() == id){ // we update indicated id
                vBlock.addAction(parallel(
                        Actions.moveTo(px,py,1f),
                        Actions.rotateTo(rot,1f)
                ));
            }
        }

    }

    @Override
    public void addBlockWithId(short val, short id){
        newDetectedOSC.add(new Pair<Short, Short>(id,val));
    }

    @Override
    public void blockRemovedWithId(short id){

        boolean inDetected = false;
        // TODO check if its not waiting to be added, add+remove = 0!
        for(int i =0; i<newDetectedOSC.size();i++){
            Gdx.app.log(TAG," id "+id + " key: "+newDetectedOSC.get(i).getKey());
            if(newDetectedOSC.get(i).getKey() == id){
                newDetectedOSC.remove(i);
                inDetected = true;
                break;
            }
        }
        if(!inDetected)
            toRemoveOSC.add(id);


      //  toRemoveOSC.add(id);

    }

    @Override
    public ArrayList getToRemove(){
        // we cops osc-array to array to return and clean osc-array to collect the data
        toRemoveFromDetectedIds =  new ArrayList(toRemoveOSC);
        toRemoveOSC.clear();
        return toRemoveFromDetectedIds;
    }

    @Override
    public ArrayList getNewDetected(){
        newDetectedIds = new ArrayList(newDetectedOSC);
        newDetectedOSC.clear();
        return new ArrayList(newDetectedIds);
    }


}
