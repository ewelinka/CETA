package ceta.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * Created by ewe on 10/6/16.
 */
public class GamePreferences {
    public static final String TAG = GamePreferences.class.getName();
    public static final GamePreferences instance = new GamePreferences();
    private Preferences prefs;
    public boolean actionSubmit;
    public float countdownMax;
    public float virtualBlocksAlpha;
    public int lastLevel;
    public int totalScore;
    public float fvalue;
    public String randomId;
    public int repeatNr;
    public boolean useDebug;

    private GamePreferences () {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public void load () {
        actionSubmit = prefs.getBoolean("actionSubmit", true);
        virtualBlocksAlpha = MathUtils.clamp(prefs.getFloat("virtualBlocksAlpha", 1.0f), 0.0f, 1.0f);
        countdownMax = MathUtils.clamp(prefs.getFloat("countdownMax", 5), 0, 10);
        lastLevel = prefs.getInteger("lastLevel",0);
        totalScore = prefs.getInteger("totalScore",0);
        randomId = prefs.getString("randomId", randomUUID ().toString());
        repeatNr = prefs.getInteger("repeatNr",0);
        fvalue = prefs.getFloat("fvalue",0.85f); //default TopCode f value for adaptive thresholding algorithm
        useDebug = prefs.getBoolean("useDebug", false);
        prefs.putString("randomId", randomId);
        prefs.flush();
        // TODO now start in 0 always, just for testing than remove!!
        //lastLevel = 25;
        repeatNr = 0; // forever and ever 0, if we want to loop we should remove it!
    }

    public void save () {
        prefs.putBoolean("actionSubmit", actionSubmit);
        prefs.putFloat("virtualBlocksAlpha", virtualBlocksAlpha);
        prefs.putFloat("countdownMax", countdownMax);
        prefs.putInteger("lastLevel",lastLevel);
        prefs.putInteger("totalScore",totalScore);
        prefs.putString("randomId", randomId);
        prefs.putInteger("repeatNr", repeatNr);
        prefs.putFloat("fvalue", fvalue);
        prefs.putBoolean("useDebug", useDebug);
        prefs.flush();
    }

    public void addToTotalScore(int newScore){
        totalScore = totalScore+newScore;
        prefs.putInteger("totalScore", totalScore);
        prefs.flush();
    }

    public void setLastLevel(int newLastLevel){
        lastLevel=newLastLevel;
        prefs.putInteger("lastLevel", lastLevel);
        prefs.flush();
    }

    public void forceGlobalScore(int newGlobalScore){
        totalScore = newGlobalScore;
        prefs.putInteger("totalScore", totalScore);
        prefs.flush();
    }
    
    public void setFvalue(float newFvalue){
        fvalue = newFvalue;
        prefs.putFloat("fvalue", fvalue);
        prefs.flush();
    }

    public float getFvalue(){
        return fvalue;
    }

//    public void addOneRepeat(){
//        Gdx.app.log(TAG,"===== addOneRepeat ===== "+repeatNr);
//        repeatNr=repeatNr+1;
//        prefs.putInteger("repeatNr", repeatNr);
//        prefs.flush();
//    }
//
    public int getRepeatNr(){
        return repeatNr;
    }
//
//    public void forceRepeatNr(int newRepeatNr){
//        repeatNr = newRepeatNr;
//        prefs.putInteger("repeatNr", repeatNr);
//        prefs.flush();
//    }

    public boolean getUseDebug(){
        return useDebug;
    }

    public void setUseDebug(boolean useIt){
        useDebug = useIt;
        prefs.putBoolean("useDebug",useDebug);
        prefs.flush();
    }

}
