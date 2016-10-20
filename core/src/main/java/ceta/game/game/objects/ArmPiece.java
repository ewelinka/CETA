package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.util.RoboticArmManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 8/23/16.
 */
public class ArmPiece extends AbstractGameObject {
    private short armValue;
    private short id;
    private float terminalX;
    private RoboticArmManager armsManager;

    public ArmPiece(short val, RoboticArmManager armsMan){
        armValue = val;
        armsManager = armsMan;

        switch (armValue){
            case 1:
                setColor(Color.LIME);
                regTex = Assets.instance.roboticParts.copperFitting1;
                break;
            case 2:
                setColor(Color.RED);
                regTex = Assets.instance.roboticParts.copperFitting2;
                break;
            case 3:
                setColor(Color.GREEN);
                regTex = Assets.instance.roboticParts.copperFitting3;
                break;
            case 4:
                setColor(Color.ORANGE);
                regTex = Assets.instance.roboticParts.copperFitting4;
                break;
            case 5:
                setColor(Color.CYAN);
                regTex = Assets.instance.roboticParts.copperFitting5;
                break;
        }
         // will be changed when we have more parts

        init();
    }

    public void init(){

        this.setSize(Constants.BASE*armValue,Constants.BASE);
        // now we can set the values that depend on size
        super.init();
    }

    public short getId() {
        return id;
    }

    public void setId(short idVal){
        id = idVal;
    }

    public float getTerminalX(){
        return terminalX;
    }

    public void setTerminalX(float newTerminalX){
        terminalX = newTerminalX;
    }

    public short getArmValue(){
        return armValue;
    }

    public void moveMeToAndSetTerminalX(float x, float y){
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x,y);
        moveToAction.setDuration(1f);

        setTerminalX(x);

        addAction(sequence(moveToAction,
                run(new Runnable() {
                    public void run() {
                        armsManager.notificationArmMoved(id);
                    }
                })
        ));
    }

    public void goBackAndRemove(){
        addAction(sequence(parallel(Actions.moveTo(getX()-getWidth(),getY(),1f),Actions.alpha(0,1f)),run(new Runnable() {
            public void run() {
                armsManager.notificationArmGone(id);
                remove();

            }
        })));
    }


}

