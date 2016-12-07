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
    // number line limits
    private int startNumber;
    private int endNumber;
    private int currentNumber; // current position between 1 and 10

    private boolean isDynamic;
    private int maxReturn;
    private int returnCounter;
    private boolean isMovingVertical;

    //TODO shouldn't be fixed val!!!
    private float myStartX;
    private int rotationVelocity;
    private int verticalMiddleXPrice = Constants.VERTICAL_MIDDLE_X + Constants.PRICE_X_OFFSET;



    public Price(int vel, int startNr, int priceReturn) {
        this(true, vel, startNr, priceReturn); // horizontal
    }


    public Price(boolean isMovingVertical, int vel, int startNr, int priceReturn) {
        this.isMovingVertical = isMovingVertical;
        init();
        localInit(vel,startNr,priceReturn);


    }


    public void init () {
        regTex = Assets.instance.toCollect.screw;
        this.setSize(Constants.BASE,Constants.BASE);
        super.init();

    }

    private void localInit(int vel, int start, int priceReturn){
        myStartX = Constants.HORIZONTAL_ZERO_X;
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
        if(!hasActions()) {
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

    private void updateHorizontalFalling(float deltaTime){

        if (this.getX() < -Constants.VIEWPORT_WIDTH / 2) {
            returnCounter -= 1;
            if (returnCounter < 0) {
                setPositionStartRight(); // new position!
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
                setPositionStartAbove(myStartX); // new position!
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
            setNewPositionMV(myStartX, Constants.VIEWPORT_HEIGHT / 2 ); // faling from the top
        }
        else{
            setNewPositionMV(myStartX, Constants.PRICE_Y_HORIZONTAL); // TODO define when we have big bruno, fixed val
        }
    }

    private void setInitialPositionMovingHorizontal(){
        if(isDynamic) {
            setNewPositionMH(Constants.VIEWPORT_WIDTH/2  );
        }
        else{
            setNewPositionMH(verticalMiddleXPrice-getWidth()/2);
        }
    }



    public void setNewPositionMV(float startX, float startY){
        currentNumber = MathUtils.random(1,10);
        // adjust the position to range number (currentNumber-startNumber)
        // and taking into account where we start to draw
        setPosition( startX + (currentNumber)*Constants.BASE - getWidth()/2, startY );
    }

    public void setNewPositionMH(float startX){
        currentNumber = MathUtils.random(1,10);
        // adjust the position to range number (currentNumber-startNumber)
        setPosition( startX, Constants.DETECTION_ZONE_END + (currentNumber)*Constants.BASE - getHeight()/2);
    }


    public void moveToNewPositionHorizontalNL(float startX, int startY, boolean wasEaten){
        int newPosition = MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,10);
        }
        currentNumber = newPosition;

        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! moveToNewPositionHorizontalNL");
            addAction(sequence(
                    parallel(Actions.moveTo(Constants.VERTICAL_MIDDLE_X-20,getY()-10,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(startX + currentNumber * Constants.BASE - getWidth() / 2, startY),
                    Actions.scaleTo(1, 1)
            ));
        }else {
            addAction(sequence(
                    parallel(
                            Actions.scaleTo(1.5f, 1.5f, 0.1f),
                            Actions.color(Color.GOLD, 0.1f)

                    ),
                    Actions.scaleTo(0.0f, 0.0f, 0.05f),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(startX + currentNumber * Constants.BASE - getWidth() / 2, startY),
                    Actions.scaleTo(1, 1)
            ));
        }

    }

    public void moveToNewPositionHorizontalNL(float startX, int startY){
        moveToNewPositionHorizontalNL(startX,startY,false);
    }

    public void moveToNewPositionVerticalNL(float startX, int startY, boolean wasEaten){
        int newPosition = MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,10);
        }
        currentNumber = newPosition;


        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! moveToNewPositionVerticalNL");
            addAction(sequence(
                    parallel(Actions.moveTo(Constants.VERTICAL_MIDDLE_X-20,getY()-10,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(startX ,startY + currentNumber*Constants.BASE - getHeight()/2),
                    Actions.scaleTo(1,1)


            ));

        }else{
            addAction(sequence(
                    parallel(
                        Actions.scaleTo(1.5f,1.5f,0.1f),
                        Actions.color(Color.GOLD,0.1f)

                    ),
                    Actions.scaleTo(0.0f,0.0f,0.05f),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(startX ,startY + currentNumber*Constants.BASE - getHeight()/2),
                    Actions.scaleTo(1,1)


            ));
        }

    }

    public void moveToNewPositionVerticalNL(float startX, int startY){
        moveToNewPositionVerticalNL(startX,startY,false);
    }

    public void wasCollected(){

        if (isMovingVertical)
            wasCollectedHorizontalNumberLine();
        else
            wasCollectedVerticalNumberLine();

    }

    public void lastCollected(){

        addAction(sequence(
                parallel(
                        Actions.scaleTo(1.5f,1.5f,0.1f),
                        Actions.color(Color.GOLD,0.1f)

                ),
                Actions.scaleTo(0.0f,0.0f,0.05f)
        ));

    }

    public void wasEaten(){

        if (isMovingVertical)
            wasEatenHorizontalNumberLine();
        else
            wasEatenVerticalNumberLine();

    }

    public void lastEaten(){

        addAction(parallel(Actions.moveTo(Constants.VERTICAL_MIDDLE_X-20,getY()-10,0.5f),
                Actions.scaleTo(0,0,0.5f)));

    }

    private void wasEatenHorizontalNumberLine(){
        if (isDynamic)
            setPositionStartAbove(myStartX, true);
        else
            moveToNewPositionHorizontalNL(myStartX,Constants.PRICE_Y_HORIZONTAL,true);

    }

    private void wasEatenVerticalNumberLine(){
        if (isDynamic)
            setPositionStartRight(true);
        else
            moveToNewPositionVerticalNL(verticalMiddleXPrice- getWidth()/2,Constants.DETECTION_ZONE_END,true);

    }




    public void wasCollectedHorizontalNumberLine(){
        if (isDynamic)
            setPositionStartAbove(myStartX);
        else
            moveToNewPositionHorizontalNL(myStartX,Constants.PRICE_Y_HORIZONTAL);

    }

    public void wasCollectedVerticalNumberLine(){
        if (isDynamic)
            setPositionStartRight();
        else
            moveToNewPositionVerticalNL(verticalMiddleXPrice- getWidth()/2,Constants.DETECTION_ZONE_END);

    }

    private void setPositionStartAbove(float startX, boolean wasEaten){
        returnCounter = maxReturn; // new position, new counter!

        int newPosition = MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,10);
        }
        currentNumber = newPosition;
        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! setPositionStartAbove");
            addAction(sequence(
                    parallel(Actions.moveTo(Constants.VERTICAL_MIDDLE_X-20,getY()-10,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(startX + currentNumber * Constants.BASE - getWidth() / 2, Constants.VIEWPORT_HEIGHT / 2 - getHeight()),
                    Actions.scaleTo(1, 1)
            ));
        }
        else {
            addAction(sequence(
                    parallel(
                            Actions.scaleTo(1.5f, 1.5f, 0.1f),
                            Actions.color(Color.GOLD, 0.1f)

                    ),
                    Actions.scaleTo(0.0f, 0.0f, 0.05f),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(startX + currentNumber * Constants.BASE - getWidth() / 2, Constants.VIEWPORT_HEIGHT / 2 - getHeight()),
                    Actions.scaleTo(1, 1)

            ));
        }


    }

    private void setPositionStartAbove(float startX){
        setPositionStartAbove(startX, false);
    }

    private void setPositionStartRight(boolean wasEaten){
        returnCounter = maxReturn; // new position, new counter!

        int newPosition = MathUtils.random(1,10);
        while (newPosition == currentNumber){
            newPosition = MathUtils.random(1,10);
        }
        currentNumber = newPosition;
        Gdx.app.log(TAG, " new position for the price x: "+ getX() + " y: "+getY()+" current position "+currentNumber+" new y "+(newPosition * Constants.BASE - getHeight() / 2));

        if(wasEaten){
            Gdx.app.log(TAG, "was eaten!! setPositionStartRight");
            addAction(sequence(
                    parallel(Actions.moveTo(Constants.VERTICAL_MIDDLE_X-20,getY()-10,0.5f),
                            Actions.scaleTo(0,0,0.5f)),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(Constants.VIEWPORT_WIDTH / 2, Constants.DETECTION_ZONE_END + newPosition * Constants.BASE - getHeight() / 2),
                    Actions.scaleTo(1, 1)
            ));
        }
        else {
            addAction(sequence(
                    parallel(
                            Actions.scaleTo(1.5f, 1.5f, 0.1f),
                            Actions.color(Color.GOLD, 0.1f)

                    ),
                    Actions.scaleTo(0.0f, 0.0f, 0.05f),
                    delay(0.2f),
                    Actions.color(Color.WHITE),
                    Actions.moveTo(Constants.VIEWPORT_WIDTH / 2, Constants.DETECTION_ZONE_END + newPosition * Constants.BASE - getHeight() / 2),
                    Actions.scaleTo(1, 1)
            ));
        }


    }

    private void setPositionStartRight() {
        setPositionStartRight(false);
    }

    public int getDisplayNumber(){
        return (currentNumber+startNumber);
    }

    public void setDynamicProps(boolean dynamic, int priceReturnNr){
        isDynamic = dynamic;
        maxReturn = priceReturnNr;
        returnCounter = priceReturnNr;

    }

    public boolean isDynamic(){
        return isDynamic;
    }





    public int getCurrentNumber(){
        return currentNumber;
    }
}
