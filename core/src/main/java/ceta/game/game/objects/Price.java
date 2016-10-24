package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by ewe on 8/12/16.
 */
public class Price extends AbstractGameObject {
    public static final String TAG = Price.class.getName();
    private short velocity;
    // number line limits
    private short startNumber;
    private short endNumber;
    private short currentNumber;

    private boolean isDynamic;
    private int maxReturn;
    private int returnCounter;

    //TODO shouldn't be fixed val!!!
    private float myStartX;


    public Price(short vel, short startNr, int priceReturn) {
        this("horizontal", vel, startNr, priceReturn); // horizontal
    }


    public Price(String type, short vel, short startNr, int priceReturn) {
        init();
        localInit(type,vel,startNr,priceReturn);
    }


    public void init () {
        regTex = Assets.instance.toCollect.screw;
        this.setSize(Constants.BASE,Constants.BASE);
        super.init();

    }

    private void localInit(String type, short vel, short start, int priceReturn){
        myStartX = -200; // change!!

        if(type.equals("horizontal")){
            isMovingVertical = true;
        } else {
            isMovingVertical = false;
        }

        velocity = vel;
        startNumber = start;
        maxReturn = priceReturn;
        returnCounter = maxReturn;

        if(priceReturn>=0){
            isDynamic = true;
        } else{
            isDynamic =false;
        }

        setInitialPosition();
    }

    public void update(float deltaTime){
        if(isDynamic) {
            if (isMovingVertical) // vertical falling = horizontal number line
                updateVerticalFalling(deltaTime);
            else
                updateHorizontalFalling(deltaTime);
        }

    }

    private void updateHorizontalFalling(float deltaTime){
        if(this.getX() < -Constants.VIEWPORT_WIDTH/2)
            this.setPosition(Constants.VIEWPORT_WIDTH/2, getY());
        else
            this.setPosition(getX()+velocity*deltaTime, getY());
    }

    private void updateVerticalFalling(float deltaTime){
        // TODO perhaps a constant that defines where the ground is
        if(this.getY() < -this.getHeight()) { // when below number line
            returnCounter-=1;
            if(returnCounter<0){
                setPositionStartAbove(myStartX); // new position!
                returnCounter = maxReturn;

            } else
                this.setPosition(getX(), Constants.VIEWPORT_HEIGHT / 2); // come back in the same place
        }
        else
            this.setPosition(getX(),getY()+velocity*deltaTime);
    }

    public void setVelocity(short vel){
        velocity = vel;
    }

    public void setStartAndEnd(short s, short e){
        startNumber = s;
        endNumber = e;
    }

    public void setStart(short s){
        startNumber = s;
    }

    private void setInitialPosition(){
        if(isDynamic) {
            setNewPosition(myStartX, Constants.VIEWPORT_HEIGHT / 2 - getHeight());
        }
        else{
            setNewPosition(myStartX, Constants.BASE);
        }
    }

    public void setNewPosition(float startX, float startY){
        currentNumber = (short)(MathUtils.random(1,10));
        // adjust the position to range number (currentNumber-startNumber)
        setPosition( startX + (currentNumber)*Constants.BASE - getWidth()/2, startY );
    }


    public void moveToNewPosition(float startX, short startY){
        short newPosition = (short)MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = (short)MathUtils.random(1,10);
        }
        currentNumber = newPosition;

        addAction(Actions.moveTo(startX + currentNumber*Constants.BASE - getWidth()/2, startY,0.6f));
        //setPosition( );
    }

    public void wasCollected(){
        if (isDynamic)
            setPositionStartAbove(myStartX);
        else
            moveToNewPosition(myStartX,Constants.BASE );

    }

    public void setPositionStartAbove(float startX){
        returnCounter = maxReturn; // new position, new counter!

        short newPosition = (short)MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = (short)MathUtils.random(1,10);
        }
        currentNumber = newPosition;

        setPosition(startX + currentNumber*Constants.BASE - getWidth()/2,(short)(Constants.VIEWPORT_HEIGHT/2-getHeight()));

    }

    public short getDisplayNumber(){
        return (short)(currentNumber+startNumber);
    }

    public void setDynamicProps(boolean dynamic, int priceReturnNr){
        isDynamic = dynamic;
        maxReturn = priceReturnNr;
        returnCounter = priceReturnNr;

    }



    public short getCurrentNumber(){
        return currentNumber;
    }
}
