package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.util.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
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
        this.setSize(Constants.BASE*abs(blockValue),Constants.BASE);
        Gdx.app.log(TAG,this.getWidth()+" "+this.getHeight());
        super.init();

        this.setTouchable(Touchable.enabled);

        // to move around
//        this.addListener(new ActorGestureListener() {
//            @Override
//            public void fling(InputEvent event, float velocityX, float velocityY, int button) {
//                Gdx.app.log(TAG, new Float(velocityX).toString()+ " actor fling");
//            }
//
//            @Override
//            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
//                //Gdx.app.log(TAG, "actor pan, x "+x+" y "+y+ " detaX "+deltaX+" deltaY "+deltaY);
//                //TODO we should check if the piece is in stage limits
//                //update position
//                setPosition(getX()+deltaX,getY()+deltaY);
//            }
//
//        });

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
