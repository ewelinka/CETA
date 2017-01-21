package ceta.game.game.levels;

import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.BrunoMovingHorizontal;
import ceta.game.game.objects.Gear;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/2/16.
 */
public class Level3Horizontal extends AbstractLevel {
    private Gear gear;
    public Level3Horizontal(Stage stage, LevelParams levelParams) {
        super(stage, levelParams);

    }

    @Override
    public void init() {
        bruno = new BrunoMovingHorizontal();
        // default horizontal
        price = new Price(levelParams.priceVelocity,levelParams.numberMin, levelParams.priceReturn, 4);

        gear = new Gear(-Constants.VIEWPORT_WIDTH/2 -110,Constants.DETECTION_ZONE_END);
        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
        stage.addActor(gear);
    }
}
