package ceta.game.game.levels;

import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.objects.BrunoJetPack;
import ceta.game.game.objects.BrunoVertical;
import ceta.game.game.objects.Price;
import ceta.game.managers.BrunosManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by ewe on 12/29/16.
 */
public class Level3Vertical extends  AbstractLevel {
    public static final String TAG = Level3Vertical.class.getName();

    public Level3Vertical(Stage stage, LevelParams levelParams, AbstractWorldController worldController) {
        super(stage, levelParams,worldController);
    }


    @Override
    public void init() {
        Gdx.app.log(TAG,"init Level Vertical");

        bruno = new BrunoJetPack(1,new BrunosManager(stage)); // this one is moving, brunos manager doesn't matter
        bruno.setSize(Constants.BASE, Constants.BASE);
        bruno.setPosition(-Constants.VIEWPORT_WIDTH/2 - 100,Constants.GROUND_LEVEL-bruno.getHeight()/2);

        // bruno.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X , -bruno.getHeight());
        // change default horizontal to horizontal moving by first param "false"
        price = new Price(false, 4, levelParams, worldController);
        if(price.isDynamic())
            bruno.addAction(Actions.moveTo(0-bruno.getWidth()/2,Constants.GROUND_LEVEL-bruno.getHeight()/2,1.5f));
        else
            bruno.addAction(Actions.moveTo(Constants.VERTICAL_MIDDLE_X - bruno.getWidth()/2,Constants.GROUND_LEVEL-bruno.getHeight()/2,1.5f));


        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
    }

    @Override
    public void render(SpriteBatch batch) {
        bruno.toFront();
        stage.draw();
    }
}
