package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.util.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static java.lang.Math.abs;

/**
 * Created by ewe on 8/2/16.
 */
public class VirtualBlock extends AbstractGameObject {
    public static final String TAG = VirtualBlock.class.getName();
    private short blockValue;
    private VirtualBlocksManager virtualBlocksManager;


    public VirtualBlock(short val, VirtualBlocksManager vbm){
        blockValue = val;
        this.virtualBlocksManager = vbm;
        init();
    }

    public void init(){
        regTex = Assets.instance.box.box;
        this.setSize(Constants.BASE,Constants.BASE*abs(blockValue));
        Gdx.app.log(TAG,this.getWidth()+" "+this.getHeight());
        super.init();

        this.setTouchable(Touchable.enabled);

        // this later on won't be necessary because the detection will be based on the position not on click
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // Gdx.app.log(TAG,event+" "+x+" "+y+" "+blockValue);
                //virtualBlocksManager.logDetected(blockValue);
                if(blockValue > 0)
                    virtualBlocksManager.blockAdded(blockValue);
                else
                    virtualBlocksManager.blockRemoved(blockValue);
            }


        });

    }

//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {}

}
