package ceta.game.game.levels;

import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;

/**
 * Created by ewe on 8/23/16.
 */
public class LevelHorizontal extends AbstractLevel {
    public static final String TAG = LevelHorizontal.class.getName();

    public LevelHorizontal(Stage stage, LevelParams levelParams){
       super(stage, levelParams);
    };

    @Override
    public void init() {

        bruno = new Bruno();
        //bruno.setPosition(Constants.HORIZONTAL_ZERO_X - bruno.getWidth(),Constants.DETECTION_ZONE_END);
        bruno.setPosition(-480,Constants.DETECTION_ZONE_END);

        // default horizontal
        price = new Price(levelParams.priceVelocity,levelParams.numberMin, levelParams.priceReturn);

        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
    }







}
