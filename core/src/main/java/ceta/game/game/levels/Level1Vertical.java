package ceta.game.game.levels;

import ceta.game.game.objects.Tube;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 11/11/16.
 */
public class Level1Vertical extends LevelHorizontal {
    public static final String TAG = Level1Vertical.class.getName();
    public Tube tube;

    public Level1Vertical(Stage stage, LevelParams levelParams) {
        super(stage, levelParams);
    }

    @Override
    public void init() {
        Gdx.app.log(TAG,"init Level 1 Vertical");
        price = new Price(false,levelParams.priceVelocity,levelParams.numberMin, levelParams.priceReturn, 1);

        tube = new Tube(12);
        //tube.setWidth(Constants.BASE*1.5f);
        tube.setPosition(Constants.VERTICAL_MIDDLE_X-tube.getWidth()/2,Constants.DETECTION_ZONE_END -tube.getHeight()); //TODO change for no-cv!!!

        // add actors
        stage.addActor(price);
        stage.addActor(tube);

    }
    @Override
    public void render(SpriteBatch batch) {
        price.toFront();
        tube.toFront();
        stage.draw();
    }

    public Tube getTube(){
        return tube;
    }

}
