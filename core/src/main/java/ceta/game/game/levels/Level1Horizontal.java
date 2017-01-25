package ceta.game.game.levels;

import ceta.game.game.objects.MegaBrunoWithArm;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/27/16.
 */
public class Level1Horizontal extends AbstractLevel {
    public Level1Horizontal(Stage stage, LevelParams levelParams) {
        super(stage, levelParams);
    }

    @Override
    public void init() {

        bruno = new MegaBrunoWithArm();
        //bruno.setSize(bruno.getWidth()*429/bruno.getHeight(),429); // scale to aspirador robot
        bruno.setPosition(-473,Constants.DETECTION_ZONE_END);
        //bruno.setPosition(Constants.HORIZONTAL_ZERO_X - bruno.getWidth(), Constants.DETECTION_ZONE_END);

        // default horizontal
        price = new Price(levelParams.priceVelocity,levelParams.numberMin,levelParams.numberMax, levelParams.priceReturn, 2);

        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
    }
}
