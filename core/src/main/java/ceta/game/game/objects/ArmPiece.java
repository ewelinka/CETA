package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

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
                break;
            case 2:
                setColor(Color.RED);
                break;
            case 3:
                setColor(Color.GREEN);
                break;
            case 4:
                setColor(Color.ORANGE);
                break;
            case 5:
                setColor(Color.CYAN);
                break;
        }
        regTex = Assets.instance.box.box; // will be changed when we have more parts

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


}

