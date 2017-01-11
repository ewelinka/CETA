package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 7/25/16.
 */
public class WorldRenderer extends AbstractWorldRenderer {
    public static final String TAG = WorldRenderer.class.getName();
    //protected FeedbackRenderer feedbackRenderer;
    protected int currentPriceTypeNr;

    public WorldRenderer(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal) {
        this.stage = stage;
        this.worldController = worldController;
        this.numberLineIsHorizontal = numberLineIsHorizontal;
        feedbackRenderer = new FeedbackRenderer(stage);
        shouldRenderClue = false;
        currentPriceTypeNr = worldController.getCurrentPriceType();
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
        //clearBlue();
        clearGray();
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


        if(worldController.isPlayerInactive() ){
            if (!feedbackRenderer.getManoImg().hasActions()) {
                feedbackRenderer.renderClue(worldController.isTooMuch());
                shouldRenderClue = true;
            }
        }else{
            // if we didn't notify yet
            if(shouldRenderClue){
                feedbackRenderer.stopRenderClue();
                shouldRenderClue = false;
            }
        }

    }



    protected void renderCounter(SpriteBatch batch){
        // TODO

        float rotationNow = (worldController.getCountdownCurrentTime()*360)/Constants.COUNTDOWN_MAX;
       // Gdx.app.log(TAG, " === "+rotationNow);

        // we render countdown!
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.setColor(feedbackRenderer.getCountdownWheel().getColor());
        batch.draw(Assets.instance.feedback.wheel,
                feedbackRenderer.getCountdownWheel().getX(),feedbackRenderer.getCountdownWheel().getY(),
                feedbackRenderer.getCountdownWheel().getWidth()/2,feedbackRenderer.getCountdownWheel().getHeight()/2,
                feedbackRenderer.getCountdownWheel().getWidth(),feedbackRenderer.getCountdownWheel().getHeight(),
                feedbackRenderer.getCountdownWheel().getScaleX(),feedbackRenderer.getCountdownWheel().getScaleY(), rotationNow

                );
        batch.setColor(1,1,1,1);
//        String text = worldController.getCountdownCurrentTime()+"";
//        GlyphLayout layout = new GlyphLayout(counterFont, text);
//        counterFont.setColor(Color.RED);
//        counterFont.draw(batch, text+"", 0 - layout.width/2, -6 * Constants.BASE-100);
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
        switch(currentPriceTypeNr){
            case 1:
                batch.draw(Assets.instance.toCollect.price1,x,y,40,40);
                break;
            case 2:
                batch.draw(Assets.instance.toCollect.price2,x,y,40,40);
                break;
            case 3:
                batch.draw(Assets.instance.toCollect.price3,x,y,40,40);
                break;
            case 4:
                batch.draw(Assets.instance.toCollect.price4,x,y,40,40);
                break;
        }

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
