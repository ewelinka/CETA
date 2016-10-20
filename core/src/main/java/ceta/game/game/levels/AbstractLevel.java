package ceta.game.game.levels;

import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Price;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;


/**
 * Created by ewe on 7/25/16.
 */
public abstract class AbstractLevel {
    public Bruno bruno;
    public Price price;

    public LevelParams levelParams;

    protected Stage stage;
    public abstract void init ();
    public abstract void update (float deltaTime);
    public abstract void render(SpriteBatch batch);

    public int getOperationsNumber(){
        return levelParams.operationsNumber;
    }

    public boolean isDynamic(){
        return levelParams.isDynamic;
    }

}
