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
import com.badlogic.gdx.math.Vector2;
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
    protected BitmapFont fontNumberLine;
    protected BitmapFont normalGuiFont,smallGuiFont,bigGuiFont;
    protected AbstractWorldController worldController;
    protected FeedbackRenderer feedbackRenderer;
    protected boolean shouldRenderClue;
    protected Stage stage;
    protected TextureAtlas.AtlasRegion imgBackground;
    protected  String levelTxt = "Nivel ";


    protected int levelMinimumNumber;
    protected boolean numberLineIsHorizontal;
    protected int maxShift;
    private  int dotsDist = 5;


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

    protected void clearGray(){
        Gdx.gl.glClearColor(180 / 255.0f, 196 / 255.0f,0xed / 255.0f, 195 / 255.0f);
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

    protected void renderBelowTheGround(SpriteBatch batch){
        TextureAtlas.AtlasRegion belowZone = Assets.instance.staticBackground.belowTheGround;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        batch.draw(belowZone.getTexture(),
//                -Constants.VIEWPORT_WIDTH/2, -Constants.VIEWPORT_HEIGHT/2,
//                0,0,
//                Constants.VIEWPORT_WIDTH, Constants.DETECTION_ZONE_END-Constants.DETECTION_LIMIT,
//                1, 1,
//                0,
//                belowZone.getRegionX(), belowZone.getRegionY(),
//                belowZone.getRegionWidth(), belowZone.getRegionHeight(), false,false);
        batch.draw(belowZone.getTexture(),
                -Constants.VIEWPORT_WIDTH/2, -Constants.VIEWPORT_HEIGHT/2,
                Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT/2+Constants.GROUND_LEVEL,
                belowZone.getRegionX(), belowZone.getRegionY(),
                belowZone.getRegionWidth(), belowZone.getRegionHeight(), false,false);

        batch.end();

    }

    protected void renderDetectionZoneImg(SpriteBatch batch){
        TextureAtlas.AtlasRegion feedbackZone = Assets.instance.background.feedbackZoneTablet;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

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

    private void drawDottedLine(ShapeRenderer shapeRenderer, int dotDist, float x1, float y1, float x2, float y2) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Point);

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for(int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.point(x1 + vec2.x, y1 + vec2.y, 0);
        }

        shapeRenderer.end();
    }

    protected void renderBackgroundImg(SpriteBatch batch){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if(numberLineIsHorizontal)
            batch.draw(imgBackground.getTexture(),-Constants.VIEWPORT_WIDTH/2, Constants.GROUND_LEVEL,
                    imgBackground.getRegionWidth()/2,imgBackground.getRegionHeight()/2,
                    600, 700,
                    1, 1,
                    0,
                    imgBackground.getRegionX(), imgBackground.getRegionY(),
                    imgBackground.getRegionWidth(), imgBackground.getRegionHeight(), false,false);
        else
            batch.draw(imgBackground.getTexture(),-Constants.VIEWPORT_WIDTH/2, Constants.GROUND_LEVEL,
                    imgBackground.getRegionWidth()/2,imgBackground.getRegionHeight()/2,
                    600, 700,
                    1, 1,
                    0,
                    imgBackground.getRegionX(), imgBackground.getRegionY(),
                    imgBackground.getRegionWidth(), imgBackground.getRegionHeight(), false,false);
        batch.end();
    }


    protected void renderHelperNumberLinesHorizontal(ShapeRenderer shRenderer) {
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        shRenderer.setColor(1, 1, 1, 1);

        for(int i = Constants.GROUND_LEVEL; i<=(Constants.GROUND_LEVEL +Constants.BASE*maxShift); i+=Constants.BASE){
            shRenderer.line(-Constants.VIEWPORT_WIDTH/2+20 , i, 240,i);
        }


        shRenderer.end();
    }

    protected void renderHelperDottedLinesHorizontal(ShapeRenderer shRenderer) {
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.setColor(0, 0, 102/255, 1);


        for(int i = Constants.GROUND_LEVEL; i<=(Constants.GROUND_LEVEL +Constants.BASE*maxShift); i+=Constants.BASE){
            //shRenderer.line(-Constants.VIEWPORT_WIDTH/2+20 , i, 240,i);
            drawDottedLine(shRenderer,dotsDist,-Constants.VIEWPORT_WIDTH/2+20 , i, 240,i);
        }


        shRenderer.end();
    }

    protected void renderLevelNumber(SpriteBatch batch){
        GlyphLayout layout = new GlyphLayout(bigGuiFont, levelTxt+worldController.getLevelNr());
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
        bigGuiFont.setColor(0,0,0,0.7f);
        bigGuiFont.draw(batch, levelTxt+worldController.getLevelNr(), 0, 500,0, Align.center,false);
//        batch.end();


    }

    protected void renderDebugNumbersHorizontal(SpriteBatch batch){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        for(int i = -300; i<300;i+=80){

            fontNumberLine.setColor(0,0,0,1);
            fontNumberLine.draw(batch, i+"", i, Constants.VIEWPORT_HEIGHT/2 -120,0, Align.center,false);
        }

        batch.end();
    }




    protected void renderHelperNumbersHorizontal(SpriteBatch batch){
        int chosenNr = worldController.level.price.getDisplayNumber();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;

        for(int i = Constants.HORIZONTAL_ZERO_X; i<=Constants.HORIZONTAL_ZERO_X+maxShift*Constants.BASE;i+=Constants.BASE){
            if(levelMinimumNumber+counter == chosenNr)
                fontNumberLine.setColor(255/255,255/255,213/255,1);
            else
                fontNumberLine.setColor(1,1,1,1);
            fontNumberLine.draw(batch, (levelMinimumNumber+counter)+"", i, Constants.GROUND_LEVEL -25,0, Align.center,false);
            //fontNumberLine.draw(batch, (levelMinimumNumber+counter)+"", i, Constants.VIEWPORT_HEIGHT/2 -120,0, Align.center,false); //top

            counter+=1;
        }

        batch.end();

    }



    protected void renderHelperNumbersVertical(SpriteBatch batch){
        int chosenNr = worldController.level.price.getDisplayNumber();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;

        for(int i = Constants.GROUND_LEVEL; i<=(Constants.GROUND_LEVEL +Constants.BASE*maxShift); i+=Constants.BASE){
            String text = counter+"";
            GlyphLayout layout = new GlyphLayout(fontNumberLine, text);
            if(levelMinimumNumber+counter == chosenNr)
                fontNumberLine.setColor(255/255,255/255,213/255,1);
            else
                fontNumberLine.setColor(1,1,1,1);
            fontNumberLine.draw(batch, (levelMinimumNumber+counter)+"", 258, i + layout.height/2,0,Align.left,false);

            counter+=1;
        }

        batch.end();

    }

    protected void renderDebugNumbersVertical(SpriteBatch batch){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for(int i = Constants.GROUND_LEVEL; i<=(Constants.GROUND_LEVEL +Constants.BASE*maxShift); i+=80){
            GlyphLayout layout = new GlyphLayout(fontNumberLine, i+"");

            fontNumberLine.setColor(0,0,0,1);
            fontNumberLine.draw(batch, i+"", -258, i + layout.height/2,0,Align.left,false);

        }

        batch.end();

    }

    protected void renderHelperNumbers(SpriteBatch batch){
        if(numberLineIsHorizontal)
            renderHelperNumbersHorizontal(batch);
        else
            renderHelperNumbersVertical(batch);
    }

    protected void renderDebugNumbers(SpriteBatch batch){
        if(numberLineIsHorizontal)
            renderDebugNumbersHorizontal(batch);
        else
            renderDebugNumbersVertical(batch);
    }

    protected void renderHelperNumberLines(ShapeRenderer shRenderer) {
        if(numberLineIsHorizontal) {
//            renderHelperNumberLinesVertical(shRenderer);
            renderHelperDottedLinesVertical(shRenderer);
        }
        else {
            renderHelperDottedLinesHorizontal(shRenderer);
            //renderHelperNumberLinesHorizontal(shRenderer);
        }

    }

    protected void renderNumberLineImg(SpriteBatch batch) {
        if(numberLineIsHorizontal) {
//            renderHelperNumberLinesVertical(shRenderer);
            renderNumberLineImgH(batch);
        }
        else {
            renderNumberLineImgV(batch);
            //renderHelperNumberLinesHorizontal(shRenderer);
        }

    }

    protected void renderNumberLineImgV(SpriteBatch batch){
        TextureAtlas.AtlasRegion nLineV = Assets.instance.background.numberLineV;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//
//        batch.draw(nLineV.getTexture(),
//                250, Constants.DETECTION_LIMIT,
//                0,0,
//                nLineV.getRegionX(), nLineV.getRegionY(),
//                1, 1,
//                0,
//                nLineV.getRegionX(), nLineV.getRegionY(),
//                nLineV.getRegionWidth(), nLineV.getRegionHeight(), false,false);

        batch.draw(nLineV.getTexture(),
                235, Constants.GROUND_LEVEL-24,
                nLineV.getRegionWidth()+16, nLineV.getRegionHeight(),
                nLineV.getRegionX(), nLineV.getRegionY(),
                nLineV.getRegionWidth(), nLineV.getRegionHeight(),false,false);

        batch.end();

    }

    protected void renderNumberLineImgH(SpriteBatch batch){
        TextureAtlas.AtlasRegion nLineH = Assets.instance.background.numberLineH;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

//        batch.draw(nLineH.getTexture(),
//                Constants.HORIZONTAL_ZERO_X-20, Constants.DETECTION_ZONE_END-35,
//                nLineH.getRegionX(), nLineH.getRegionY(),
//                nLineH.getRegionWidth(), nLineH.getRegionHeight());

        batch.draw(nLineH.getTexture(),
                Constants.HORIZONTAL_ZERO_X-19, Constants.DETECTION_ZONE_END-35,
                nLineH.getRegionWidth(), nLineH.getRegionHeight()+10,
                nLineH.getRegionX(), nLineH.getRegionY(),
                nLineH.getRegionWidth(), nLineH.getRegionHeight(), false,false);


//                0,0,
//                nLineH.getRegionX(), nLineH.getRegionY(),
//                1, 1,
//                0,
//                nLineH.getRegionX(), nLineH.getRegionY(),
//                nLineH.getRegionWidth(), nLineH.getRegionHeight(), false,false);

        batch.end();

    }



    protected void renderHelperNumberLinesVertical(ShapeRenderer shRenderer){
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        shRenderer.setColor(1, 1, 1, 1);

        for(int i = Constants.HORIZONTAL_ZERO_X; i<= Constants.HORIZONTAL_ZERO_X+maxShift*Constants.BASE;i+=Constants.BASE){
            shRenderer.line(i , Constants.GROUND_LEVEL, i,Constants.VIEWPORT_HEIGHT/2 - 100);
        }
        shRenderer.end();
    }

    protected void renderHelperDottedLinesVertical(ShapeRenderer shRenderer){
        Gdx.gl.glLineWidth(1);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.setColor(0, 0, 102/255, 1);

        for(int i = Constants.HORIZONTAL_ZERO_X; i<= Constants.HORIZONTAL_ZERO_X+maxShift*Constants.BASE;i+=Constants.BASE){
            //shRenderer.line(i , Constants.GROUND_LEVEL, i,Constants.VIEWPORT_HEIGHT/2 - 100);
            drawDottedLine(shRenderer,dotsDist,i , Constants.GROUND_LEVEL, i,Constants.VIEWPORT_HEIGHT/2 - 100);
        }
        shRenderer.end();
    }



    protected int getCurrentPriceImgNr(){
        return worldController.getCurrentPriceType();
    }



    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
//        fontNumberLine.dispose();
//        counterFont.dispose();
    }
}
