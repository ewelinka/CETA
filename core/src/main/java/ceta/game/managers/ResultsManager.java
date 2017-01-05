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

    private Date intentStartDate;
    private Date intentEndDate;
    private int intentsNr;
    private int priceValue;
    private int priceNumber;
    private int response;
    private int levelNumber;
    private FileHandle file;
    private  SimpleDateFormat sdf;
    private  SimpleDateFormat justTime;
    private long collectionTimeMillis;
    private String toSave;
    private byte successfulIntent; // 1=yes, 0=no

    public ResultsManager() {
        sdf = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss.SSS");
        justTime = new SimpleDateFormat("HH:mm:ss.SSS");
        file = Gdx.files.local("results.csv");
        Gdx.app.log(TAG, " AAAAAAA "+ Gdx.files.getLocalStoragePath()+ " available: "+Gdx.files.isLocalStorageAvailable());

        //String text = file.readString();
        //Gdx.app.log(TAG,text);
    }



    public void newPriceAppeared(int priceNr, int levelNr){
        intentStartDate = new Date();
        intentsNr = 0;
        priceNumber = priceNr;
        levelNumber = levelNr;
    }

    public void addIntent(boolean wasSuccessful, int kidResponse, int priceVal){
        Gdx.app.log(TAG," addIntent +1 to "+intentsNr);
        intentsNr+=1;
        intentEndDate = new Date();
        collectionTimeMillis = intentEndDate.getTime() - intentStartDate.getTime() ;

        if(wasSuccessful)
            successfulIntent = 1;
        else
            successfulIntent = 0;

        response = kidResponse;
        priceValue = priceVal;
        // level, price Nr, price val, response, intents nr, intenet result (1 or 0), time appeared, time collected, difference in millis
        toSave = levelNumber+","+priceNumber+","+priceValue+","+response+","+intentsNr+","+successfulIntent+","
                +sdf.format(intentStartDate)+","+justTime.format(intentEndDate)+","+collectionTimeMillis+"\n";
        Gdx.app.log(TAG,"save data "+toSave);
        file.writeString(toSave,true);

        // now we reset the start
        intentStartDate = intentEndDate;

    }

    public void resetIntentStart(){
        intentStartDate = new Date();
    }
//
//    public void priceCollected(int priceVal){
//        priceValue = priceVal;
//        intentEndDate = new Date();
//        collectionTimeMillis = intentStartDate.getTime() - intentEndDate.getTime();
//        saveData();
//    }

//    private void saveData(){
//        // write data to file
//        // level, price Nr, price val, response, intents nr, intenet result (1 or 0), time appeared, time collected, difference in millis
//         toSave = levelNumber+","+priceNumber+","+priceValue+","+response+"'"+intentsNr+","+successfulIntent+","
//                +sdf.format(intentStartDate)+","+justTime.format(intentEndDate)+","+collectionTimeMillis+"\n";
//        Gdx.app.log(TAG,"save data "+toSave);
//        file.writeString(toSave,true);
//
//    }
}