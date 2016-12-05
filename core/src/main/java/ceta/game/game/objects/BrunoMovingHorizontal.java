package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;

/**
 * Created by ewe on 12/2/16.
 */
public class BrunoMovingHorizontal extends Bruno {

    @Override
    public void init () {
        regTex = Assets.instance.bruno.body05;
        this.setSize(Constants.BASE*1,Constants.BASE*5.5f);
        superinit();
        lookingLeft = false;
        float xZero = Constants.HORIZONTAL_ZERO_X-getWidth()/2;
        setPosition(xZero,Constants.DETECTION_ZONE_END);
        setTerminalX(xZero);

    }
}
