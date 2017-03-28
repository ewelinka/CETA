package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by ewe on 3/22/17.
 */
public class Cloud extends AbstractGameObject {
    public static final String TAG = Cloud.class.getName();

    private float cloudSpeed;
    private TextureAtlas.AtlasRegion [] clouds ={
            Assets.instance.background.cloud1,
            Assets.instance.background.cloud2,
            Assets.instance.background.cloud3,
            Assets.instance.background.cloud4,
            Assets.instance.background.cloud5,
    };


    public Cloud(){
        init();
    }

    public void init () {

        cloudSpeed = MathUtils.random(20,60);
        regTex = clouds[MathUtils.random(clouds.length-1)];
        setSize(regTex.getRegionWidth(),regTex.getRegionHeight());
        setPosition(Constants.VIEWPORT_WIDTH/2 + MathUtils.random(20,100),MathUtils.random(300,500));

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        setPosition(getX()-delta*cloudSpeed,getY());
        if(getX()< -Constants.VIEWPORT_WIDTH/2-100){
            init();

        }
    }


//    public void update (float deltaTime) {
//
//        setPosition(getX()+deltaTime*cloudSpeed,getY());
//        if(getX()< -Constants.VIEWPORT_WIDTH/2-100);
//
//    }
}
