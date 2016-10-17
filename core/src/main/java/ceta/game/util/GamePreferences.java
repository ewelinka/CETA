package ceta.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

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
    public float collectedToWin;
    public int lastLevel;

    private GamePreferences () {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public void load () {
        actionSubmit = prefs.getBoolean("actionSubmit", true);
        virtualBlocksAlpha = MathUtils.clamp(prefs.getFloat("virtualBlocksAlpha", 1.0f), 0.0f, 1.0f);
        countdownMax = MathUtils.clamp(prefs.getFloat("countdownMax", 5), 0, 10);
        collectedToWin = MathUtils.clamp(prefs.getFloat("collectedToWin", 5), 1, 10);
        lastLevel = prefs.getInteger("lastLevel",0);

    }

    public void save () {
        prefs.putBoolean("actionSubmit", actionSubmit);
        prefs.putFloat("virtualBlocksAlpha", virtualBlocksAlpha);
        prefs.putFloat("countdownMax", countdownMax);
        prefs.putFloat("collectedToWin",collectedToWin);
        prefs.flush();
    }
}
