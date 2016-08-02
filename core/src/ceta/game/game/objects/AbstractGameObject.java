package ceta.game.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Body;


/**
 * Created by ewe on 7/25/16.
 */
public abstract class AbstractGameObject {
    public static final String TAG = AbstractGameObject.class.getName();
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    public Vector2 velocity;
    public Rectangle bounds;
    public Vector2 friction;
   // public Body body;
    public float stateTime;

    public AbstractGameObject () {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
        velocity = new Vector2();
        bounds = new Rectangle();
        friction = new Vector2();

        //stateTime = 0;
    }
    public void update (float deltaTime) {
        // Move to new position
        updateMotionY(deltaTime);
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;

    }
    protected void updateMotionY (float deltaTime) {
        if (velocity.y != 0) {
            // Apply friction
            if (velocity.y > 0) {
                velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
            } else {
                velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
            }
        }
    }

    public abstract void render (SpriteBatch batch);
}
