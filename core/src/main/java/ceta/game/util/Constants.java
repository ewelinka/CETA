package ceta.game.util;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by ewe on 7/25/16.
 */
public class Constants {
    public static final boolean withCV = true;

    // Visible game world is 600 meters wide
    public static final float VIEWPORT_WIDTH = 600.0f;
    public static final float VIEWPORT_HEIGHT = 1024.0f;

    public static final String PREFERENCES = "settings.prefs";
    public static final String TEXTURE_ATLAS_OBJECTS = "images/pack.atlas";
    public static final String SKIN_UI = "visui/uiskin.json";

    public static final String LEVELS_FOLDER = "levels";

    public static final int BASE = 40;

    public static final int DETECTION_LIMIT = -6 * BASE; // just for tablet-only
    public static final int PRICE_Y_HORIZONTAL = 40; // where price will be if number line horizontal

    public static final int ACTION_SUBMIT_WAIT = 2000;
    public static final int INACTIVITY_LIMIT = 10000;


    public static int DETECTION_ZONE_END ;

    public static final int HORIZONTAL_ZERO_X = -200;
    public static final int VERTICAL_MIDDLE_X = -260;


    public static final int CV_DETECTION_EDGE_TABLET = 360;
    public static final int CV_MAX_X = 480;
    public static final int CV_MIN_Y = 640 - CV_MAX_X; // in camera we check 480x480, warning! image is rotated so CV_MIN_Y should be compared with X!

    public Constants(){
        if(withCV)
            DETECTION_ZONE_END  = (int)-VIEWPORT_HEIGHT/2+CV_DETECTION_EDGE_TABLET;
        else
            DETECTION_ZONE_END = 0;

    }


}
