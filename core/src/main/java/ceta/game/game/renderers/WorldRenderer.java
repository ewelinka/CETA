package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 7/25/16.
 */
public class WorldRenderer extends AbstractWorldRenderer {

    public WorldRenderer(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal) {
        this.stage = stage;
        this.worldController = worldController;
        this.numberLineIsHorizontal = numberLineIsHorizontal;
        init();
    }

    public WorldRenderer(AbstractWorldController worldController, Stage stage) {
        this(worldController,stage,true); //default horizontal number line

    }

    public void init () {
        levelMinimumNumber = worldController.getMinimumNumber();

        spriteBatch = new SpriteBatch();
        font = Assets.instance.fonts.defaultSmall;
        bigFont = Assets.instance.fonts.defaultNormal;
        counterFont = Assets.instance.fonts.defaultBig;
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.setToOrtho(false,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
        stage.getViewport().setCamera(camera);

    }


    public void render () {
      //  clearWhite();
        clearBlue();

        //renderDetectionZone(shapeRenderer);
        renderDetectionZoneImg(spriteBatch);
        renderBackgroundImg(spriteBatch);
        renderHelperNumberLines(shapeRenderer);
        renderWorld(spriteBatch);

        if(worldController.isNumberLineVisible())
            renderHelperNumbers(spriteBatch);

        if (worldController.getCountdownOn()) {
            if(GamePreferences.instance.actionSubmit)
                renderCounter(spriteBatch);
        }
        //renderPriceValue(spriteBatch);
        renderGui(spriteBatch);

    }



    protected void renderCounter(SpriteBatch batch){

        // we render countdown!
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        String text = worldController.getCountdownCurrentTime()+"";
        GlyphLayout layout = new GlyphLayout(counterFont, text);
        counterFont.setColor(Color.RED);
        counterFont.draw(batch, text+"", 0 - layout.width/2, -6 * Constants.BASE-100);
        batch.end();


    }

    protected void renderWorld (SpriteBatch batch) {
        worldController.cameraHelper.applyTo(camera);
        // we draw coin on the top

//        worldController.level.bruno.toFront();
//        worldController.level.price.toFront();

        batch.begin();
        worldController.level.render(batch);
        batch.end();
    }

    protected void renderPriceValue(SpriteBatch batch){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.setColor(Color.RED);
        font.draw(batch, worldController.level.price.getDisplayNumber()+"",
                worldController.level.price.getX()+worldController.level.price.getWidth()/2,
                worldController.level.price.getY()+worldController.level.price.getHeight()+10,
                0,Align.center,false);
        font.setColor(Color.WHITE);
        batch.end();
    }

    protected void renderGuiScore (SpriteBatch batch) {
        float x =  -Constants.VIEWPORT_WIDTH/2 + 10;
        float y = Constants.VIEWPORT_HEIGHT/2 - 50;
        batch.draw(Assets.instance.toCollect.screw,x,y,40,40);
        // TODO hardcoded position!
        bigFont.draw(batch, worldController.score+"",x+50,y+30);
    }


    private void renderWin(){
        imgBackground = new Image(Assets.instance.finishBackGround.finishBack);
        stage.addActor(imgBackground);
        imgBackground.setOrigin(imgBackground.getWidth() / 2, imgBackground.getHeight() / 2);
        imgBackground.setPosition(-Constants.VIEWPORT_WIDTH/2,-400);
        imgBackground.addAction(sequence(
//                moveTo(135, -20),
                scaleTo(0, 0),
//                fadeOut(0),
               // delay(0.5f),
                parallel(moveBy(0, 100, 1.5f, Interpolation.swingOut),
                        scaleTo(1.0f, 1.0f, 0.25f, Interpolation.linear),
                        alpha(1.0f, 0.5f)),
                alpha(0,1f),
                removeActor()
        ));
    }


}
