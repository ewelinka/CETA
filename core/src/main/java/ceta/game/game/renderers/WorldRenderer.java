package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.controllers.AbstractWorldController;
import ceta.game.game.objects.VirtualBlock;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;

/**
 * Created by ewe on 7/25/16.
 */
public class WorldRenderer extends AbstractWorldRenderer {
    public static final String TAG = WorldRenderer.class.getName();
    //protected FeedbackRenderer feedbackRenderer;
    protected int currentPriceTypeNr;
    private boolean isPlayingCleanTable;

//    public WorldRenderer(AbstractWorldController worldController, Stage stage) {
//        this(worldController,stage,true); //default horizontal number line
//
//    }

    public WorldRenderer(AbstractWorldController worldController, Stage stage,TextureAtlas.AtlasRegion imgBackground) {
        this(worldController,stage,true,imgBackground); //default horizontal number line

    }

//    public WorldRenderer(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal) {
//        this(worldController,stage,numberLineIsHorizontal, Assets.instance.staticBackground.tubes5);
//    }



    public WorldRenderer(AbstractWorldController worldController, Stage stage, boolean numberLineIsHorizontal,TextureAtlas.AtlasRegion imgBackground) {
        this.stage = stage;
        this.worldController = worldController;
        this.numberLineIsHorizontal = numberLineIsHorizontal;
        this.imgBackground = imgBackground;

        feedbackRenderer = new FeedbackRenderer(stage);
        shouldRenderClue = false;
        currentPriceTypeNr = worldController.getCurrentPriceType();
        isPlayingCleanTable = false;
        maxShift = worldController.getMaximumNumber() - worldController.getMinimumNumber();
        init();
    }



    public void init () {
        levelMinimumNumber = worldController.getMinimumNumber();

        spriteBatch = new SpriteBatch();
        fontNumberLine = Assets.instance.fonts.defaultNumberLine;
        normalGuiFont = Assets.instance.fonts.defaultNormal;
        smallGuiFont = Assets.instance.fonts.defaultSmall;
        bigGuiFont =  Assets.instance.fonts.defaultBig;
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.setToOrtho(false,Constants.VIEWPORT_WIDTH,Constants.VIEWPORT_HEIGHT);
        camera.position.set(0,0,0);
        camera.update();
        stage.getViewport().setCamera(camera);

    }


    public void render (float delta) {
      //  clearWhite();
        //clearBlue();
        clearGray();
        renderBelowTheGround(spriteBatch);
        renderDetectionZoneImgOrDebug(spriteBatch);
        renderBackgroundImg(spriteBatch);



        if(worldController.isNumberLineVisible())
            renderHelperNumberLines(shapeRenderer);

        renderBrunoPositionLine(shapeRenderer);
        renderWorld(spriteBatch);
        renderNumberLineImg(spriteBatch);
        renderHelperNumbers(spriteBatch,delta);
        renderFeedback();
//        renderDebugNumbersVertical(spriteBatch);
//        renderDebugNumbersHorizontal(spriteBatch);




    }

    protected void renderFeedback(){
       // renderWorld(spriteBatch);
        if (worldController.getCountdownOn()) {
            if(GamePreferences.instance.actionSubmit)
                renderCounter(spriteBatch);
        }
        //renderPriceValue(spriteBatch);
        renderGui(spriteBatch);

       // Gdx.app.log(TAG," wasTableCleaned "+ worldController.wasTableCleaned()+" isPlayingCleanTable "+isPlayingCleanTable);

        if(!worldController.wasTableCleaned() && !isPlayingCleanTable ){
            //renderOldBlocks();
            AudioManager.instance.playWithoutInterruptionLoud(Assets.instance.sounds.cleanTable);
            feedbackRenderer.renderTooMuchClue();
            isPlayingCleanTable = true;
            Timer.schedule(new Timer.Task(){
                @Override
                public void run() {
                    isPlayingCleanTable = false;
                }
            }, 10);
        }else { // if its not cleaning table problem, we give hint
            if (worldController.isPlayerInactive()) {
                if (!feedbackRenderer.getManoImg().hasActions()) {
                    feedbackRenderer.renderClue(worldController.isTooMuch());
                    shouldRenderClue = true;
                }
            } else {
                // if we didn't notify yet
                if (shouldRenderClue) {
                    feedbackRenderer.stopRenderClue();
                    shouldRenderClue = false;
                }
            }
        }



    }
    private void renderOldBlocks(ArrayList<VirtualBlock> oldblocks){
        for(int i = 0; i < oldblocks.size();i++){
            oldblocks.get(i).setColor(Color.RED);
            oldblocks.get(i).draw(spriteBatch,1);
        }
    }



    protected void renderCounter(SpriteBatch batch){
        float rotationNow = (worldController.getCountdownCurrentTime()*360)/Constants.COUNTDOWN_MAX;
       // Gdx.app.log(TAG, " === "+rotationNow);

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

        String text = worldController.getNowDetectedSumWithStartNumberLineValue()+"";
        GlyphLayout layout = new GlyphLayout(bigGuiFont, text);
//        counterFont.setColor(Color.RED);
        bigGuiFont.draw(batch, text, 0 , feedbackRenderer.getFeedbackMiddlePoint()+layout.height/2,0,Align.center,false);
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

//    protected void renderPriceValue(SpriteBatch batch){
//        batch.setProjectionMatrix(camera.combined);
//        batch.begin();
//        fontNumberLine.setColor(Color.RED);
//        fontNumberLine.draw(batch, worldController.level.price.getDisplayNumber()+"",
//                worldController.level.price.getX()+worldController.level.price.getWidth()/2,
//                worldController.level.price.getY()+worldController.level.price.getHeight()+10,
//                0,Align.center,false);
//        fontNumberLine.setColor(Color.WHITE);
//        batch.end();
//    }

    protected void renderGuiScore (SpriteBatch batch) {
        float x =  -Constants.VIEWPORT_WIDTH/2 + 15;
        float y = Constants.VIEWPORT_HEIGHT/2 - 50;


        batch.draw(Assets.instance.background.guiGearL,
                -Constants.VIEWPORT_WIDTH/2-34,Constants.VIEWPORT_HEIGHT/2-90*1.2f,
                184*1.2f,188*1.2f);

        batch.draw(Assets.instance.background.guiGearR,
                Constants.VIEWPORT_WIDTH/2-190,Constants.VIEWPORT_HEIGHT/2-85,
                269,259);

        renderLevelNumber(batch);

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
            case 5:
                batch.draw(Assets.instance.toCollect.price5,x,y,40,40);
                break;
            case 6:
                batch.draw(Assets.instance.toCollect.price6,x,y,40,40);
                break;
        }

        normalGuiFont.draw(batch, worldController.score+"/"+worldController.getOperationsNumberToFinishLevel(),x+50,y+30);
        // total score
        batch.draw(Assets.instance.feedback.prices,x+475,y,40,40);
        smallGuiFont.draw(batch, GamePreferences.instance.totalScore+"",x+525,y+27);
    }




}
