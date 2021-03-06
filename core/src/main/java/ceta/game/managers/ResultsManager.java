package ceta.game.managers;

import ceta.game.game.objects.VirtualBlock;
import ceta.game.util.GamePreferences;
import ceta.game.util.Solution;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private int priceDisplayNumber;
    private int priceNumber;
    private int response;
    private ArrayList<Integer> responseBlocks;
    private int levelNumber;
    // private FileHandle intentsFile; COMMENTED FOR FINAL APP
    private  SimpleDateFormat sdf;
    private  SimpleDateFormat justTime;
    private long collectionTimeMillis;
    private String toSave;
    private byte successfulIntent; // 1=yes, 0=no

    private ArrayList<VirtualBlock> lastSolution;
    private ArrayList<Solution> lastFinalSolution;

    private int lastSolutionNr;
    private int lastFinalSolutionNr;
    private String randomId;

    public ResultsManager() {
        sdf = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss.SSS");
        justTime = new SimpleDateFormat("HH:mm:ss.SSS");
        randomId = GamePreferences.instance.randomId;

        //intentsFile = Gdx.files.external("./ceta-results/"+randomId+"-results.csv"); COMMENTED FOR FINAL APP
        //Gdx.app.log(TAG, " AAAAAAA "+ Gdx.files.getLocalStoragePath()+ " available: "+Gdx.files.isLocalStorageAvailable());
        lastSolution = new ArrayList<VirtualBlock>();
        lastFinalSolution = new ArrayList<Solution>();
        lastFinalSolutionNr = 0;
        lastSolutionNr = 0;

        //String text = intentsFile.readString(); COMMENTED FOR FINAL APP
        //Gdx.app.log(TAG,text);
    }



    public void newPriceAppeared(int priceNr, int levelNr){
        intentStartDate = new Date();
        intentsNr = 0;
        priceNumber = priceNr;
        levelNumber = levelNr;
        Gdx.app.log(TAG,"NEW PRICE "+intentStartDate);
    }

    public synchronized void addIntent(boolean wasSuccessful, int kidResponse, int priceVal, int priceDisplayNr, ArrayList<Integer> toReadVals, int score){
        Gdx.app.log(TAG," addIntent +1 to "+intentsNr);
        //String id = Secure.getString(getContentResolver(), Secure.ANDROID_ID); COMMENTED FOR FINAL APP


        responseBlocks = toReadVals;

        intentsNr+=1;
        intentEndDate = new Date();
        collectionTimeMillis = intentEndDate.getTime() - intentStartDate.getTime() ;

        if(wasSuccessful)
            successfulIntent = 1;
        else
            successfulIntent = 0;

        response = kidResponse;
        priceValue = priceVal;
        priceDisplayNumber = priceDisplayNr;
        // level, price Nr, price val, priceDiplayNr, response, responseBlocks, response blocks number, intents nr, intnet result (1 or 0),
        // time appeared, time collected, difference in millis
        // current score, total score
        // id
        toSave = levelNumber+","+priceNumber+","+priceValue+","+priceDisplayNumber+","
                +response+","+formatResponseBlocks(responseBlocks)+","+responseBlocks.size()+","
                +intentsNr+","+successfulIntent+","
                +sdf.format(intentStartDate)+","+justTime.format(intentEndDate)+","+collectionTimeMillis+","
                +score+","+GamePreferences.instance.totalScore+","
                +randomId+"\n";
        Gdx.app.log(TAG,"save data "+toSave);
        //intentsFile.writeString(toSave,true); COMMENTED FOR FINAL APP

        // now we reset the start
        intentStartDate = intentEndDate;
        lastSolutionNr = kidResponse;

    }

    private String formatResponseBlocks(ArrayList<Integer> responseBlocks){
        String responseAsString = "";
        for (int i=0;i<responseBlocks.size();i++){
            responseAsString+=responseBlocks.get(i)+" ";
        }
        return responseAsString;
    }

    public ArrayList<VirtualBlock> getLastSolution(){
        return lastSolution;
    }

    public int getLastSolutionNr(){
        return lastSolutionNr;
    }

    public ArrayList<Solution> getLastFinalSolution(){
        return lastFinalSolution;
    }

    public int getLastFinalSolutionNr(){
        return lastFinalSolutionNr;
    }

    public void setLastToFinal(){
        lastFinalSolution.clear();
        for(int i =0;i<lastSolution.size();i++) {
            VirtualBlock last = lastSolution.get(i);
            lastFinalSolution.add(new Solution(last.getCenterVector().x,last.getCenterVector().y,last.getBlockId()));
        }
        lastFinalSolutionNr = lastSolutionNr;

        for(int i=0;i<lastSolution.size();i++){
            Gdx.app.log(TAG," final solution id"+lastSolution.get(i).getBlockId()
                    +" x "+lastSolution.get(i).getCenterVector().x
                    +" val "+lastSolution.get(i).getBlockValue());
        }
    }

    public void saveLastSolution(ArrayList<VirtualBlock> lastBlocks){
        Gdx.app.log(TAG, " saving last solution: "+lastBlocks.size());
        lastSolution = lastBlocks;

    }

    public synchronized void onCollisionRecord(int correctAnswer, int displayNr, int score){
        Gdx.app.log(TAG," onCollisionRecord, intent: "+ intentsNr);

        if(intentsNr == 0) {
            intentEndDate = new Date();
            collectionTimeMillis = intentEndDate.getTime() - intentStartDate.getTime();
            successfulIntent = 1;

            response = correctAnswer;
            priceValue = correctAnswer;
            priceDisplayNumber = displayNr;
            // level, price Nr, price val, priceDiplayNr, response, responseBlocks, response blocks number, intents nr, intent result (1 or 0),
            // time appeared, time collected, difference in millis
            // current score, total score
            // id
            toSave = levelNumber + "," + priceNumber + "," + priceValue + "," + priceDisplayNumber + ","
                    + response + "," + formatResponseBlocks(responseBlocks) + "," + responseBlocks.size() + ","
                    + intentsNr + "," + successfulIntent + ","
                    + sdf.format(intentStartDate) + "," + justTime.format(intentEndDate) + "," + collectionTimeMillis + ","
                    + score + "," + GamePreferences.instance.totalScore + ","
                    + randomId + "\n";
            Gdx.app.log(TAG, "save data " + toSave);
            //intentsFile.writeString(toSave, true); COMMENTED FOR FINAL APP

            // now we reset the start
            intentStartDate = intentEndDate;
            lastSolutionNr = correctAnswer;
        }
        else
            Gdx.app.log(TAG," ignore on collision");

    }

}