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
        setScale(0);

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


    public void expandMe(short delay){
        //armsManager.addToInMovementIds(id);
       // ((AnimatedRoboticArmManager)armsManager).addToInExpansionIds(id);
        addAction(sequence(
                //Actions.scaleTo(0.0f,1),
                Actions.delay(delay),
                Actions.scaleTo(1.0f,1.0f,0.5f)
//                run(new Runnable() {
//                    public void run() {
//                        ((AnimatedRoboticArmManager)armsManager).notificationArmExpanded(id);
//                    }
//                })
        ));
    }

    public void collapseMe(short delay){
        armsManager.addToInMovementIds(id);

        addAction(sequence(
                Actions.delay(delay),
                Actions.scaleTo(0.01f,0f,0.5f),
                run(new Runnable() {
                    public void run() {
                        armsManager.notificationArmGone(id);
                        remove();
                    }
                })
        ));


    }

}
