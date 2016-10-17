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



    public Price() {
        init();
    }


    public void init () {
        regTex = Assets.instance.toCollect.screw;
        this.setSize(Constants.BASE,Constants.BASE);
        super.init();

    }

    public void update(float deltaTime){

        if(isVertical)
            updateVertical(deltaTime);
        else
            updateHorizontal(deltaTime);

    }

    private void updateHorizontal(float deltaTime){
        if(this.getX() < -Constants.VIEWPORT_WIDTH/2)
            this.setPosition(Constants.VIEWPORT_WIDTH/2, getY());
        else
            this.setPosition(getX()+velocity*deltaTime, getY());
    }

    private void updateVertical(float deltaTime){
        // TODO perhaps a constant that defines where the ground is
        if(this.getY() < -this.getHeight()) // when below number line
            this.setPosition(getX(),Constants.VIEWPORT_HEIGHT/2);
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

    public void setNewPosition(float startX){
        currentNumber = (short)(MathUtils.random(startNumber+1,endNumber));
        setPosition( startX + currentNumber*Constants.BASE - getWidth()/2, Constants.BASE );
    }
    public void moveToNewPosition(float startX){
        short newPosition = (short)MathUtils.random(startNumber+1,endNumber);
        while (newPosition == currentNumber){
            newPosition = (short)MathUtils.random(startNumber+1,endNumber);
        }
        currentNumber = newPosition;


        addAction(Actions.moveTo(startX + newPosition*Constants.BASE - getWidth()/2, Constants.BASE,0.6f));
        //setPosition( );
    }
}
