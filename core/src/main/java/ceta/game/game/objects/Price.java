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
    private short currentNumber; // current position between 1 and 10

    private boolean isDynamic;
    private int maxReturn;
    private int returnCounter;
    private boolean isMovingVertical;

    //TODO shouldn't be fixed val!!!
    private float myStartX, myStartX_verticalNL;
    private short rotationVelocity;


    public Price(short vel, short startNr, int priceReturn) {
        this(true, vel, startNr, priceReturn); // horizontal
    }


    public Price(boolean isMovingVertical, short vel, short startNr, int priceReturn) {
        this.isMovingVertical = isMovingVertical;
        init();
        localInit(vel,startNr,priceReturn);


    }


    public void init () {
        regTex = Assets.instance.toCollect.screw;
        this.setSize(Constants.BASE,Constants.BASE);
        super.init();

    }

    private void localInit(short vel, short start, int priceReturn){
        myStartX = -200; // change!!
        myStartX_verticalNL = -280;
        rotationVelocity = 30;

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
            rotation+=(deltaTime*rotationVelocity);
            setRotation(rotation);
            if (isMovingVertical) // vertical falling = horizontal number line
                updateVerticalFalling(deltaTime);
            else
                updateHorizontalFalling(deltaTime);
        }

    }

    private void updateHorizontalFalling(float deltaTime){
        if(this.getX() < -Constants.VIEWPORT_WIDTH/2) {
            returnCounter -= 1;
            if (returnCounter < 0) {
                setPositionStartRight(); // new position!
                returnCounter = maxReturn;

            } else
                this.setPosition(Constants.VIEWPORT_WIDTH/2, getY()); // come back in the same place
        }
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

    private void setInitialPosition() {
        if(isMovingVertical)
            setInitialPositionMovingVertical();
        else
            setInitialPositionMovingHorizontal();
    }

    private void setInitialPositionMovingVertical(){
        if(isDynamic) {
            setNewPositionMV(myStartX, Constants.VIEWPORT_HEIGHT / 2 );
        }
        else{
            setNewPositionMV(myStartX, Constants.BASE);
        }
    }

    private void setInitialPositionMovingHorizontal(){
        if(isDynamic) {
            setNewPositionMH(Constants.VIEWPORT_WIDTH/2  );
        }
        else{
            setNewPositionMH(myStartX_verticalNL);
        }
    }



    public void setNewPositionMV(float startX, float startY){
        currentNumber = (short)(MathUtils.random(1,10));
        // adjust the position to range number (currentNumber-startNumber)
        setPosition( startX + (currentNumber)*Constants.BASE - getWidth()/2, startY );
    }

    public void setNewPositionMH(float startX){
        currentNumber = (short)(MathUtils.random(1,10));
        // adjust the position to range number (currentNumber-startNumber)
        setPosition( startX, (currentNumber)*Constants.BASE - getHeight()/2);
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

    public void moveToNewPositionVertical(float startX, short startY){
        short newPosition = (short)MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = (short)MathUtils.random(1,10);
        }
        currentNumber = newPosition;

        addAction(Actions.moveTo(startX, startY + currentNumber*Constants.BASE - getHeight()/2,0.6f));
        //setPosition( );
    }

    public void wasCollected(){
        if (isMovingVertical)
            wasCollectedHorizontalNumberLine();
        else
            wasCollectedVerticalNumberLine();

    }

    public void wasCollectedHorizontalNumberLine(){
        if (isDynamic)
            setPositionStartAbove(myStartX);
        else
            moveToNewPosition(myStartX,Constants.BASE );

    }

    public void wasCollectedVerticalNumberLine(){
        if (isDynamic)
            setPositionStartRight();
        else
            moveToNewPositionVertical(myStartX_verticalNL,(short)0);

    }

    private void setPositionStartAbove(float startX){
        returnCounter = maxReturn; // new position, new counter!

        short newPosition = (short)MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = (short)MathUtils.random(1,10);
        }
        currentNumber = newPosition;

        setPosition(startX + currentNumber*Constants.BASE - getWidth()/2,(short)(Constants.VIEWPORT_HEIGHT/2-getHeight()));

    }

    private void setPositionStartRight(){
        returnCounter = maxReturn; // new position, new counter!

        short newPosition = (short)MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = (short)MathUtils.random(1,10);
        }
        currentNumber = newPosition;

        setPosition(Constants.VIEWPORT_WIDTH/2,newPosition*Constants.BASE-getHeight()/2);

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
