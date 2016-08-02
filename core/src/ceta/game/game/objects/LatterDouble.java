package ceta.game.game.objects;

import ceta.game.game.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by ewe on 8/1/16.
 */
public class LatterDouble extends AbstractLatter {
    public static final String TAG = LatterDouble.class.getName();

    public LatterDouble(){}

    public void init(){
        dimension.set(5.0f,10.0f);
        regLatter = Assets.instance.latterDouble.latter;
        Gdx.app.debug(TAG, "in init() "+ dimension.y);
        super.init();

    }
}
