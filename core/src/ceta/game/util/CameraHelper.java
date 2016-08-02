package ceta.game.util;

import ceta.game.game.objects.AbstractGameObject;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by ewe on 7/25/16.
 */
public class CameraHelper {
    private Vector2 position;
    private float zoom;
    private final float FOLLOW_SPEED = 4.0f;
    private AbstractGameObject target;

    public CameraHelper () {
        position = new Vector2();
        zoom = 1.0f;
    }

    public void update (float deltaTime) {
        if (!hasTarget()) return;
        //  position.x = target.position.x + target.origin.x;
        position.lerp(target.position, FOLLOW_SPEED * deltaTime);
        // position.y = target.position.y + target.origin.y;
        // Prevent camera from moving down too far
        position.y = Math.max(-1f, position.y);
    }
    public AbstractGameObject getTarget () { return target; }
    public boolean hasTarget () { return target != null; }
    public boolean hasTarget (AbstractGameObject target) { return hasTarget() && this.target.equals(target);}
    public void applyTo (OrthographicCamera camera) {
        camera.position.x = position.x;
        camera.position.y = position.y;
        camera.zoom = zoom;
        camera.update();
    }

}
