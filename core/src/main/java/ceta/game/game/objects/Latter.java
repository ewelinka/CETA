package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;



/**
 * Created by ewe on 8/8/16.
 */
public class Latter extends AbstractGameObject {
    private short latterValue;
    boolean blocked = false;

    public Latter(short val){
        latterValue =val;
        switch(latterValue){
            case 1:
                regTex = Assets.instance.latter.latter;
                this.setColor(1,0,0,1);
                break;
            case 2:
                regTex = Assets.instance.latterDouble.latter;
                this.setColor(1,1,0,1);
                break;
            default:
                regTex = Assets.instance.box.box;
                break;
        }

        init();
    }

    public void init(){

        this.setSize(Constants.BASE,Constants.BASE*latterValue);
        // now we can set the values that depend on size
        super.init();



    }


    public short getLatterValue(){
        return latterValue;
    }


}
