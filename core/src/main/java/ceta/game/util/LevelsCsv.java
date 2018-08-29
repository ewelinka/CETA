package ceta.game.util;

import ceta.game.game.levels.LevelCharacteristics;
import ceta.game.game.levels.LevelParams;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by ewe on 2/3/17.
 */
public class LevelsCsv {
    public static final String TAG = LevelsCsv.class.getName();
    public static final LevelsCsv instance = new LevelsCsv();
    private String[] csvLines;

    private LevelsCsv () {
        FileHandle levelsFile = Gdx.files.internal(Constants.LEVELS_FOLDER+"/levels-fer.csv");
        String csvContentString = levelsFile.readString();
        csvLines = csvContentString.split("\n");
        Gdx.app.log(TAG,"csvContentString "+csvContentString);
    }

    public LevelParams getParams(int levelNr){
        LevelParams levelParams = new LevelParams();
        String line = csvLines[levelNr];//0 for titles
        Gdx.app.log(TAG,"line "+line);
        String [] splittedLine = line.split(",");
        levelParams.numberMin = Integer.parseInt(splittedLine[3]); //fourth column (nr3 if we start in 0)
        levelParams.numberMax = Integer.parseInt(splittedLine[4]);
        levelParams.operationsToFinishLevel = Integer.parseInt(splittedLine[5]);
        levelParams.islandNr = Integer.parseInt(splittedLine[0]);

        int [] operations;
        if(splittedLine[6].length() > 0){
            Gdx.app.log(TAG," splitted 6 "+ splittedLine[6]+ " splited len " +splittedLine[6].length());
            String operationsStr = splittedLine[6];
            Gdx.app.log(TAG," operationsStr "+operationsStr);
            String[] operationsStrSplit = operationsStr.split(" ");
            operations = new int[operationsStrSplit.length];
            Gdx.app.log(TAG," operationsStrSplit "+operationsStrSplit);
            for(int i=0;i<operationsStrSplit.length;i++){
                if(operationsStrSplit[i]!="")
                    operations[i]= Integer.parseInt(operationsStrSplit[i]);
            }
        }else{
            operations = new int[]{};
        }

        levelParams.operations = operations;
        levelParams.priceReturn = Integer.parseInt(splittedLine[7]);
        levelParams.priceVelocity = Integer.parseInt(splittedLine[8]);
        if(splittedLine[9].trim().equals("1")) {
            Gdx.app.log(TAG, "---- VISIBLE "+splittedLine[9].trim());
            levelParams.visibleNumberLine = true;
        }
        else {
            Gdx.app.log(TAG, " --- NOT ---- VISIBLE "+splittedLine[9].trim());
            levelParams.visibleNumberLine = false;
        }

        return levelParams;

    }

    public LevelCharacteristics getLevelCharacteristics(int levelNr){
        LevelCharacteristics levelCharacteristics = new LevelCharacteristics();
        String line = csvLines[levelNr];//0 for titles
        Gdx.app.log(TAG,"line "+line);
        String [] splittedLine = line.split(",");
        levelCharacteristics.island = Integer.parseInt(splittedLine[0]); //first column
        levelCharacteristics.representation = Integer.parseInt(splittedLine[1]); //second column
        levelCharacteristics.isHorizontal = (splittedLine[2].trim().equals("H") || splittedLine[2].trim().equals("h")); //third column
        levelCharacteristics.backgroundNr = Integer.parseInt(splittedLine[10]);
        return levelCharacteristics;

    }
}
