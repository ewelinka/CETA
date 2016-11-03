package ceta.game.managers;

import ceta.game.game.objects.VirtualBlock;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Stage;


import java.util.ArrayList;

/**
 * Created by ewe on 8/11/16.
 */
public class VirtualBlocksManager extends AbstractBlocksManager {
    public static final String TAG = VirtualBlocksManager.class.getName();
    Stage stage;
    short linesRange;
    short nowId;
    short margin;
    Polygon polygon;
    protected ArrayList<VirtualBlock> virtualBlocksOnStage;

    public VirtualBlocksManager(Stage stage){
        this.stage = stage;
    }

    public void init(){
        //TODO ojo que este hardcoded no es lindo
        linesRange = 6 * Constants.BASE;
        margin = 20;
        nowId = 0;
        polygon = new Polygon();
        virtualBlocksOnStage = new ArrayList<VirtualBlock>();
        resetDetectedAndRemoved();
        // polygon will be set for checks
        polygon = new Polygon();
        initBlocks();
    }

    private void initBlocks(){
        // we initialise first 5 blocks (numbers 1-5)
        for(short i=1;i<=5;i++){
            addVirtualBlockInEmptySpace(i);
        }
    }

    @Override
    public void updateDetected() {
        // first - clean!
//        if (!GamePreferences.instance.actionSubmit) {
//            resetDetectedAndRemoved(); //reset just if there is no action submit
//        }
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
            if (vBlock.getWasMoved()){

                // if it was detected before and is in detection zone we do nothing
                // if it was detected before and is out of detection zone we make it disappear and reset
                if(vBlock.getWasDetected()){
                    if( polygon.getTransformedVertices()[1] < Constants.DETECTION_LIMIT){
                        // TODO here we should check for smallest Y, not first vertex
                        // was detected but now gone
                       // blockRemoved(vBlock.getBlockValue());
                        blockRemovedWithIdAndValue(vBlock.getBlockId(),vBlock.getBlockValue());
                        //blockRemovedById(vBlock.getBlockId());
                        removeFromStageByIndex(i); // we remove it from detection zone
                    }
                }else{
                    if( polygon.getTransformedVertices()[1] > Constants.DETECTION_LIMIT){
                        // TODO here we should check for smallest Y, not first vertex
                        // new detected!
                        vBlock.setWasDetected(true);
                       // addBlock(vBlock.getBlockValue());
                        addBlockWithId(vBlock.getBlockValue(),vBlock.getBlockId());
                        // new virtual block in empty space
                        addVirtualBlockInEmptySpace(vBlock.getBlockValue());

                    }
                    else{
                        vBlock.goHome();
                    }
                }
                // we should check just one time so we set moved to false
                vBlock.resetWasMoved();
            }
        }

    }

    protected void addVirtualBlockInEmptySpace(short val){
        //Gdx.app.log(TAG, "we creat new vistual block of valua "+val);
        // TODO create or take from pool!!
        VirtualBlock virtualBlock = new VirtualBlock(val);
        // this works for vertical blocks
        virtualBlock.setPosition(-260 + 2*Constants.BASE*val ,-Constants.BASE*12);

        stage.addActor(virtualBlock);
        // set home so that we can come back
        virtualBlock.setHome(virtualBlock.getX(),virtualBlock.getY());

        virtualBlock.setBlockId(nowId);
        nowId+=1;

        virtualBlocksOnStage.add(virtualBlock);

    }

    protected void checkMargins(VirtualBlock vBlock){
        //check up
        if (polygon.getTransformedVertices()[5] + margin > 0){
            //if (virtualBlocksOnStage.get(i).getY() + virtualBlocksOnStage.get(i).getHeight() + margin > 0){
            // we have to adjust Y
            vBlock.setY(vBlock.getY() - (polygon.getTransformedVertices()[5] + margin));

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
                virtualBlocksOnStage.get(i).goHomeAndRemove();
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



}
