package ceta.game.game.objects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import ceta.game.game.Assets;
import ceta.game.managers.AnimatedRoboticTubeManager;

/**
 * Created by ewe on 10/27/16.
 */
public class TubePieceAnimated extends ArmPiece{
    public static final String TAG = TubePieceAnimated.class.getName();
//    private Animation animationIn;
//    private Animation animationOut;
//    private Animation animationOn;
//    private float stateTime;



    public TubePieceAnimated(int val, AnimatedRoboticTubeManager armsMan) {
        // by default 1
        super(1,armsMan);
        setOrigin(0, this.getHeight()/ 2);
        setScale(0,1);

    }

//    @Override
//    public void init(){
//        Gdx.app.log(TAG, " --- init ---");
//
//    }

    @Override
    protected void setColorAndTexture(int val){
        //setColor(Color.YELLOW);
        regTex = Assets.instance.roboticParts.tubeUnit;

    }


    public void expandMe(float delay, float speed){
        armsManager.addToInMovementIds(id);
        addAction(sequence(
                Actions.delay(delay),
                Actions.scaleTo(1.0f,1.0f,speed),
                run(new Runnable() {
                    public void run() {
                        armsManager.notificationArmMoved(id);
                    }
                })
        ));
    }

    public void collapseMe(float delay, float speed){
        armsManager.addToInMovementIds(id);

        addAction(sequence(
                Actions.delay(delay),
                Actions.scaleTo(0f,1.0f,speed),
                run(new Runnable() {
                    public void run() {
                        armsManager.notificationArmGone(id);
                        remove();
                    }
                })
        ));


    }

}
