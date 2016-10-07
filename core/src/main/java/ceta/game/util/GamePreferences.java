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

    private GamePreferences () {
        prefs = Gdx.app.getPreferences(Constants.PREFERENCES);
    }

    public void load () {
        actionSubmit = prefs.getBoolean("actionSubmit", true);
        virtualBlocksAlpha = MathUtils.clamp(prefs.getFloat("virtualBlocksAlpha", 1.0f), 0.0f, 1.0f);
        countdownMax = MathUtils.clamp(prefs.getFloat("countdownMax", 5), 0, 10);

    }

    public void save () {
        prefs.putBoolean("actionSubmit", actionSubmit);
        prefs.putFloat("virtualBlocksAlpha", virtualBlocksAlpha);
        prefs.putFloat("countdownMax", countdownMax);
        prefs.flush();
    }
}
