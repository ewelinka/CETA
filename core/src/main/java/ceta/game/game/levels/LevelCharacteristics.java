package ceta.game.game.levels;

import com.badlogic.gdx.Gdx;

/**
 * Created by ewe on 2/16/17.
 */
public class LevelCharacteristics {
    private static final String TAG = LevelCharacteristics.class.getName();

    public int island;
    public int representation;
    public boolean isHorizontal;
    public int backgroundNr;

    public void printLevelCharacteristics(){
        Gdx.app.log(TAG, "island "+island + "rep "+representation+" isH "+isHorizontal+" backgroundNr "+backgroundNr);
    }
}
