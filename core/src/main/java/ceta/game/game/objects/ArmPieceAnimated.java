package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.managers.AnimatedRoboticArmManager;
import ceta.game.managers.RoboticArmManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 10/27/16.
 */
public class ArmPieceAnimated extends ArmPiece{
    private Animation animationIn;
    private Animation animationOut;
    private Animation animationOn;
    private float stateTime;



    public ArmPieceAnimated(short val, AnimatedRoboticArmManager armsMan) {
        // by default 1
        super((short)1,armsMan);
        setOrigin(0, this.getHeight()/ 2);
        setScale(0,1);

    }

//    @Override
//    public void init(){
//        Gdx.app.log(TAG, " --- init ---");
//
//    }

    @Override
    protected void setColorAndTexture(short val){
        setColor(Color.YELLOW);
        regTex = Assets.instance.roboticParts.copperFitting1;

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
