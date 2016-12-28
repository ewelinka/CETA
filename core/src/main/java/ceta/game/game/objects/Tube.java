package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;



/**
 * Created by ewe on 8/8/16.
 */
public class Tube extends AbstractGameObject {
    private int tubeValue;

    public Tube(int val){
        tubeValue =val;
        regTex = Assets.instance.roboticParts.tubeVertical;

        init();
    }

    public void init(){
        this.setSize(regTex.getRegionWidth()*1.2f,200);
        // now we can set the values that depend on size
        super.init();

    }

}
