package ceta.game.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ewe on 1/4/17.
 */


public class ResultsManager {
    public static final String TAG = ResultsManager.class.getName();

    private Date priceAppeardDate;
    private Date priceCollectedDate;
    private int intentsNr;
    private byte collected; // 1=yes, 0=no
    private int priceValue;
    private int priceNumber;
    private int levelNumber;
    private FileHandle file;
    private  SimpleDateFormat sdf;
    private  SimpleDateFormat justTime;

    // singleton: prevent instantiation from other classes
    public ResultsManager() {
        sdf = new SimpleDateFormat("MMM dd yyyy,HH:mm:ss.SSS");
        justTime = new SimpleDateFormat("HH:mm:ss.SSS");
        file = Gdx.files.local("results.csv");
    }



    public void newPriceAppeard(int priceNr, int levelNr){
        priceAppeardDate = new Date();
        priceCollectedDate = new Date(2000,12,12);
        intentsNr = 0;
        collected = 0;
        priceNumber = priceNr;
        levelNumber = levelNr;
    }

    public void addIntent(){
        Gdx.app.log(TAG," addIntent +1 to "+intentsNr);
        intentsNr+=1;
    }

    public void priceCollected(int priceVal){
        priceValue = priceVal;
        priceCollectedDate = new Date();
        saveData();
    }

    private void saveData(){
        // write data to file
        // level, price Nr, price val, intents nr, time appeared, time collected
        String toSave = levelNumber+","+priceNumber+","+priceValue+","+intentsNr+","+sdf.format(priceAppeardDate)+","+justTime.format(priceCollectedDate)+"\n";
        Gdx.app.log(TAG,"save data "+toSave);
        file.writeString(toSave,true);

    }
}