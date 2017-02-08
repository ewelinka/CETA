package ceta.game.util;

import ceta.game.game.levels.LevelParams;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by ewe on 2/3/17.
 */
public class LevelsCsv {
    public static final String TAG = LevelsCsv.class.getName();
    public static final LevelsCsv instance = new LevelsCsv();
    private String[] csvLines;

    private LevelsCsv () {
        FileHandle levelsFile = Gdx.files.internal(Constants.LEVELS_FOLDER+"/levels.csv");
        String csvContentString = levelsFile.readString();
        csvLines = csvContentString.split("\n");
        Gdx.app.log(TAG,"csvContentString "+csvContentString);
    }

    public LevelParams getParams(int levelNr){
        LevelParams levelParams = new LevelParams();
        String line = csvLines[levelNr];//0 for level 0 = tutorial
        Gdx.app.log(TAG,"line "+line);
        String [] splittedLine = line.split(",");
        levelParams.numberMin = Integer.parseInt(splittedLine[0]);
        levelParams.numberMax = Integer.parseInt(splittedLine[1]);
        levelParams.operationsToFinishLevel = Integer.parseInt(splittedLine[2]);

        int [] operations;
        if(splittedLine[3].length() > 0){
            Gdx.app.log(TAG," splitted 3 "+splittedLine[3]);
            String operationsStr = splittedLine[3];
            Gdx.app.log(TAG," operationsStr "+operationsStr);
            String[] operationsStrSplit = operationsStr.split(" ");
            operations = new int[operationsStrSplit.length];
            Gdx.app.log(TAG," operationsStrSplit "+operationsStrSplit);
            for(int i=0;i<operationsStrSplit.length;i++){
                operations[i]= Integer.parseInt(operationsStrSplit[i]);
            }
        }else{
            operations = new int[]{};
        }

        levelParams.operations = operations;
        levelParams.priceReturn = Integer.parseInt(splittedLine[4]);
        levelParams.priceVelocity = Integer.parseInt(splittedLine[5]);
        if(splittedLine[6].equals("1"))
            levelParams.visibleNumberLine = true;
        else
            levelParams.visibleNumberLine=false;

        return levelParams;

    }
}
