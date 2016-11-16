package ceta.game.game.levels;

import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.Latter;
import ceta.game.game.objects.Price;
import ceta.game.managers.BrunosManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 11/11/16.
 */
public class Level1Vertical extends LevelHorizontal {
    public static final String TAG = Level1Vertical.class.getName();
    public Latter tube;

    public Level1Vertical(Stage stage, LevelParams levelParams) {
        super(stage, levelParams);
    }

    @Override
    public void init() {
        Gdx.app.log(TAG,"init Level 1 Vertical");

        bruno = new BrunoVertical((short)1,new BrunosManager(stage)); // this one is moving
        bruno.setSize(0,0);

        price = new Price(false,(short)levelParams.priceVelocity,(short)levelParams.numberMin, levelParams.priceReturn);

        tube = new Latter((short)12);
        tube.setWidth(Constants.BASE*1.5f);
        tube.setPosition(Constants.VERTICAL_MIDDLE_X-tube.getWidth()/2,-tube.getHeight());

        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
        stage.addActor(tube);

    }
    @Override
    public void render(SpriteBatch batch) {
        bruno.toFront();
        price.toFront();
        tube.toFront();

        stage.draw();
    }

}
