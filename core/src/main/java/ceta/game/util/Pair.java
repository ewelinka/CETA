package ceta.game.util;

/**
 * Created by ewe on 10/25/16.
 */
public class Pair {
    private short key;
    private short value;
    public Pair (short k, short v) {
        this.key = k;
        this.value = v;
    }
    public short getKey(){ return key; }

    public short getValue(){ return value;}


}
