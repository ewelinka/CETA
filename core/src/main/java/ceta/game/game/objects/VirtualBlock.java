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
    private boolean isAtHome;
    public float[] vertices;
    //private float previousRotation =0;
    private float baseRotation =0;
    private float temporalRotation =0;


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
        isAtHome = true;



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
//
            @Override
            public void pinch(InputEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)  {
                //Actor actor = event.getListenerActor();

                Vector2 a = initialPointer2.sub(initialPointer1);
                Vector2 b = pointer2.sub(pointer1);
                a = a.nor();
                b = b.nor();
                float deltaRot = (float)(Math.atan2(b.y,b.x) - Math.atan2(a.y,a.x));
                float deltaRotDeg = (float)(((deltaRot*180)/Math.PI + 360) % 360);
                temporalRotation = deltaRotDeg;

                if(deltaRotDeg>0){
                    setRotation((baseRotation + deltaRotDeg)%360);
                    //Gdx.app.log(TAG,"--- "+deltaRot+" "+getRotation());
                }
            }

            @Override
            public void touchDown(InputEvent event, float x, float y, int pointer, int button){
                baseRotation = getRotation();
                Gdx.app.debug(TAG, "isTouched!!!");
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                baseRotation = (baseRotation + temporalRotation)%360;
                wasMoved = true;
                Gdx.app.debug(TAG, "wasMoved!!! " + blockValue);
            }

        });

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
        setAtHome(true);
    }

    public void goHomeAndRemove(){
//        addAction(sequence(Actions.moveTo(home.x,home.y,0.5f),run(new Runnable() {
//                                                                      public void run() {
//                                                                          remove();
//                                                                      }
//                                                                  })));
        addAction(sequence(parallel(Actions.moveTo(home.x,home.y,1f),Actions.rotateTo(0,1f)),run(new Runnable() {
            public void run() {
                remove();
            }
        })));
        setAtHome(true);
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

    public void setAtHome(boolean isAt){
        isAtHome = isAt;
    }
    public boolean isAtHome(){
        return isAtHome;
    }

//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {}

}
