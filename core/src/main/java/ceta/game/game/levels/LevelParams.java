package ceta.game.game.levels;

/**
 * Created by ewe on 10/17/16.
 */
public class LevelParams {
    public int level;
    public int operationsToFinishLevel; // operations that should APPEAR before level finish
    public int[] operations;
    public int numberMin;
    public int numberMax;
    public int priceReturn; // will define if dynamic or not
    public int priceVelocity;
    public int islandNr; // options: 1,2,3,4,5,6
    public boolean visibleNumberLine;

}
