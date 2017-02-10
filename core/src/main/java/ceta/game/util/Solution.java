package ceta.game.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by ewe on 1/21/17.
 */
public class Solution {
    private Vector2 pos;
    private int id;

    public Solution(float x, float y, int id){
        pos= new Vector2(x,y);
        this.id = id;
    }

    public Vector2 getPosition(){
        return pos;
    }

    public int getId(){
        return id;
    }
}
