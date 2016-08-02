package ceta.game.game.objects;

import ceta.game.game.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by ewe on 8/1/16.
 */
public abstract class AbstractLatter extends AbstractGameObject {
    public static final String TAG = AbstractLatter.class.getName();
    public TextureRegion regLatter;
    public enum LATTER_STATE {
        GROUNDED, FALLING, RISING, HIDDEN
    }
    public LATTER_STATE latter_state;


    public AbstractLatter(){init();}

    public void init(){
        Gdx.app.debug(TAG, "in init() "+ dimension.y);
        origin.set(dimension.x / 2, dimension.y / 2);
        bounds.set(0, 0, dimension.x, dimension.y);
    }

    @Override
    public void render(SpriteBatch batch) {
        //batch.draw(regLatter.getTexture(),position.x,position.y);
        batch.draw(regLatter.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x , dimension.y , scale.x, scale.y,
                rotation,
                regLatter.getRegionX(), regLatter.getRegionY(),
                regLatter.getRegionWidth(), regLatter.getRegionHeight(), false,false);

    }
}
