package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/27/16.
 */
public class WorldRenderer1Vertical extends WorldRenderer{
    private int adjustX = 50;
    private int adjustY = 0;

    public WorldRenderer1Vertical(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal) {
        super(worldController, stage, numberLineIsHorizontal);
    }

    public WorldRenderer1Vertical(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal,TextureAtlas.AtlasRegion imgBackground) {
        super(worldController, stage, numberLineIsHorizontal,imgBackground);
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
