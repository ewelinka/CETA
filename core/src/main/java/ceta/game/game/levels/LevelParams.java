package ceta.game.game.levels;

import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by ewe on 10/17/16.
 */
public class LevelParams {
    public int level;
    public int operationsNumberToPassToNext; // we have to collect at least X to pass
    public int operationsToFinishLevel; // operations that should APPEAR before level finish
    public int[] operations;
    public int numberMin;
    public int numberMax;
    public String numberOrOperation;
    public int priceReturn; // will define if dynamic or not
    public int priceVelocity;
    public boolean visibleNumberLine;

}
