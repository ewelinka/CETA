package ceta.game.managers;

import ceta.game.game.objects.VirtualBlock;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.util.Pair;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;
import javafx.scene.layout.VBox;


import java.util.ArrayList;

/**
 * Created by ewe on 8/11/16.
 */
public class VirtualBlocksManager extends AbstractBlocksManager {
    public static final String TAG = VirtualBlocksManager.class.getName();
    Stage stage;
    int nowId;
    int margin;
    int xSpace, ySpace;
    Polygon polygon;

    //protected ArrayList<VirtualBlock> virtualBlocksOnStage;

    public VirtualBlocksManager(Stage stage){
        this.stage = stage;
    }

    public void init(){
        margin = 20;
        nowId = 0;
        polygon = new Polygon();
        virtualBlocksOnStage = new ArrayList<VirtualBlock>();
        resetDetectedAndRemoved();
        // polygon will be set for checks
        polygon = new Polygon();
        xSpace = 60;
        ySpace = 45;
        initBlocks();
        setWaitForFirstMove(true);
        noChangesSince = TimeUtils.millis();
        nowDetectedVals = new ArrayList<Integer>();
        nowDetectedBlocks = new ArrayList<VirtualBlock>();
    }

    protected void initBlocks(){
        // we initialise first 5 blocks (numbers 1-5)
        for(int i=1;i<=5;i++){
            addVirtualBlockInEmptySpace(i);
        }
    }

    @Override
    public void updateDetected() {
        nowDetectedVals.clear();
        nowDetectedBlocks.clear();
        // we check what changed
        for (int i = 0; i < virtualBlocksOnStage.size(); i++) {
            VirtualBlock vBlock = virtualBlocksOnStage.get(i);
            // set polygon for collision detection
            polygon.setVertices(vBlock.getVertices());
            polygon.setOrigin(vBlock.bounds.width/2, vBlock.bounds.height/2);
            polygon.setPosition(vBlock.getX(), vBlock.getY());
            polygon.setRotation(vBlock.getRotation());
            // check if we are above zero or below screen height
            // vBlock is already set
            checkMargins(vBlock);

            // if the kid is touching/moving we ignore;
            // we just act when the piece is left free [we set wasMoved=true on release]
            if (vBlock.getWasMoved()){ // was moved
                //Gdx.app.log(TAG, " virtual block moved!");
                resetNoChangesSince();

                // if it was detected before and is in detection zone we do nothing
                // if it was detected before and is out of detection zone we make it disappear and reset
                if(vBlock.getWasDetected()){
                    if( polygon.getTransformedVertices()[1] < Constants.DETECTION_LIMIT){
                        // here we should check for smallest Y, not first vertex, but no rotation case works (our case!)
                        setWaitForFirstMove(false);
                        vBlock.setWasDetected(false);
                        blockRemovedWithIdAndValue(vBlock.getBlockId(),vBlock.getBlockValue());
                        removeFromStageByIndex(i); // we remove it from detection zone
                    }
                }else{
                    if( polygon.getTransformedVertices()[1] > Constants.DETECTION_LIMIT){
                        setWaitForFirstMove(false);
                        vBlock.setWasDetected(true);
                       // addBlock(vBlock.getBlockValue());
                        addBlockTablet(vBlock.getBlockValue(),vBlock.getBlockId());
                        // new virtual block in empty space
                        addVirtualBlockInEmptySpace(vBlock.getBlockValue());

                    }
                    else{
                        vBlock.setWasDetected(false);
                        vBlock.goHome();
                    }
                }
                // we should check just one time so we set moved to false
                vBlock.resetWasMoved();
            }

            if (vBlock.getWasDetected()) {
               // Gdx.app.log(TAG, "DETE CTED "+ vBlock.getBlockValue());
                nowDetectedVals.add(vBlock.getBlockValue());
                nowDetectedBlocks.add(vBlock);
            }
            if(needAS()) {
                setWaitForFirstMove(false);

            }else{
                setWaitForFirstMove(true);
            }

        }

    }

    private void addBlockTablet(int blockToAddVal, int id){
        if(toRemoveFromDetectedValues.contains(blockToAddVal)){
            Gdx.app.log(TAG, "addBlockTablet but we had it in to remove val:" + blockToAddVal);
            removeFirstValue(blockToAddVal);
            removeFromIdsIdWithValue(blockToAddVal, id); //watch out! different id!!


        }else {
            Gdx.app.log(TAG, "addBlockTablet, setting block id to " + id + " and value: "+blockToAddVal);
            addBlockWithId(blockToAddVal, id); // add to manager! will be checked by brunos manager

        }




    }

    private void removeFromIdsIdWithValue(int val, int detectedId){
        for(int i =0;i<toRemoveFromDetectedIds.size();i++){
            int currentId = toRemoveFromDetectedIds.get(i); //important! we have to keep it to than
            if(getDetectedBlockValueById(currentId) == val){
                Gdx.app.log(TAG," remove value from toRemove "+val+ " id "+toRemoveFromDetectedIds.get(i));
                toRemoveFromDetectedIds.remove(i);
                changeId(currentId,detectedId);


                break; //!!! just one delete!!
            }
        }
    }

    private void changeId(int donorId, int receptorId){
        getBlockById(receptorId).setBlockId(donorId);

    }

    private void removeFirstValue(int val){
        for(int i =0;i<toRemoveFromDetectedValues.size();i++){
            if(toRemoveFromDetectedValues.get(i) == val){
                Gdx.app.log(TAG," remove value from toRemove VALS "+val);
                toRemoveFromDetectedValues.remove(i);
                break; //!!! just one delete!!
            }
        }
    }

    protected void addVirtualBlockInEmptySpace(int val){
        //Gdx.app.log(TAG, "we creat new vistual block of valua "+val);
        // TODO create or take from pool!!
        VirtualBlock virtualBlock = new VirtualBlock(val);
        setBlockWhereItBelongs(virtualBlock);
        stage.addActor(virtualBlock);
        // set home so that we can come back
        virtualBlock.setHome(virtualBlock.getX(),virtualBlock.getY());

        virtualBlock.setBlockId(nowId);
        nowId+=1;

        virtualBlocksOnStage.add(virtualBlock);

    }


    private void setBlockWhereItBelongs( VirtualBlock virtualBlock) {
        switch(virtualBlock.getBlockValue()){
            case 1:
                virtualBlock.setPosition(-Constants.VIEWPORT_WIDTH/2 + 60 +xSpace,
                        -Constants.VIEWPORT_HEIGHT / 2 + ySpace*1.5f + Constants.BASE);
                break;
            case 2:
                virtualBlock.setPosition(-Constants.VIEWPORT_WIDTH/2 + 60 + xSpace*2 + Constants.BASE , // 1
                        -Constants.VIEWPORT_HEIGHT / 2 + ySpace*1.5f + Constants.BASE);
                break;
            case 3:
                virtualBlock.setPosition(-Constants.VIEWPORT_WIDTH/2 + 60 + xSpace*3 + Constants.BASE*3 , // 1,2
                        -Constants.VIEWPORT_HEIGHT / 2 + ySpace*1.5f + Constants.BASE);
                break;
            case 4:
                virtualBlock.setPosition(-Constants.VIEWPORT_WIDTH/2 + 40+ xSpace,
                        -Constants.VIEWPORT_HEIGHT / 2 + ySpace);
                break;
            case 5:
                virtualBlock.setPosition(-Constants.VIEWPORT_WIDTH/2 + 40 + xSpace*2 + Constants.BASE*4,
                        -Constants.VIEWPORT_HEIGHT / 2 + ySpace);
                break;



        }
    }

    protected void checkMargins(VirtualBlock vBlock){
        //check up
        if (polygon.getTransformedVertices()[5] + margin > Constants.DETECTION_ZONE_END){
            //if (virtualBlocksOnStage.get(i).getY() + virtualBlocksOnStage.get(i).getHeight() + margin > 0){
            // we have to adjust Y
            vBlock.setY(Constants.DETECTION_ZONE_END - margin - vBlock.getHeight());

        }
        //check down
        else if (polygon.getTransformedVertices()[1] < -Constants.VIEWPORT_HEIGHT/2 + margin){
            vBlock.setY(vBlock.getY() +
                    (-polygon.getTransformedVertices()[1]-Constants.VIEWPORT_HEIGHT/2 + margin)
            );
        }

        // check right
        if(polygon.getTransformedVertices()[2] + margin > Constants.VIEWPORT_WIDTH/2){
            vBlock.setX( vBlock.getX() -
                    (polygon.getTransformedVertices()[2]+margin - Constants.VIEWPORT_WIDTH/2)

            );
        }
        // check left
        else if (polygon.getTransformedVertices()[6]  < -Constants.VIEWPORT_WIDTH/2 + margin){
            vBlock.setX(  vBlock.getX() +
                    (-polygon.getTransformedVertices()[6] - Constants.VIEWPORT_WIDTH/2 + margin)
            );
        }

    }

    protected void removeFromStageById(int whichId){
        // remove Actor
        Gdx.app.log(TAG,"we should remove id: "+whichId+" from stage");
        for(int i=0;i<virtualBlocksOnStage.size();i++){
           // Gdx.app.log(TAG,"we are checking block "+i+" id: "+virtualBlocksOnStage.get(i).getBlockId());
            if(virtualBlocksOnStage.get(i).getBlockId() == whichId && !virtualBlocksOnStage.get(i).isAtHome()){
                //remove actor
               // virtualBlocksOnStage.get(i).goHomeAndRemove();
                // remove from array
                virtualBlocksOnStage.remove(i);
                return;
            }
        }

    }

    protected void removeFromStageByIndex(int index){
        // remove Actor
        Gdx.app.log(TAG,"we should remove index: "+index);

        virtualBlocksOnStage.get(index).goHomeAndRemove();
    }

    public void adaptXposition(int limit){
        for(int i = 0; i< virtualBlocksOnStage.size();i++){
            VirtualBlock vb = virtualBlocksOnStage.get(i);
            if(vb.getWasDetected() && (vb.getX() < limit)){
                vb.addAction(Actions.moveTo(limit+2, vb.getY(),0.3f));
            }
        }
    }

    protected VirtualBlock getBlockByValue(int valueToFind){
        for(int i = 0; i< virtualBlocksOnStage.size();i++){
            VirtualBlock vb = virtualBlocksOnStage.get(i);
            if(vb.getBlockValue() == valueToFind){
                return vb;
            }
        }
        return new VirtualBlock(1);

    }

    protected VirtualBlock getBlockById(int idToFind){
        for(int i = 0; i< virtualBlocksOnStage.size();i++){
            VirtualBlock vb = virtualBlocksOnStage.get(i);
            if(vb.getBlockId() == idToFind){
                return vb;
            }
        }
        return new VirtualBlock(1);

    }

    protected int getDetectedBlockValueById(int idToFind){

        for(int i = 0; i< virtualBlocksOnStage.size();i++){
            VirtualBlock vb = virtualBlocksOnStage.get(i);
            Gdx.app.log(TAG,"to find: "+idToFind+" id "+vb.getBlockId()+" val "+vb.getBlockValue()+" detected "+vb.getWasDetected()+" at home "+vb.isAtHome());
            if(vb.getBlockId() == idToFind){
                return vb.getBlockValue();
            }
        }
        return 1;

    }

    private boolean needAS(){
       // Gdx.app.log(TAG," toRemoveFromDetectedIds size "+toRemoveFromDetectedIds.size()+" newDetectedPairs "+newDetectedPairs.size());
        return (toRemoveFromDetectedIds.size()>0 || newDetectedPairs.size()>0);
    }







}
