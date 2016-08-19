package ceta.game.util;

import ceta.game.game.objects.VirtualBlock;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 8/11/16.
 */
public class VirtualBlocksManager extends AbstractBlocksManager {
    Stage stage;

    public VirtualBlocksManager(Stage stage){
        this.stage = stage;
    }


    public void init(){
        initBlocksL1();
    }

    private void initBlocksL1(){
        for(short i=-5;i<=5;i++){
            VirtualBlock virtualBlock = new VirtualBlock(i,this);
            virtualBlock.setPosition(0+Constants.BASE*i,-Constants.BASE*8);
            stage.addActor(virtualBlock);
        }
    }


}
