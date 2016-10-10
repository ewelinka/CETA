package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 8/23/16.
 */
public class ArmPiece extends AbstractGameObject {
    private short armValue;

    public ArmPiece(short val){
        armValue = val;
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

    public short getArmValue(){
        return armValue;
    }

    public void goBackAndRemove(){
        addAction(sequence(parallel(Actions.moveTo(getX()-getWidth(),getY(),1f),Actions.alpha(0,1f)),run(new Runnable() {
            public void run() {
                remove();
            }
        })));
    }


}

