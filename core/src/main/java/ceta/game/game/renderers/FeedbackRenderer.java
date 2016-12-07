package ceta.game.game.renderers;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

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

    public FeedbackRenderer(Stage stage){
        this.stage = stage;
        alphaColor = 0;
        fadeIn = true;
        manoImg = new Image(Assets.instance.feedback.mano);
        init();

    }

    public void init(){
        manoImg.setColor(1,1,1,0);
        manoImg.setSize(manoImg.getWidth()/4,manoImg.getHeight()/4);
        manoImg.setOrigin(manoImg.getWidth() / 2, manoImg.getHeight() / 2);
        stage.addActor(manoImg);
    }

    public void renderClue(){
        Gdx.app.log(TAG,"renderClue");
        manoImg.toFront();
        manoImg.setColor(1,1,1,1);
        manoImg.setPosition(-Constants.CV_DETECTION_EDGE_TABLET/2-manoImg.getWidth()/2,-Constants.VIEWPORT_HEIGHT/2-manoImg.getHeight());
        manoImg.addAction(sequence(
                moveTo(0-manoImg.getWidth()/2,-Constants.VIEWPORT_HEIGHT/2,2.0f),
                alpha(0,0.3f),
                delay(2.0f)
               // removeActor()
        ));


    }

    public void stopRenderClue(){
        Gdx.app.log(TAG,"stopRenderClue");
        manoImg.clearActions();
        manoImg.addAction(sequence(
                alpha(0,0.2f)
                // removeActor()
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

    public Image getManoImg(){
        return manoImg;
    }
}
