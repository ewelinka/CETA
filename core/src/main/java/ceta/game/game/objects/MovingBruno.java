package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;

/**
 * Created by ewe on 11/4/16.
 */
public class MovingBruno extends Bruno {

    public void init () {
        regTex = Assets.instance.bruno.body;
        this.setSize(Constants.BASE*1,Constants.BASE*4);
        super.init();

    }


}
