package ceta.game.util;


/**
 * Created by ewe on 7/25/16.
 */
public class Constants {
    public static final boolean WITH_CV = false;

    public static final int MARGIN_FADE  = 100;
    public static final int COUNTDOWN_MAX = 1; // in seconds

    // Visible game world is 600 meters wide
    public static final float VIEWPORT_WIDTH = 600.0f;
    public static final float VIEWPORT_HEIGHT = 1024.0f;

    public static final String PREFERENCES = "settings.prefs";
    public static final String TEXTURE_ATLAS_OBJECTS = "images/pack.atlas";
    public static final String SKIN_UI = "visui/uiskin.json";

    public static final String LEVELS_FOLDER = "levels";
    public static final float AUTOMATIC_LEVEL_PASS = 5.0f;

    public static final int BASE = 40;
    public static final int ERRORS_FOR_HINT = 2;

    public static final int L1_COMPLETED_NR = 4; // 7 = last level, 8 first level in L2
    public static final int L2_COMPLETED_NR = 8;
    public static final int L3_COMPLETED_NR = 12;
    public static final int L4_COMPLETED_NR = 17;
    public static final int L5_COMPLETED_NR = 22;
    public static final int L6_COMPLETED_NR = 26;
    public static final int LAST_LEVEL_NR = L6_COMPLETED_NR;

    public static final int L1_HALF_COMPLETED_NR = L1_COMPLETED_NR-2;
    public static final int L2_HALF_COMPLETED_NR = L2_COMPLETED_NR-2;
    public static final int L3_HALF_COMPLETED_NR = L3_COMPLETED_NR-2;
    public static final int L4_HALF_COMPLETED_NR = L4_COMPLETED_NR-2;
    public static final int L5_HALF_COMPLETED_NR = L5_COMPLETED_NR-2;
    public static final int L6_HALF_COMPLETED_NR = L6_COMPLETED_NR-2;

    public static final int NO_MOVEMENT_DIST = 25;




    public static int PRICE_Y_HORIZONTAL; // where price will be if number line horizontal
    public static int DRAW_START;

    public static final int ACTION_SUBMIT_WAIT = 1500;
    public static final int INACTIVITY_LIMIT = 7000;
    public static final float TIME_DELAY_SCREEN_FINISHED = 1.0f;


    public static int DETECTION_ZONE_END ;
    public static int DETECTION_LIMIT; // just for tablet-only
    public static int GROUND_LEVEL;

    public static final int HORIZONTAL_ZERO_X = -160;
    public static final int VERTICAL_MIDDLE_X = -233;
    public static final int PRICE_X_OFFSET = 60;


    public static final int CV_DETECTION_EDGE_TABLET = 360;
    public static final int CV_MAX_X = 480;
    public static final int CV_MIN_Y = 640 - CV_MAX_X; // in camera we check 480x480, warning! image is rotated so CV_MIN_Y should be compared with X!

    public Constants(){
        if(WITH_CV) {
            DETECTION_ZONE_END = (int) -VIEWPORT_HEIGHT / 2 + CV_DETECTION_EDGE_TABLET;
            DETECTION_LIMIT = (int)-VIEWPORT_HEIGHT/2;
        }
        else {

            DETECTION_LIMIT = (int)-VIEWPORT_HEIGHT/2 + 160;
            DETECTION_ZONE_END = DETECTION_LIMIT + 200;



        }
        GROUND_LEVEL = DETECTION_ZONE_END + 20;
        DRAW_START = DETECTION_ZONE_END + 10;
        PRICE_Y_HORIZONTAL = DETECTION_ZONE_END + 6*BASE;

    }


}
