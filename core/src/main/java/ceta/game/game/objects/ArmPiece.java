package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.managers.RoboticArmManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 8/23/16.
 */
public class ArmPiece extends AbstractGameObject {
    protected short armValue;
    protected short id;

    protected RoboticArmManager armsManager;


    public ArmPiece(short val, RoboticArmManager armsMan){
        armValue = val;
        armsManager = armsMan;

         // will be changed when we have more parts

        init();
        setColorAndTexture(val);
    }

    public void init(){
        this.setSize(Constants.BASE*armValue,Constants.BASE);
        // now we can set the values that depend on size
        super.init();
    }

    protected void setColorAndTexture(short armVal){
        switch (armVal){
            case 1:
                setColor(Color.YELLOW);
                regTex = Assets.instance.roboticParts.copperFitting1;
                break;
            case 2:
                setColor(Color.CYAN);
                regTex = Assets.instance.roboticParts.copperFitting2;
                break;
            case 3:
                setColor(Color.ORANGE);
                regTex = Assets.instance.roboticParts.copperFitting3;
                break;
            case 4:
                setColor(Color.VIOLET);
                regTex = Assets.instance.roboticParts.copperFitting4;
                break;
            case 5:
                setColor(Color.GREEN);
                regTex = Assets.instance.roboticParts.copperFitting5;
                break;
        }
    }

    public short getId() {
        return id;
    }

    public void setId(short idVal){
        Gdx.app.log(TAG, "we set the id "+idVal);
        id = idVal;
    }



    public short getArmValue(){
        return armValue;
    }

    public void moveMeToAndSetTerminalX(float x, float y){
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x,y);
        moveToAction.setDuration(1f);
        setTerminalX(x);

        armsManager.addToInMovementIds(id);

        addAction(sequence(moveToAction,
                run(new Runnable() {
                    public void run() {
                        armsManager.notificationArmMoved(id);
                    }
                })
        ));
    }

    public void goBackAndRemove(){
        armsManager.addToInMovementIds(id);
        addAction(sequence(parallel(Actions.moveTo(getX()-getWidth(),getY(),1f),Actions.alpha(0,1f)),run(new Runnable() {
            public void run() {
                armsManager.notificationArmGone(id);
                remove();

            }
        })));
    }



    public void disappearAndRemove(){
        armsManager.addToInMovementIds(id);

        addAction(sequence(Actions.alpha(0,1f),run(new Runnable() {
            public void run() {
                armsManager.notificationArmGone(id);
                remove();
            }
        })));
    }


}

