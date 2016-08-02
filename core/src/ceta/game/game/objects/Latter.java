package ceta.game.game.objects;

import ceta.game.game.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by ewe on 8/1/16.
 */
public class Latter extends AbstractLatter {
    public static final String TAG = Latter.class.getName();


    public Latter(){}

    public void init(){
        dimension.set(5.0f,5.0f);
        regLatter = Assets.instance.latter.latter;
        Gdx.app.debug(TAG, "in init() "+ dimension.y);

        velocity.y = 2.0f;

        latter_state = LATTER_STATE.HIDDEN;
        super.init();

    }
    @Override
    public void render(SpriteBatch batch) {
        //batch.draw(regLatter.getTexture(),position.x,position.y);
        batch.draw(regLatter.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x , dimension.y , scale.x, scale.y,
                rotation,
                regLatter.getRegionX(), regLatter.getRegionY(),
                regLatter.getRegionWidth(), regLatter.getRegionHeight(), false,false);

    }

    @Override
    protected void updateMotionY (float deltaTime) {
        switch (latter_state) {
            case GROUNDED:
                break;
            case RISING:
                super.updateMotionY(deltaTime);
                break;
            case FALLING:
                break;
            case HIDDEN:
                break;
        }

        //super.updateMotionY(deltaTime);

    }
}
