package ceta.game.util;

import ceta.game.game.objects.ArmPiece;
import ceta.game.game.objects.VirtualBlock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;

import java.util.Date;
import java.util.List;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;

/**
 * Created by ewe on 10/2/16.
 */
public class VirtualBlocksManagerOSC extends VirtualBlocksManager  {

    public VirtualBlocksManagerOSC(Stage stage) {

        super(stage);
    }

    @Override
    public void updateDetected() {
        // detection via OSC
    }


    public void oscAdd(float blockToAddVal, int id, float px, float py, float rot) {

       // int s =virtualBlocksOnStage.size();
        Gdx.app.log(TAG, "enter oscAdd "+ blockToAddVal +" "+px+" "+py+" "+rot+ " "+id);

        for (int i = 0; i < virtualBlocksOnStage.size(); i++) {
            vBlock = virtualBlocksOnStage.get(i);

            if((vBlock.getBlockValue() == blockToAddVal) && vBlock.isAtHome()){ // pieces in "stand-by" are atHome
                Gdx.app.log(TAG, "add block of value: "+ blockToAddVal +" that was at home" + " with id "+id);
                vBlock.setWasDetected(true);
                vBlock.setBlockId(id);
                vBlock.setAtHome(false);
                vBlock.addAction(parallel(
                        Actions.moveTo(px,py,1f),
                        Actions.rotateTo(rot,1f),
                        Actions.alpha(1,1f)
                ));

                //
                addBlock(vBlock.getBlockValue());
                // new virtual block in empty space
                addVirtualBlock(vBlock.getBlockValue());
                // we found block to move so we break for loop
                break;
            }
        }
    }

    public void oscRemove(int blockToRemoveId) {
        for (int i = 0; i < virtualBlocksOnStage.size(); i++) {
            vBlock = virtualBlocksOnStage.get(i);
            if(vBlock.getBlockId() == blockToRemoveId){ // we remove indicated id
                blockRemoved(vBlock.getBlockValue());
                removeVirtualBlock(i);
            }
        }

    }

    public void oscUpdateBlock(int id, float px, float py, float rot){
        for (int i = 0; i < virtualBlocksOnStage.size(); i++) {
            vBlock = virtualBlocksOnStage.get(i);
            if(vBlock.getBlockId() == id){ // we update indicated id
                vBlock.addAction(parallel(
                        Actions.moveTo(px,py,1f),
                        Actions.rotateTo(rot,1f)
                ));
            }
        }

    }

}
