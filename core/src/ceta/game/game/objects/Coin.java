package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;

/**
 * Created by ewe on 8/12/16.
 */
public class Coin extends AbstractGameObject {
    public static final String TAG = Coin.class.getName();
    private short velocityX;

    public Coin () {
        init();
    }


    public void init () {
        regTex = Assets.instance.coin.coin;
        velocityX = -100;
        this.setSize(Constants.BASE/2,Constants.BASE/2);
        Gdx.app.log(TAG,this.getWidth()+" "+this.getHeight());
        super.init();

    }

    public void update(float deltaTime){
        if(this.getX() < -Constants.VIEWPORT_WIDTH/2)
            this.setPosition(300, getY());
        else
            this.setPosition(getX()+velocityX*deltaTime, getY());

    }
}
