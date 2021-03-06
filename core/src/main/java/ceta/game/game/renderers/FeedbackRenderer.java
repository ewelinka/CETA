package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.game.objects.VirtualBlock;
import ceta.game.util.AudioManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 11/30/16.
 */
public class FeedbackRenderer {
    public static final String TAG = FeedbackRenderer.class.getName();
    private float alphaColor;
    private boolean fadeIn;
    private Stage stage;
    private Image manoImg;
    private int feedbackMiddlePoint;

    private Image countdownWheel;

    public FeedbackRenderer(Stage stage){
        this.stage = stage;
        alphaColor = 0;
        fadeIn = true;
        manoImg = new Image(Assets.instance.feedback.hand);
        countdownWheel = new Image(Assets.instance.feedback.wheel);
        feedbackMiddlePoint = Constants.DETECTION_ZONE_END - (Constants.DETECTION_ZONE_END - Constants.DETECTION_LIMIT)/2 ;
        init();

    }

    public void init(){
        manoImg.setColor(1,1,1,0);
        manoImg.setSize(manoImg.getWidth(),manoImg.getHeight());
        manoImg.setOrigin(manoImg.getWidth() / 2, manoImg.getHeight() / 2);
        stage.addActor(manoImg);

        countdownWheel.setScale(0.5f);
        countdownWheel.setColor(130/255f,229/255f,225/255f,0.95f);
        //countdownWheel.setPosition(0-countdownWheel.getWidth()/2,Constants.DETECTION_ZONE_END- countdownWheel.getHeight());

        Gdx.app.log(TAG, " end "+ Constants.DETECTION_ZONE_END + "menos "+(Constants.DETECTION_ZONE_END - Constants.DETECTION_LIMIT)/2);
        Gdx.app.log(TAG, "limit detection "+Constants.DETECTION_LIMIT);

        countdownWheel.setPosition(0-countdownWheel.getWidth()/2,feedbackMiddlePoint - countdownWheel.getHeight()/2);
    }

    public void renderClue(boolean isTooMuch){ // we already checked if mano has actions in world renderer
        Gdx.app.log(TAG,"renderClue");
        if (isTooMuch)
            renderAndReadTooMuchClue();
        else
            renderAndReadTooFewClue();

    }

    public void stopRenderClue(){
        Gdx.app.log(TAG,"stopRenderClue");
        manoImg.clearActions();
        manoImg.addAction(sequence(
                alpha(0,0.1f),
                moveTo(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth()/2,-Constants.VIEWPORT_HEIGHT/2-manoImg.getHeight())
        ));
    }

    private void renderAndReadTooMuchClue(){
        AudioManager.instance.playWithoutInterruptionLoud(Assets.instance.sounds.tooMuch);
        renderTooMuchClue();
    }

    public void renderTooMuchClue(){
        manoImg.toFront();
        manoImg.setColor(1,1,1,0);
        manoImg.setPosition(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth()/2,feedbackMiddlePoint - manoImg.getHeight()+50);
        manoImg.addAction(sequence(
                alpha(1,0.3f),
                moveTo(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth(),-Constants.VIEWPORT_HEIGHT/2-manoImg.getHeight(),2.0f),
                delay(5.0f)
        ));

    }


    public void renderCleanTableClue(){
        manoImg.toFront();
        manoImg.setColor(1,1,1,0);
        manoImg.setPosition(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth()/2,feedbackMiddlePoint - manoImg.getHeight()+50);
        manoImg.addAction(sequence(
                delay(2.0f),
                alpha(1,0.3f),
                moveTo(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth(),-Constants.VIEWPORT_HEIGHT/2-manoImg.getHeight(),2.0f),
                delay(5.0f)
        ));

    }


    private void renderAndReadTooFewClue(){
        AudioManager.instance.playWithoutInterruptionLoud(Assets.instance.sounds.tooFew);
        renderTooFewClue();
    }

    private void renderTooFewClue(){
        manoImg.toFront();
        manoImg.setColor(1,1,1,1);
        manoImg.setPosition(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth(),-Constants.VIEWPORT_HEIGHT/2-manoImg.getHeight());
        manoImg.addAction(sequence(
                moveTo(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth()/2,feedbackMiddlePoint - manoImg.getHeight()+50,2.0f),
                alpha(0,0.3f),
                moveTo(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth(),-Constants.VIEWPORT_HEIGHT/2-manoImg.getHeight()), //out!
                delay(5.0f)
        ));

    }




    public void renderColorChange(ShapeRenderer shRenderer){
        if(fadeIn){
            alphaColor+=0.01;
        }
        else
            alphaColor-=0.01;

        if(alphaColor > 0.8){
            fadeIn=!fadeIn;
        }
        if(alphaColor< 0)
            fadeIn=!fadeIn;

        shRenderer.setColor(255,255,77,alphaColor);

        shRenderer.rect(-Constants.CV_DETECTION_EDGE_TABLET/2, -Constants.VIEWPORT_HEIGHT/2,Constants.CV_DETECTION_EDGE_TABLET, Constants.CV_DETECTION_EDGE_TABLET);

    }

    public void resetColorChange(){
        alphaColor = 0;
        fadeIn = true;
    }

    public int getFeedbackMiddlePoint(){return feedbackMiddlePoint;}

    public Image getManoImg(){
        return manoImg;
    }

    public Image getCountdownWheel(){ return countdownWheel;}
}
