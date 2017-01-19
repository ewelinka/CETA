package ceta.game.game.renderers;

import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.objects.City;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 1/16/17.
 */
public class WorldRenderer1VerticalMoving extends WorldRenderer {
    private City city;
    public WorldRenderer1VerticalMoving(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal) {
        super(worldController, stage, numberLineIsHorizontal);
        city = new City(spriteBatch, camera);

    }

    @Override
    public void render () {

        clearGray();
        renderMoving();
        renderDetectionZoneImg(spriteBatch);
        renderHelperNumberLines(shapeRenderer);
        renderWorldAndOver();
    }

    private void renderMoving(){
        city.drawAll();

    }

    public void updateMovingBackground(float delta){
        city.update(delta);

    }
}
