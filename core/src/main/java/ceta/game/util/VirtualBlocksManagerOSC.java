package ceta.game.util;

import ceta.game.game.objects.ArmPiece;
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


    public void oscAdd(float blockToAddVal, float px, float py, float rot) {

        int s =virtualBlocksOnStage.size();
        Gdx.app.log(TAG, "enter oscAdd "+ blockToAddVal +" "+px+" "+py+" "+rot+ " "+s);

        for (int i = 0; i < s; i++) {
            vBlock = virtualBlocksOnStage.get(i);

            if((vBlock.getBlockValue() == blockToAddVal) && vBlock.isAtHome()){
                Gdx.app.log(TAG, "add block of value: "+ blockToAddVal +" that was at home");
                vBlock.setWasDetected(true);
                vBlock.setAtHome(false);
                vBlock.addAction(parallel(
                        Actions.moveTo(px,py,1f),
                        Actions.rotateTo(rot,1f)
                ));

                //
                addBlock(vBlock.getBlockValue());
                // new virtual block in empty space
                addVirtualBlock(vBlock.getBlockValue());
            }
        }
    }

    public void oscRemove(float blockToRemoveVal) {
        for (int i = 0; i < virtualBlocksOnStage.size(); i++) {
            vBlock = virtualBlocksOnStage.get(i);
            if((vBlock.getBlockValue() == blockToRemoveVal) && !vBlock.isAtHome()){
                blockRemoved(vBlock.getBlockValue());
                removeVirtualBlock(i);
            }
        }

    }
}
