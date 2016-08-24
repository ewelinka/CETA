package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;

/**
 * Created by ewe on 8/23/16.
 */
public class ArmPiece extends AbstractGameObject {
    private short armValue;

    public ArmPiece(short val){
        armValue = val;
        switch(armValue){
            case 1:
                regTex = Assets.instance.latter.latter;

                break;
            case 2:
                regTex = Assets.instance.latter.latter;

                break;
            default:
                regTex = Assets.instance.latter.latter;
                break;
        }

        init();
    }

    public void init(){

        this.setSize(Constants.BASE*armValue,Constants.BASE);
        Gdx.app.log(TAG,this.getWidth()+" "+this.getHeight()+" "+armValue);
        // now we can set the values that depend on size
        super.init();



    }


    public short getArmValue(){
        return armValue;
    }


}

