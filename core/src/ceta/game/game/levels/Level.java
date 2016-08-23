package ceta.game.game.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


/**
 * Created by ewe on 7/25/16.
 */
public abstract class Level {

    public abstract void init ();
    public abstract void update (float deltaTime);
    public abstract void render(SpriteBatch batch);

}
