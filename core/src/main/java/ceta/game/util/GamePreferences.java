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
    public String randomId;

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

        prefs.putString("randomId", randomId);
        prefs.flush();
        // TODO now start in 0 always, just for testing than remove!!
        lastLevel = 0;

    }

    public void save () {
        prefs.putBoolean("actionSubmit", actionSubmit);
        prefs.putFloat("virtualBlocksAlpha", virtualBlocksAlpha);
        prefs.putFloat("countdownMax", countdownMax);
        prefs.putInteger("lastLevel",lastLevel);
        prefs.putInteger("totalScore",totalScore);
        prefs.putString("randomId", randomId);
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

}
