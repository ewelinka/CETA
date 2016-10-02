package ceta.game.game.levels;

import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Coin;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * Created by ewe on 7/25/16.
 */
public abstract class Level {
    public Bruno bruno;
    public Coin coin;
    protected Stage stage;
    public abstract void init ();
    public abstract void update (float deltaTime);
    public abstract void render(SpriteBatch batch);

}
