package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by ewe on 12/27/16.
 */
public class WorldRenderer1VerticalCV extends WorldRendererCV {
    public WorldRenderer1VerticalCV(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal) {
        super(worldController, stage, numberLineIsHorizontal);
    }

    @Override
    protected void renderDetectionZoneImg(SpriteBatch batch) {
        TextureAtlas.AtlasRegion b = Assets.instance.background.feedbackZoneV2CV;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(b.getTexture(), -Constants.VIEWPORT_WIDTH / 2, Constants.DETECTION_ZONE_END,
                b.getRegionWidth() / 2, b.getRegionHeight() / 2,
                b.getRegionWidth(), b.getRegionHeight(),
                1, 1,
                0,
                b.getRegionX(), b.getRegionY(),
                b.getRegionWidth(), b.getRegionHeight(), false, false);
        batch.end();
    }
}
