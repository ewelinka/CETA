package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;



/**
 * Created by ewe on 8/8/16.
 */
public class Tube extends AbstractGameObject {

    public Tube(){
        regTex = Assets.instance.roboticParts.tubeVertical;
        init();
    }

    public void init(){
        this.setSize(regTex.getRegionWidth(),regTex.getRegionHeight());
        super.init();

    }

}
