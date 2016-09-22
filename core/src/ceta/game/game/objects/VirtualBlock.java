package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.util.VirtualBlocksManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import static java.lang.Math.abs;
import static java.lang.Math.sin;

/**
 * Created by ewe on 8/2/16.
 */
public class VirtualBlock extends AbstractGameObject {
    public static final String TAG = VirtualBlock.class.getName();
    private short blockValue;
    public Vector2 home;
    private boolean wasDetected;
    public float[] vertices;


    private boolean wasMoved;
    private VirtualBlocksManager virtualBlocksManager;


    public VirtualBlock(short val, VirtualBlocksManager vbm){
        blockValue = val;
        this.virtualBlocksManager = vbm;
        init();
    }

    public void init(){
        regTex = Assets.instance.box.box;
        //horizontal
        //this.setSize(Constants.BASE*abs(blockValue),Constants.BASE);
        //vertical
        this.setSize(Constants.BASE, Constants.BASE*abs(blockValue));
        super.init();

        home = new Vector2();
        wasDetected = false;
        wasMoved = false;
        vertices = new float[]{
                0,0,
                bounds.width,0,
                bounds.width,bounds.height,
                0,bounds.height
        };

        setMyColor();



        this.setTouchable(Touchable.enabled);
        // to move around
        this.addListener(new ActorGestureListener() {

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                //TODO we should check if the piece is in stage limits or the controller should do this?

                float sin = (float)Math.sin(Math.toRadians(getRotation()));
                float cos = (float)Math.cos(Math.toRadians(getRotation()));

                float rotatedX = (deltaX) * cos - (deltaY) * sin;
                float rotatedY = (deltaX) * sin + (deltaY) * cos;
                setPosition(getX()+rotatedX,getY()+rotatedY);
                //the code below was working without rotation
                //setPosition(getX()+deltaX,getY()+deltaY);
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){

                Gdx.app.debug(TAG, "isTouched!!!");
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                wasMoved = true;
                Gdx.app.debug(TAG, "wasMoved!!! " + blockValue);
            }

        });
//        this.addListener(new ClickListener() {
//             @Override
//             public void touchDragged(InputEvent event, float x, float y, int pointer){
//                 //Gdx.app.log(TAG,"x: "+x+" y: "+y);
//                 setPosition(getX()+x - getWidth()/2, getY()+y - getHeight()/2);
//                 setColor(1,0,0,1);
//             }
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
//                setColor(1,1,0,1);
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
//                setColor(0,0,1,1);
//            }
//         });


        // this later on won't be necessary because the detection will be based on the position not on click
//        this.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//               // Gdx.app.log(TAG,event+" "+x+" "+y+" "+blockValue);
//                //virtualBlocksManager.logDetected(blockValue);
//                if(blockValue > 0)
//                    virtualBlocksManager.blockAdded(blockValue);
//                else
//                    virtualBlocksManager.blockRemoved(blockValue);
//
//            }
//
//
//        });

    }

    private void setMyColor(){
       // amarillo, rojo, verde, naranja y celeste

        switch (blockValue){
            case 1:
                setColor(Color.LIME);
                break;
            case 2:
                setColor(Color.RED);
                break;
            case 3:
                setColor(Color.GREEN);
                break;
            case 4:
                setColor(Color.ORANGE);
                break;
            case 5:
                setColor(Color.CYAN);
                break;
        }
    }



    public boolean getWasDetected(){
        return wasDetected;

    }

    public float[] getVertices(){
        //Gdx.app.log(TAG," we have vertices!!! ");
        return vertices;
    }

    public boolean getWasMoved(){
        return wasMoved;
    }

    public void resetWasMoved(){
        wasMoved = false;
        Gdx.app.debug(TAG, "was moved set to FALSEEE");
    }

    public void setWasDetected(boolean was){
        //Gdx.app.debug(TAG," was detected! "+blockValue);
        wasDetected = was;
    }

    public void goHome(){
        addAction(Actions.moveTo(home.x,home.y,1f));
    }

    public void goHomeAndRemove(){
        addAction(sequence(Actions.moveTo(home.x,home.y,0.5f),run(new Runnable() {
                                                                      public void run() {
                                                                          remove();
                                                                      }
                                                                  })));
    }

    public void rotate90(){
        if(blockValue>1)
            addAction(Actions.rotateTo(90,0.5f));
    }

    public short getBlockValue(){
        return blockValue;
    }

    public void setHome(float x, float y){
        home.x = x;
        home.y = y;
    }

//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {}

}
