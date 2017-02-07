package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.objects.City;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 1/16/17.
 */
public class WorldRenderer1VerticalMoving extends WorldRenderer {
    private City city;
    private int adjustX = 50;
    private int adjustY = 0;

    public WorldRenderer1VerticalMoving(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal,TextureAtlas.AtlasRegion  regtex) {
        super(worldController, stage, numberLineIsHorizontal,regtex);
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

    @Override
    protected void renderDetectionZoneImg(SpriteBatch batch) {
        TextureAtlas.AtlasRegion blocksZone = Assets.instance.background.blocksTablet;
        // render for deposite
        TextureAtlas.AtlasRegion feedbackZone = Assets.instance.background.feedbackZoneV1Tablet;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        batch.draw(feedbackZone.getTexture(),
                -Constants.VIEWPORT_WIDTH / 2 - adjustX, Constants.DETECTION_LIMIT-adjustY,
                0, 0,
                Constants.VIEWPORT_WIDTH+adjustX, Constants.DETECTION_ZONE_END - Constants.DETECTION_LIMIT+adjustY,
                1, 1,
                0,
                feedbackZone.getRegionX(), feedbackZone.getRegionY(),
                feedbackZone.getRegionWidth(), feedbackZone.getRegionHeight(), false, false);

        batch.end();
    }
}
