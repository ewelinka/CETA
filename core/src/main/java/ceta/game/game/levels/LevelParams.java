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
    public String type;
    public int operationsNumber;
    public int numberMin;
    public int numberMax;
    public String numberOrOperation;
    public boolean isDynamic;
    public int priceReturn;
    public int priceVelocity;
    public boolean visibleNumberLine;

}