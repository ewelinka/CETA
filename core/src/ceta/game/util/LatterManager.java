package ceta.game.util;

import ceta.game.game.objects.Latter;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by ewe on 8/1/16.
 */
public class LatterManager {
    private HashMap<Integer, Integer> detected_numbers;
    private  HashMap<Integer, Integer> previous_detected;

    private ArrayList<Latter> latters;

    public LatterManager(){
        init();
    }

    private void init(){
        latters = new ArrayList<Latter>();
        detected_numbers = new HashMap<Integer, Integer>();
        detected_numbers.put(1,0);
        detected_numbers.put(2,0);
        previous_detected = new HashMap<Integer, Integer>();
        previous_detected.put(1,0);
        previous_detected.put(2,0);
    }

    private void updateDetected(){

    }

    private void findDifferences(){

    }

}
