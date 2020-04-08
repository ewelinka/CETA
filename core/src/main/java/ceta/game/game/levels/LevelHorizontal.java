package ceta.game.game.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;

/**
 * Created by ewe on 8/23/16.
 */
public class LevelHorizontal extends AbstractLevel {
    public static final String TAG = LevelHorizontal.class.getName();

    public LevelHorizontal(Stage stage, LevelParams levelParams, AbstractWorldController worldController){
       super(stage, levelParams, worldController);
    };

    @Override
    public void init() {

        bruno = new Bruno();
        //bruno.setPosition(Constants.HORIZONTAL_ZERO_X - bruno.getWidth(),Constants.DETECTION_ZONE_END);
        bruno.setPosition(-480,Constants.DETECTION_ZONE_END);

        // default horizontal, default 1 representation
        price = new Price(levelParams, worldController);

        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
    }







}
