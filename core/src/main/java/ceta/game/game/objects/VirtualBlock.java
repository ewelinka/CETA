package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    private int blockValue;
    private int blockId;
    public Vector2 home;
    private boolean wasDetected;
    private boolean isAtHome;
    public float[] vertices;
    private float rotLast = 0;
    private float myAlpha;
    private int pixelsPerUnit;
    private boolean disappearing;
    private boolean applyAlpha;

    private boolean wasMoved;


    public VirtualBlock(int val, int pixelsPerUnit, boolean applyAlpha){
        this.blockValue = val;
        this.pixelsPerUnit = pixelsPerUnit;
        this.applyAlpha = applyAlpha;
        init();
    }



    public VirtualBlock(int val){
        this(val, Constants.BASE, false);
    }

    public void init(){

        this.myAlpha = 1;
        //horizontal
        //this.setSize(Constants.BASE*abs(blockValue),Constants.BASE);
        //vertical
        this.setSize(this.pixelsPerUnit*abs(this.blockValue),this.pixelsPerUnit);
        super.init();

        home = new Vector2();
        wasDetected = false;
        setWasMoved(false);
        vertices = new float[]{
                0,0,
                bounds.width,0,
                bounds.width,bounds.height,
                0,bounds.height
        };
        disappearing = false;
        setMyColorAndTexture();
        isAtHome = true;
        blockId = -1; // default value, we first set "real" id on addBlock event





        this.setTouchable(Touchable.enabled);
        // to move around
        this.addListener(new ActorGestureListener() {

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                //TODO we should check if the piece is in stage limits or the controller should do this?
                setPosition(getX()+deltaX,getY()+deltaY);
            }


            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                rotLast = 0;
                wasMoved = true;
                //Gdx.app.debug(TAG, "wasMoved!!! " + blockValue);
            }

        });

    }

    private void setMyColorAndTexture(){
       // amarillo, rojo, verde, naranja y celeste

        switch (blockValue){
            case 1:
                //setColor(Color.YELLOW);
                this.regTex = Assets.instance.box.box;
                break;
            case 2:
                //setColor(Color.GREEN);
                this.regTex = Assets.instance.box.box2;
                break;
            case 3:
                //setColor(Color.ORANGE);
                this.regTex = Assets.instance.box.box3;
                break;
            case 4:
                //setColor(Color.CYAN);
                this.regTex = Assets.instance.box.box4;
                break;
            case 5:
                //setColor(Color.PINK);
                this.regTex = Assets.instance.box.box5;
                break;
        }
        getColor().a = myAlpha;
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
        setWasMoved(false);
        //Gdx.app.debug(TAG, "was moved set to FALSEEE");
    }

    public void setWasMoved(boolean was){
        wasMoved = was;
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
        disappearing = true;
//        addAction(sequence(Actions.moveTo(home.x,home.y,0.5f),run(new Runnable() {
//                                                                      public void run() {
//                                                                          remove();
//                                                                      }
//                                                                  })));
        addAction(sequence(parallel(Actions.moveTo(home.x,home.y,1f),Actions.rotateTo(0,1f),Actions.alpha(0,1f)),run(new Runnable() {
            public void run() {
                remove();
            }
        })));
        setAtHome(true);
    }

    public void disappearAndRemove(){
        disappearing = true;
        addAction(sequence(parallel(Actions.alpha(0,1f)),run(new Runnable() {
            public void run() {
                remove();
            }
        })));
    }

    public void rotate90(){
        if(blockValue>1)
            addAction(Actions.rotateTo(90,0.5f));
    }

    public int getBlockValue(){
        return blockValue;
    }

    public int getBlockId(){ return blockId; }

    public void setBlockId(int id){ blockId = id;}

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
    public void setMyAlpha(float newAlpha){ myAlpha = newAlpha;}
    public Vector2 getCenterVector(){return new Vector2(getX()+getWidth()/2,getY()+getHeight()/2);}

//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {}

    @Override
    public void act(float delta){

        super.act(delta);
        if(!disappearing && applyAlpha) {
            getColor().a = 1;
            //batch.setProjectionMatrix(camera.combined);
            // batch.draw(regTex,this.getX(),this.getY());
            float diff = Constants.CV_DETECTION_EDGE_TABLET / 2 - (getX() + getWidth() / 2);
            float a = 1;
            //Gdx.app.log(TAG," alpha getX "+getX());
            // y
            if (diff < Constants.MARGIN_FADE) {
                a = map(diff, 0, Constants.MARGIN_FADE, 0.4f, 1);
              //  Gdx.app.log(TAG, " alpha " + a + " diff " + diff + " get x " + getX() + getWidth() / 2);
                getColor().a = a;
            }

            if ((Constants.CV_DETECTION_EDGE_TABLET - diff) < Constants.MARGIN_FADE) {
                diff = Constants.CV_DETECTION_EDGE_TABLET - diff;
                a = map(diff, 0, Constants.MARGIN_FADE, 0.4f, 1);
            //    Gdx.app.log(TAG, " alpha " + a + " diff " + diff + " get x " + getX() + getWidth() / 2);
                //Actions.alpha(a*parentAlpha);
                getColor().a = a;

            }

            //float yAdjusted = getY()+getHeight()/2 + 332;
            diff = Constants.CV_DETECTION_EDGE_TABLET / 2 - (getY() + getHeight() / 2 + 332); // TODO hardcoded!!

            if (diff < Constants.MARGIN_FADE) {
                a = map(diff, 0, Constants.MARGIN_FADE, 0.4f, 1);
             //   Gdx.app.log(TAG, " alpha " + a + " diff " + diff + " get y " + (getY() + getHeight() / 2 + 332));
                if (a < getColor().a)
                    getColor().a = a;
            }

            if ((Constants.CV_DETECTION_EDGE_TABLET - diff) < Constants.MARGIN_FADE) {
                diff = Constants.CV_DETECTION_EDGE_TABLET - diff;
                a = map(diff, 0, Constants.MARGIN_FADE, 0.4f, 1);
            //    Gdx.app.log(TAG, " alpha " + a + " diff " + diff + " get y " + (getY() + getHeight() / 2 + 332));
                //Actions.alpha(a*parentAlpha);
                if (a < getColor().a)
                    getColor().a = a;

            }
        }
    }


    private float map(float x, float in_min, float in_max, float out_min, float out_max)
    {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

}
