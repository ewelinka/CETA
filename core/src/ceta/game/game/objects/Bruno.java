package ceta.game.game.objects;

import ceta.game.game.Assets;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by ewe on 7/26/16.
 */
public class Bruno extends AbstractGameObject {
    public static final String TAG = Bruno.class.getName();
    public enum VIEW_DIRECTION { LEFT, RIGHT }
    private TextureRegion regBody;
    public VIEW_DIRECTION viewDirection;
    public enum BRUNO_STATE {
        GROUNDED, FALLING, RISING
    }
    public BRUNO_STATE bruno_state;

    public Bruno () {
        init();
    }


    public void init () {
        dimension.set(5.0f, 5.0f);
        regBody = Assets.instance.bruno.body;
        // Center image on game object
        origin.set(dimension.x / 2, dimension.y / 2);
        // Bounding box for collision detection
        bounds.set(0, 0, dimension.x, dimension.y);
        // View direction
        viewDirection = VIEW_DIRECTION.RIGHT;
        bruno_state = BRUNO_STATE.GROUNDED;
    }

    @Override
    public void render (SpriteBatch batch) {
        // Draw image
        batch.draw(regBody.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x , dimension.y , scale.x, scale.y,
                rotation,
                regBody.getRegionX(), regBody.getRegionY(),
                regBody.getRegionWidth(), regBody.getRegionHeight(),
                viewDirection == VIEW_DIRECTION.LEFT, false);

    }

    @Override
    protected void updateMotionY (float deltaTime) {
        switch (bruno_state) {
            case GROUNDED:
                bruno_state = BRUNO_STATE.FALLING;
                break;
            default:
                super.updateMotionY(deltaTime);
        }
    }

}
