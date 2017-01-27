package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.ParallelAction;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 8/12/16.
 */
public class Price extends AbstractGameObject {
    public static final String TAG = Price.class.getName();
    private int velocity;
    private float priceScale;
    private int multiplicationFactorForScale;
    // number line limits
    private int startNumber;
    private int endNumber;
    private int maxShift; // difference between start and end; start = 1, end = 6, shift = 6-1 =>5
    private int currentNumber; // current position between 1 and 10
    // price dynamics
    private boolean isDynamic;
    private int maxReturn;
    private int returnCounter;
    private boolean isMovingVertical;
    //price position in the world
    private float myStartX;
    private float myStartY;
    private int rotationVelocity;
    private int verticalMiddleXPrice = Constants.VERTICAL_MIDDLE_X + Constants.PRICE_X_OFFSET;
    // price type associated to the number 1 to 4
    private int priceTypeNr;
    private boolean isLast;



    public Price(int vel, int startNr, int endNr, int priceReturn, int priceType){
        // by default horizontal
        this(true,vel,startNr, endNr, priceReturn, priceType);}

    public Price(int vel, int startNr, int endNr, int priceReturn) {
        // by default horizontal, and by default first price type
        this(true, vel, startNr, endNr, priceReturn, 1);
    }


    public Price(boolean isMovingVertical, int vel, int startNr, int endNr, int priceReturn, int priceType) {
        this.isMovingVertical = isMovingVertical;
        this.priceTypeNr = priceType;
        init();
        localInit(vel,startNr, endNr,priceReturn);


    }


    public void init () {
        setTexture();
        super.init();

    }

    private void setTexture(){
        Gdx.app.log(TAG,"set price texture -> texture nr "+priceTypeNr);

        switch(priceTypeNr){
            case 1:
                regTex = Assets.instance.toCollect.price1;
                break;
            case 2:
                regTex = Assets.instance.toCollect.price2;
                break;
            case 3:
                regTex = Assets.instance.toCollect.price3;
                break;
            case 4:
                regTex = Assets.instance.toCollect.price4;
                break;
        }

        this.setSize(Constants.BASE,Constants.BASE);
    }

    private void localInit(int vel, int start, int end, int priceReturn){
        myStartX = Constants.HORIZONTAL_ZERO_X;
        myStartY = Constants.DETECTION_ZONE_END;
        rotationVelocity = 30;
        priceScale = 1;
        multiplicationFactorForScale = 1;

        velocity = vel;
        startNumber = start;
        endNumber = end;
        maxShift = endNumber - startNumber;
        maxReturn = priceReturn;
        returnCounter = maxReturn;

        if(priceReturn>=0){
            isDynamic = true;
        } else{
            isDynamic =false;
        }

        setInitialPosition();
        isLast = false;
    }

    public void update(float deltaTime){
        //Gdx.app.log(TAG, " update price!! has actions: "+ !hasActions()+ " is last "+isLast+ " now scale "+priceScale + " get scale x" +getScaleX());
        if(!hasActions() && !isLast) {
            adjustScale(deltaTime);
            if (isDynamic) {
                rotation += (deltaTime * rotationVelocity);
                setRotation(rotation);

                if (isMovingVertical) // vertical falling = horizontal number line
                    updateVerticalFalling(deltaTime);
                else
                    updateHorizontalFalling(deltaTime);
            }
        }

    }

    private void adjustScale(float deltaTime){
        priceScale += (deltaTime/2*multiplicationFactorForScale);
        if(priceScale > 1.5 )
            multiplicationFactorForScale=-1;

        if( priceScale < 0.85)
            multiplicationFactorForScale=1;

        setScale(priceScale);
    }

    private void updateHorizontalFalling(float deltaTime){

        if (this.getX() < -Constants.VIEWPORT_WIDTH / 2) {
            returnCounter -= 1;
            if (returnCounter < 0) {
                setPositionStartRight(false,0,0); // new position! not eaten!
                returnCounter = maxReturn;

            } else
                this.setPosition(Constants.VIEWPORT_WIDTH / 2, getY()); // come back in the same place
        } else
            this.setPosition(getX() + velocity * deltaTime, getY());

    }

    private void updateVerticalFalling(float deltaTime){
        // TODO perhaps a constant that defines where the ground is
        if(this.getY() < Constants.DETECTION_ZONE_END-this.getHeight()) { // when below number line
            returnCounter-=1;
            if(returnCounter<0){
                setPositionStartAbove(); // new position!
                returnCounter = maxReturn;

            } else
                this.setPosition(getX(), Constants.VIEWPORT_HEIGHT / 2); // come back in the same place
        }
        else
            this.setPosition(getX(),getY()+velocity*deltaTime);
    }

    public void setVelocity(int vel){
        velocity = vel;
    }

    public void setStartAndEnd(int s, int e){
        startNumber = s;
        endNumber = e;
    }

    public void setStart(int s){
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
            setNewPositionMV(Constants.VIEWPORT_HEIGHT / 2 ); // faling from the top
        }
        else{
            setNewPositionMV(Constants.PRICE_Y_HORIZONTAL); // TODO define when we have big bruno, fixed val
        }
    }

    private void setInitialPositionMovingHorizontal(){
        if(isDynamic) {
            setNewPositionMH(Constants.VIEWPORT_WIDTH/2);
        }
        else{
            setNewPositionMH(verticalMiddleXPrice-getWidth()/2);
        }
    }



    public void setNewPositionMV(float startYnow){
        currentNumber = MathUtils.random(1,maxShift);
        // adjust the position to range number (currentNumber-startNumber)
        // and taking into account where we start to draw
        setPosition( myStartX + (currentNumber)*Constants.BASE - getWidth()/2, startYnow );
    }

    public void setNewPositionMH(float myStartXnow){
        currentNumber = MathUtils.random(1,maxShift);
        // adjust the position to range number (currentNumber-startNumber)
        setPosition( myStartXnow, myStartY + (currentNumber)*Constants.BASE - getHeight()/2);
    }


    public void moveToNewPositionHorizontalNL(boolean wasEaten, float toX, float toY){
        int newPosition = MathUtils.random(1,maxShift);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,maxShift);
        }
        currentNumber = newPosition;

        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! moveToNewPositionHorizontalNL");
            addAction(sequence(
                    parallel(Actions.moveTo(toX,toY,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                    //Actions.color(Color.WHITE),
                    Actions.moveTo(myStartX + currentNumber * Constants.BASE - getWidth() / 2, myStartY),
                    Actions.scaleTo(1, 1)
            ));
        }else {
            actionNotEaten(myStartX + currentNumber * Constants.BASE - getWidth() / 2, Constants.PRICE_Y_HORIZONTAL);
        }

    }

//    public void moveToNewPositionHorizontalNL(float startX, int startY){
//        moveToNewPositionHorizontalNL(startX,startY,false);
//    }

    public void moveToNewPositionVerticalNL(boolean wasEaten, float toX, float toY){
        int newPosition = MathUtils.random(1,maxShift);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,maxShift);
        }
        currentNumber = newPosition;


        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! moveToNewPositionVerticalNL");
            addAction(sequence(
                    parallel(Actions.moveTo(toX,toY,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                    //Actions.color(Color.WHITE),
                    Actions.moveTo(verticalMiddleXPrice-getWidth()/2 ,myStartY + currentNumber*Constants.BASE - getHeight()/2),
                    Actions.scaleTo(1,1)


            ));

        }else{
            actionNotEaten(myStartX ,myStartY + currentNumber*Constants.BASE - getHeight()/2);
        }

    }

    private void actionNotEaten(float newX, float newY){
        addAction(sequence(
                parallel(
                        Actions.scaleTo(1.8f,1.8f,0.1f),
                        //Actions.color(Color.GOLD,0.1f)
                        Actions.alpha(0,0.1f)


                ),
                Actions.scaleTo(0.0f,0.0f,0.05f),
                delay(0.2f),
                //Actions.color(Color.WHITE),
                Actions.alpha(1f),
                Actions.moveTo(newX,newY),
                Actions.scaleTo(1,1)


        ));
        priceScale = 1;
        multiplicationFactorForScale = 1;

    }

//    public void moveToNewPositionVerticalNL(){
//        moveToNewPositionVerticalNL(false);
//    }

    public void wasCollected(){

        if (isMovingVertical)
            wasCollectedHorizontalNumberLine();
        else
            wasCollectedVerticalNumberLine();

    }

    public void lastCollected(){
        isLast = true;

        addAction(sequence(
                Actions.scaleTo(1.5f,1.5f,0.1f),
                Actions.scaleTo(0.0f,0.0f,0.05f)
        ));

    }

    public void wasEaten(float whereX, float whereY){
        if (isMovingVertical)
            wasEatenHorizontalNumberLine(whereX,whereY);
        else
            wasEatenVerticalNumberLine(whereX,whereY);

    }

    public void wasEatenHorizontal(float whereX, float whereY) {
        if (isDynamic)
            setPositionStartAbove(true,whereX,whereY);
        else
            moveToNewPositionEatenHorizontal(whereX,whereY);
    }

    public void moveToNewPositionEatenHorizontal(float toX, float toY) {
        int newPosition = MathUtils.random(1, maxShift);
        while (newPosition == currentNumber) {
            newPosition = MathUtils.random(1, maxShift);
        }
        currentNumber = newPosition;


        Gdx.app.log(TAG, "was eaten!! moveToNewPositionHorizontalNL");
        addAction(sequence(
                parallel(Actions.moveTo(toX, toY, 0.5f),
                        Actions.scaleTo(0, 0, 0.5f)),
                delay(0.2f),
               // Actions.color(Color.WHITE),
                Actions.moveTo(myStartX + currentNumber * Constants.BASE - getWidth() / 2, Constants.PRICE_Y_HORIZONTAL),
                Actions.scaleTo(1, 1)
        ));
    }


    public void lastEaten(float whereX, float whereY){
        isLast = true;
        addAction(parallel(Actions.moveTo(whereX,whereY,0.5f),
                Actions.scaleTo(0,0,0.5f)));

    }

    private void wasEatenHorizontalNumberLine(float whereX, float whereY){
        if (isDynamic)
            setPositionStartAbove(true,whereX,whereY);
        else
            moveToNewPositionHorizontalNL(true,Constants.VERTICAL_MIDDLE_X-20,getY()-10);

    }

    private void wasEatenVerticalNumberLine(float whereX, float whereY){
        if (isDynamic)
            setPositionStartRight(true, whereX, whereY);
        else
            moveToNewPositionVerticalNL(true, whereX, whereY);

    }




    public void wasCollectedHorizontalNumberLine(){
        if (isDynamic)
            setPositionStartAbove();
        else
            moveToNewPositionHorizontalNL(false,0,0);// 0s are placeholder, won't be used

    }

    public void wasCollectedVerticalNumberLine(){
        if (isDynamic)
            setPositionStartRight(false,0,0);
        else
            moveToNewPositionVerticalNL(false,0,0);

    }

    private void setPositionStartAbove(boolean wasEaten,float toX, float toY){
        returnCounter = maxReturn; // new position, new counter!

        int newPosition = MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,10);
        }
        currentNumber = newPosition;
        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! setPositionStartAbove");
            addAction(sequence(
                    parallel(Actions.moveTo(toX,toY,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                    //Actions.color(Color.WHITE),
                    Actions.moveTo(myStartX + currentNumber * Constants.BASE - getWidth() / 2, Constants.VIEWPORT_HEIGHT / 2 - getHeight()),
                    Actions.scaleTo(1, 1)
            ));
        }
        else {
            actionNotEaten(myStartX + currentNumber * Constants.BASE - getWidth() / 2, Constants.VIEWPORT_HEIGHT / 2 - getHeight());
        }


    }

    private void setPositionStartAbove(){
        setPositionStartAbove(false,0,0);
    }

    private void setPositionStartRight(boolean wasEaten, float whereX, float whereY){
        returnCounter = maxReturn; // new position, new counter!

        int newPosition = MathUtils.random(1,maxShift);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,maxShift);
        }
        currentNumber = newPosition;
        Gdx.app.log(TAG, " new position for the price x: "+ getX() + " y: "+getY()+" current position "+currentNumber+" new y "+(newPosition * Constants.BASE - getHeight() / 2));

        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! setPositionStartRight");
            addAction(sequence(
                    parallel(Actions.moveTo(whereX,whereY,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                   // Actions.color(Color.WHITE),
                    Actions.moveTo(Constants.VIEWPORT_WIDTH / 2, Constants.DETECTION_ZONE_END + newPosition * Constants.BASE - getHeight() / 2),
                    Actions.scaleTo(1, 1)
            ));
        }
        else {
            actionNotEaten(Constants.VIEWPORT_WIDTH / 2, Constants.DETECTION_ZONE_END + newPosition * Constants.BASE - getHeight() / 2);
        }


    }



    public int getDisplayNumber(){
        return (currentNumber+startNumber);
    }

    public int getCorrectAnswerToPut(){
        return currentNumber;
    }

    public void setDynamicProps(boolean dynamic, int priceReturnNr){
        isDynamic = dynamic;
        maxReturn = priceReturnNr;
        returnCounter = priceReturnNr;

    }

    public boolean isDynamic(){
        return isDynamic;
    }

    public int getPriceTypeNr(){ return priceTypeNr;}





    public int getCurrentNumber(){
        return currentNumber;
    }
}
