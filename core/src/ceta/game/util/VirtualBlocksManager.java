package ceta.game.util;

import ceta.game.game.objects.VirtualBlock;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 8/11/16.
 */
public class VirtualBlocksManager extends AbstractBlocksManager {
    public static final String TAG = VirtualBlocksManager.class.getName();
    Stage stage;
    short linesRange;

    public VirtualBlocksManager(Stage stage){
        this.stage = stage;
    }


    public void init(){
        //TODO ojo que este hardcoded no es lindo
        linesRange = (short)(Constants.VIEWPORT_HEIGHT/Constants.BASE/4)*Constants.BASE;
        initBlocksL1();
        Gdx.app.log(TAG, ""+linesRange*2);
    }

    private void initBlocksL1(){
        for(short i=1;i<=5;i++){
            VirtualBlock virtualBlock = new VirtualBlock(i,this);
            // this works for vertical blocks
            //virtualBlock.setPosition(-300 + 2*Constants.BASE*i ,-Constants.BASE*10);

            virtualBlock.setPosition(
                    -virtualBlock.getWidth()/2,
                    -linesRange - Constants.BASE*i - Constants.BASE/4*i
            );
            stage.addActor(virtualBlock);
        }
    }


}
