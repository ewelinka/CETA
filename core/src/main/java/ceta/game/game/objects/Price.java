package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by ewe on 8/12/16.
 */
public class Price extends AbstractGameObject {
    public static final String TAG = Price.class.getName();
    private short velocity;


    public Price() {
        init();
    }


    public void init () {
        regTex = Assets.instance.toCollect.screw;
        velocity = -100;
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
        if(this.getY() < 0)
            this.setPosition(getX(),Constants.VIEWPORT_HEIGHT/2);
        else
            this.setPosition(getX(),getY()+velocity*deltaTime);
    }

    public void setVelocity(short vel){
        velocity = vel;
    }

    public void setNewPosition(float startX){
        setPosition( startX + (int)(MathUtils.random(1,10))*Constants.BASE - getWidth()/2, Constants.BASE );
    }
}
