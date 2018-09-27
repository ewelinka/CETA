package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;


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
    protected float alphaColor=0.5f;
    protected boolean fadeIn=true;
    protected int brunoNowVal = 0;
    private Texture tex;
    private Pixmap pixmap;
    private Sprite sprite;
    private int debugImgSize =360;
    private Actor magnet, arrow;


    public abstract void init();
    public abstract void render(float delta);
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
        batch.draw(belowZone.getTexture(),
                -Constants.VIEWPORT_WIDTH/2, -Constants.VIEWPORT_HEIGHT/2,
                Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT/2+Constants.GROUND_LEVEL,
                belowZone.getRegionX(), belowZone.getRegionY(),
                belowZone.getRegionWidth(), belowZone.getRegionHeight(), false,false);
        batch.end();

    }

    protected void renderDetectionZoneImgOrDebug(SpriteBatch batch){
            renderDetectionZoneImg(batch);
    }


    protected void renderDetectionZoneImg(SpriteBatch batch){
        boolean plus10 = worldController.getMinimumNumber() >= 10;
        TextureAtlas.AtlasRegion feedbackZone = plus10 ? Assets.instance.background.feedbackZoneTabletPlus10 : Assets.instance.background.feedbackZoneTablet;
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
        GlyphLayout layout = new GlyphLayout(bigGuiFont, levelTxt+(worldController.getLevelNr()+Constants.LAST_LEVEL_NR* GamePreferences.instance.getRepeatNr()));
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
        bigGuiFont.setColor(0,0,0,0.7f);
        bigGuiFont.draw(batch, levelTxt+(worldController.getLevelNr()+Constants.LAST_LEVEL_NR*GamePreferences.instance.getRepeatNr()), 0, 490,0, Align.center,false);
//        batch.end();


    }

    protected void renderHelperNumbersHorizontal(SpriteBatch batch, float aColor){
        int chosenNr = worldController.level.price.getDisplayNumber();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;

        for(int i = Constants.HORIZONTAL_ZERO_X; i<=Constants.HORIZONTAL_ZERO_X+maxShift*Constants.BASE;i+=Constants.BASE){
            if(levelMinimumNumber+counter == chosenNr)
                fontNumberLine.setColor(Color.WHITE.cpy().lerp(Color.CORAL, aColor));
            else
                fontNumberLine.setColor(1,1,1,1);
            fontNumberLine.draw(batch, (levelMinimumNumber+counter)+"", i, Constants.GROUND_LEVEL -25,0, Align.center,false);
            //fontNumberLine.draw(batch, (levelMinimumNumber+counter)+"", i, Constants.VIEWPORT_HEIGHT/2 -120,0, Align.center,false); //top

            counter+=1;
        }

        batch.end();

    }



    protected void renderHelperNumbersVertical(SpriteBatch batch, float aColor){
        int chosenNr = worldController.level.price.getDisplayNumber();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        int counter  = 0;

        for(int i = Constants.GROUND_LEVEL; i<=(Constants.GROUND_LEVEL +Constants.BASE*maxShift); i+=Constants.BASE){
            String text = counter+"";
            GlyphLayout layout = new GlyphLayout(fontNumberLine, text);
            if(levelMinimumNumber+counter == chosenNr)
                fontNumberLine.setColor(Color.WHITE.cpy().lerp(Color.CORAL, aColor));
            else
                fontNumberLine.setColor(1,1,1,1);
            fontNumberLine.draw(batch, (levelMinimumNumber+counter)+"", 258, i + layout.height/2,0,Align.left,false);

            counter+=1;
        }

        batch.end();

    }


    protected void renderHelperNumbers(SpriteBatch batch, float delta){
        if(fadeIn){
            alphaColor+=(delta);
        }
        else
            alphaColor-=(delta);

        if(alphaColor >= 1.3){
            fadeIn=false;
        }
        if(alphaColor <= 0.1)
            fadeIn=true;

        if(numberLineIsHorizontal)
            renderHelperNumbersHorizontal(batch, alphaColor);
        else
            renderHelperNumbersVertical(batch,alphaColor);
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

    protected void renderBrunoPositionLine(ShapeRenderer shRenderer){
        Gdx.gl.glLineWidth(2);
        shRenderer.setProjectionMatrix(camera.combined);
        shRenderer.setColor(Color.CORAL);
        shRenderer.begin(ShapeRenderer.ShapeType.Line);
        int brunoNow;

        if(worldController.isWaitForFirstMove())
            brunoNowVal = worldController.getNowDetectedSum();

        if(numberLineIsHorizontal) {
            brunoNow = Constants.HORIZONTAL_ZERO_X + brunoNowVal*Constants.BASE;
            shRenderer.line(brunoNow , Constants.GROUND_LEVEL, brunoNow,Constants.VIEWPORT_HEIGHT/2 - 100);
            //drawDottedLine(shRenderer,dotsDist,brunoNow, Constants.GROUND_LEVEL, brunoNow,Constants.VIEWPORT_HEIGHT/2 - 100);
        }
        else {
            brunoNow = Constants.GROUND_LEVEL + brunoNowVal*Constants.BASE;
            shRenderer.line(-Constants.VIEWPORT_WIDTH/2+20 , brunoNow, 240,brunoNow);
            //drawDottedLine(shRenderer,dotsDist,-Constants.VIEWPORT_WIDTH/2+20 , brunoNow, 240,brunoNow);
        }
        shRenderer.end();
        shRenderer.setColor(Color.WHITE);
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



    protected void renderMagnet(){
        if(!magnet.hasActions()){
            magnet.addAction(sequence(
                    moveTo(Constants.VIEWPORT_WIDTH/2 - 20,magnet.getY(), 5.0f* MathUtils.random(1.0f,3.0f), Interpolation.bounceOut),
                    delay(0.3f),
                    moveTo(-magnet.getWidth()-100,magnet.getY(), 6.7f * MathUtils.random(1.0f,3.0f), Interpolation.bounceOut)
            ));
        }


    }
    protected void addMagnet(){
        magnet = new Image(Assets.instance.tree.magnet);
        magnet.setScale(0.66f);
        magnet.setPosition(-magnet.getWidth(),Constants.VIEWPORT_HEIGHT/2- magnet.getHeight()* magnet.getScaleY() + 20);
        stage.addActor(magnet);

    }

    protected void addArrow(){
        arrow = new Image(Assets.instance.tree.arrowWhite);
        float changeSpeed = 0.5f;

        int arrowY = Constants.WITH_CV ? -440 : -280;

        arrow.setColor(1,1,0,0);
        arrow.setPosition(-arrow.getWidth()/2,arrowY);
        arrow.setRotation(0);
        arrow.addAction(sequence(
                delay(1.1f),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                alpha(1,changeSpeed),
                alpha(0,changeSpeed),
                run(new Runnable() {
                    public void run() {
                        arrow.remove();
                    }
                })

        ));

        stage.addActor(arrow);
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
//        fontNumberLine.dispose();
//        counterFont.dispose();
    }

}
