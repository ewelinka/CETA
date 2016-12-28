package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ewe on 10/19/16.
 */
public abstract class AbstractWorldRenderer implements Disposable {
    public static final String TAG = AbstractWorldRenderer.class.getName();
    protected OrthographicCamera camera;
    protected SpriteBatch spriteBatch;
    protected ShapeRenderer shapeRenderer;
    protected BitmapFont font;
    protected BitmapFont bigFont;
    protected BitmapFont counterFont;
    protected AbstractWorldController worldController;
    protected Stage stage;
    protected Image imgBackground;


    protected int levelMinimumNumber;
    protected boolean numberLineIsHorizontal;


    public abstract void init();
    public abstract void render();
    protected abstract void renderGuiScore(SpriteBatch batch);

    public void resize (int width, int height) {
        camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
        camera.update();
        stage.getViewport().update(width, height, true);
        camera.position.set(0,0,0);
    }

    protected void renderGui (SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderGuiScore(batch);
        batch.end();
    }

    protected void clearWhite(){
        // white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void clearBlue(){
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void renderDetectionZone(ShapeRenderer shRenderer){
        // detection zone in gray
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shRenderer.setColor(Color.LIGHT_GRAY);
        shRenderer.rect(-Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_LIMIT,Constants.VIEWPORT_WIDTH, Constants.DETECTION_ZONE_END-Constants.DETECTION_LIMIT);
        shRenderer.end();
    }

    protected void renderDetectionZoneImg(SpriteBatch batch){
        // render for pieces
        TextureAtlas.AtlasRegion blocksZone = Assets.instance.background.blocksTablet;
        // render for deposite
        TextureAtlas.AtlasRegion feedbackZone = Assets.instance.background.feedbackZoneTablet;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(blocksZone.getTexture(),
                -Constants.VIEWPORT_WIDTH/2, -Constants.VIEWPORT_HEIGHT/2,
                0, 0,
                Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT/2 + Constants.DETECTION_LIMIT,
                1, 1,
                0,
                blocksZone.getRegionX(), blocksZone.getRegionY(),
                blocksZone.getRegionWidth(), blocksZone.getRegionHeight(), false,false);

        batch.draw(feedbackZone.getTexture(),
                -Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_LIMIT,
                0,0,
                Constants.VIEWPORT_WIDTH, Constants.DETECTION_ZONE_END-Constants.DETECTION_LIMIT,
                1, 1,
                0,
                feedbackZone.getRegionX(), feedbackZone.getRegionY(),
                feedbackZone.getRegionWidth(), feedbackZone.getRegionHeight(), false,false);




        batch.end();



    }

    protected void renderBackgroundImg(SpriteBatch batch){
        TextureAtlas.AtlasRegion b = Assets.instance.background.back3;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(b.getTexture(),-Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_ZONE_END,
                b.getRegionWidth()/2, b.getRegionHeight()/2,
                b.getRegionWidth(), b.getRegionHeight(),
                1, 1,
                0,
                b.getRegionX(), b.getRegionY(),
                b.getRegionWidth(), b.getRegionHeight(), false,false);
        batch.end();
    }


    protected void renderHelperNumberLinesHorizontal(ShapeRenderer shRenderer) {
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        shRenderer.setColor(1, 1, 1, 1);

        for(int i = Constants.DETECTION_ZONE_END; i<=(Constants.DETECTION_ZONE_END +400); i+=Constants.BASE){
            shRenderer.line(-Constants.VIEWPORT_WIDTH/2+20 , i, 240,i);
        }


        shRenderer.end();
    }


    protected void renderHelperNumbersVertical(SpriteBatch batch){
        int chosenNr = worldController.level.price.getDisplayNumber();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;

        for(int i = -200; i<240;i+=Constants.BASE){
            if(levelMinimumNumber+counter == chosenNr)
                font.setColor(0,153,0,1);
            else
                font.setColor(0,0,0,0.7f);
            font.draw(batch, (levelMinimumNumber+counter)+"", i, Constants.DETECTION_ZONE_END,0, Align.center,false);

            counter+=1;
        }

        batch.end();

    }


    protected void renderHelperNumbersHorizontal(SpriteBatch batch){
        int chosenNr = worldController.level.price.getDisplayNumber();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;

        for(int i = Constants.DETECTION_ZONE_END; i<=(Constants.DETECTION_ZONE_END +400); i+=Constants.BASE){
            String text = counter+"";
            GlyphLayout layout = new GlyphLayout(font, text);
            if(levelMinimumNumber+counter == chosenNr)
                font.setColor(0,153,0,1);
            else
                font.setColor(0,0,0,0.7f);
            font.draw(batch, (levelMinimumNumber+counter)+"", 250, i + layout.height/2,0,Align.center,false);

            counter+=1;
        }

        batch.end();

    }

    protected void renderHelperNumbers(SpriteBatch batch){
        if(numberLineIsHorizontal)
            renderHelperNumbersVertical(batch);
        else
            renderHelperNumbersHorizontal(batch);
    }

    protected void renderHelperNumberLines(ShapeRenderer shRenderer) {
        if(numberLineIsHorizontal)
            renderHelperNumberLinesVertical(shRenderer);
        else
            renderHelperNumberLinesHorizontal(shRenderer);

    }



    protected void renderHelperNumberLinesVertical(ShapeRenderer shRenderer){
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        shRenderer.setColor(1, 1, 1, 1);

        for(int i = -200; i<=200;i+=Constants.BASE){
            shRenderer.line(i , Constants.DETECTION_ZONE_END, i,Constants.VIEWPORT_HEIGHT/2 - 100);
        }
//        shRenderer.setColor(0, 0, 1, 1);
//        shRenderer.line(-Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_ZONE_END, Constants.VIEWPORT_WIDTH/2,Constants.DETECTION_ZONE_END);

        shRenderer.end();
    }





    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
//        font.dispose();
//        counterFont.dispose();
    }
}
