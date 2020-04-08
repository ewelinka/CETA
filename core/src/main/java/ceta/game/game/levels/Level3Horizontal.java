package ceta.game.game.levels;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.objects.BrunoMovingHorizontal;
import ceta.game.game.objects.Gear;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;

/**
 * Created by ewe on 12/2/16.
 */
public class Level3Horizontal extends AbstractLevel {
    public Gear gear;
    public Level3Horizontal(Stage stage, LevelParams levelParams, AbstractWorldController worldController) {
        super(stage, levelParams, worldController);

    }

    @Override
    public void init() {
        bruno = new BrunoMovingHorizontal();
        // default horizontal
        price = new Price(levelParams, worldController);

        gear = new Gear(-Constants.VIEWPORT_WIDTH/2 -110,Constants.DETECTION_ZONE_END);
        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
        stage.addActor(gear);
    }

}
