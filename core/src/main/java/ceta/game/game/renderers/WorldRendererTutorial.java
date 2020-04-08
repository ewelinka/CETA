package ceta.game.game.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;

import ceta.game.game.controllers.AbstractWorldController;

/**
 * Created by ewe on 2/16/17.
 */
public class WorldRendererTutorial extends  WorldRenderer {
    public WorldRendererTutorial(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal, TextureAtlas.AtlasRegion imgBackground) {
        super(worldController, stage, numberLineIsHorizontal, imgBackground);
        levelTxt = "Tutorial";
    }

    @Override
    protected void renderLevelNumber(SpriteBatch batch){
//        GlyphLayout layout = new GlyphLayout(bigGuiFont, levelTxt);
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
        bigGuiFont.setColor(0,0,0,0.7f);
        bigGuiFont.draw(batch, levelTxt, 0, 500,0, Align.center,false);
//        batch.end();


    }
}
