package ceta.game.game.levels;

import ceta.game.game.objects.Price;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/6/16.
 */
public class Level2Vertical extends AbstractLevel {
    public static final String TAG = Level2Vertical.class.getName();


    public Level2Vertical(Stage stage, LevelParams levelParams){
        super(stage,levelParams);

    };

    @Override
    public void init() {
        Gdx.app.log(TAG,"init Level Vertical");

        // bruno.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X , -bruno.getHeight());
        // change default horizontal to horizontal moving by first param "false"
        price = new Price(false,levelParams.priceVelocity,levelParams.numberMin, levelParams.numberMax, levelParams.priceReturn, 3);

        // add actors
        //stage.addActor(bruno);
        stage.addActor(price);
    }

    @Override
    public void render(SpriteBatch batch) {
        price.toFront();
        stage.draw();
    }
}
