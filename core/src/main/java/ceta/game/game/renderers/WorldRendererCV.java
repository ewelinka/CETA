package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;

/**
 * Created by ewe on 11/25/16.
 */
public class WorldRendererCV extends WorldRenderer {
    public static final String TAG = WorldRendererCV.class.getName();
    private FeedbackRenderer feedbackRenderer;
    private boolean shouldRenderClue;

    public WorldRendererCV(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal) {
        super(worldController, stage, numberLineIsHorizontal);
        feedbackRenderer = new FeedbackRenderer(stage);
        shouldRenderClue = false;

    }

    @Override
    public void render () {
        super.render();
        if(worldController.isPlayerInactive() ){
            if (!feedbackRenderer.getManoImg().hasActions()) {
                feedbackRenderer.renderClue();
                shouldRenderClue = true;
            }
        }else{
            // if we didn't notify yet
            if(shouldRenderClue){
                stopRenderClue();
                shouldRenderClue = false;
            }
        }

    }

    public void stopRenderClue(){
        feedbackRenderer.stopRenderClue();
    }


    @Override
    protected void renderDetectionZone(ShapeRenderer shRenderer){
        // detection zone in gray
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shRenderer.setColor(Color.LIGHT_GRAY);
        //shapeRenderer.rect(x, y, width, height);
        shRenderer.rect(-Constants.CV_DETECTION_EDGE_TABLET/2, -Constants.VIEWPORT_HEIGHT/2,
                Constants.CV_DETECTION_EDGE_TABLET, Constants.CV_DETECTION_EDGE_TABLET);

        if(worldController.isPlayerInactive()){
            feedbackRenderer.renderColorChange(shRenderer);
        }else{
            feedbackRenderer.resetColorChange();
        }
        shRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    protected void renderBackgroundImg(SpriteBatch batch) {
        TextureAtlas.AtlasRegion b = Assets.instance.background.back;
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

    @Override
    protected void renderHelperNumberLinesVertical(ShapeRenderer shRenderer){
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        shRenderer.setColor(1, 1, 1, 1);

        for(int i = -200; i<=200;i+=Constants.BASE){
            shRenderer.line(i , Constants.DETECTION_ZONE_END, i,Constants.VIEWPORT_HEIGHT/2);
        }
        shRenderer.setColor(0, 0, 1, 1);
        shRenderer.line(-Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_ZONE_END, Constants.VIEWPORT_WIDTH/2,Constants.DETECTION_ZONE_END);

        shRenderer.end();
    }

    @Override
    protected void renderHelperNumberLinesHorizontal(ShapeRenderer shRenderer) {
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        shRenderer.setColor(1, 1, 1, 1);

        for(int i = Constants.DETECTION_ZONE_END; i<=(Constants.DETECTION_ZONE_END +400); i+=Constants.BASE){
            shRenderer.line(-Constants.VIEWPORT_WIDTH/2 , i, 240,i);
        }
        shRenderer.setColor(1, 0, 0, 1);
        //vertical
        shapeRenderer.line(0 , -Constants.VIEWPORT_HEIGHT/2, 0,Constants.VIEWPORT_HEIGHT/2);

        shRenderer.end();
    }


    @Override
    protected void renderHelperNumbersVertical(SpriteBatch batch){

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;
        for(int i = -200; i<240;i+=Constants.BASE){
            font.draw(batch, (levelMinimumNumber+counter)+"", i, Constants.DETECTION_ZONE_END,0, Align.center,false);

            counter+=1;
        }

        batch.end();

    }

    @Override
    protected void renderHelperNumbersHorizontal(SpriteBatch batch){

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;
        for(int i = Constants.DETECTION_ZONE_END; i<=(Constants.DETECTION_ZONE_END +400); i+=Constants.BASE){
            String text = counter+"";
            GlyphLayout layout = new GlyphLayout(font, text);;
            font.draw(batch, (levelMinimumNumber+counter)+"", 250, i + layout.height/2,0,Align.center,false);
            counter+=1;
        }

        batch.end();

    }
}
