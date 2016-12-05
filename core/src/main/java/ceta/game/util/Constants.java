package ceta.game.util;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

/**
 * Created by ewe on 7/25/16.
 */
public class Constants {
    public static final boolean WITH_CV = false;

    // Visible game world is 600 meters wide
    public static final float VIEWPORT_WIDTH = 600.0f;
    public static final float VIEWPORT_HEIGHT = 1024.0f;

    public static final String PREFERENCES = "settings.prefs";
    public static final String TEXTURE_ATLAS_OBJECTS = "images/pack.atlas";
    public static final String SKIN_UI = "visui/uiskin.json";

    public static final String LEVELS_FOLDER = "levels";

    public static final int BASE = 40;


    public static int PRICE_Y_HORIZONTAL; // where price will be if number line horizontal

    public static final int ACTION_SUBMIT_WAIT = 2000;
    public static final int INACTIVITY_LIMIT = 10000;
    public static final float TIME_DELAY_SCREEN_FINISHED = 0.5f;


    public static int DETECTION_ZONE_END ;
    public static int DETECTION_LIMIT; // just for tablet-only

    public static final int HORIZONTAL_ZERO_X = -200;
    public static final int VERTICAL_MIDDLE_X = -240;


    public static final int CV_DETECTION_EDGE_TABLET = 360;
    public static final int CV_MAX_X = 480;
    public static final int CV_MIN_Y = 640 - CV_MAX_X; // in camera we check 480x480, warning! image is rotated so CV_MIN_Y should be compared with X!

    public Constants(){
        if(WITH_CV) {
            DETECTION_ZONE_END = (int) -VIEWPORT_HEIGHT / 2 + CV_DETECTION_EDGE_TABLET;
            PRICE_Y_HORIZONTAL = DETECTION_ZONE_END + 5*BASE;
        }
        else {


            DETECTION_LIMIT = (int)-VIEWPORT_HEIGHT/2 + 160;
            DETECTION_ZONE_END = DETECTION_LIMIT + 200;
            PRICE_Y_HORIZONTAL = DETECTION_ZONE_END + 5*BASE;


        }

    }


}
