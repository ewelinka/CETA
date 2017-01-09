package ceta.game.game.levels;

import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.BrunoMovingHorizontal;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/2/16.
 */
public class Level3Horizontal extends LevelHorizontal {
    public Level3Horizontal(Stage stage, LevelParams levelParams) {
        super(stage, levelParams);

    }

    @Override
    public void init() {
        bruno = new BrunoMovingHorizontal();
        // default horizontal
        price = new Price(levelParams.priceVelocity,levelParams.numberMin, levelParams.priceReturn, 4);

        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
    }
}
