package ceta.game.managers;

import ceta.game.game.Assets;
import ceta.game.game.levels.LevelCharacteristics;
import ceta.game.screens.*;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import ceta.game.util.LevelsCsv;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.sun.tools.internal.jxc.apt.Const;

/**
 * Created by ewe on 10/19/16.
 */
public class LevelsManager {
    public static final String TAG = LevelsManager.class.getName();

    private DirectedGame game;
    final ScreenTransition transition = ScreenTransitionFade.init(0.75f);
    private int lastLevelCompleted;

    public LevelsManager(DirectedGame game){
        this.game = game;
        lastLevelCompleted = GamePreferences.instance.lastLevel;
    }

    public void onLevelCompleted(int score){
        lastLevelCompleted +=1;
        GamePreferences.instance.setLastLevel(lastLevelCompleted);
        GamePreferences.instance.addToTotalScore(score);
    }

    public void goToNextLevelWorkaround(){
        lastLevelCompleted +=1;
        updateGamePreferencesAndGoWorkaround(lastLevelCompleted);
    }

    public void goToPreviousLevelWorkaround(){ // please, do not do it at home!
        lastLevelCompleted -=1;
        updateGamePreferencesAndGoWorkaround(lastLevelCompleted);
    }

    private void updateGamePreferencesAndGoWorkaround(int forcedLastCompletedLevel) {
        GamePreferences.instance.setLastLevel(forcedLastCompletedLevel); // we notify game preferences that we are in this level
        goToNextLevel(forcedLastCompletedLevel);
    }

    private void resetToZero(){
        lastLevelCompleted = 0;
        GamePreferences.instance.setLastLevel(lastLevelCompleted);
    }

    public void goToFirstUncompletedLevel(){
        Gdx.app.log(TAG,"lastLevelCompleted ----> "+lastLevelCompleted);
        //first we check if we deal with an important change
        if( lastLevelCompleted == 0
                || lastLevelCompleted == Constants.L1_COMPLETED_NR
                || lastLevelCompleted == Constants.L2_COMPLETED_NR
                || lastLevelCompleted == Constants.L3_COMPLETED_NR
                || lastLevelCompleted == Constants.L4_COMPLETED_NR
                || lastLevelCompleted == Constants.L5_COMPLETED_NR
                || lastLevelCompleted == Constants.L6_COMPLETED_NR){
            game.setScreen(new TreeScreen(game),ScreenTransitionFade.init(0.75f));
        }
        else { // if no important change -> we just go to next level
            goToNextLevel(lastLevelCompleted);
        }
    }

    public void goToNextLevel(int completedLevel){
        if(completedLevel >= Constants.LAST_LEVEL_NR){
            resetToZero();
            completedLevel = 0;
        }
        if (Constants.WITH_CV) {
            goToLevelCvCsv(completedLevel);
        } else {
            goToLevelTabletCsv(completedLevel);
        }


    }




    public void goToUncompletedLevel(){
        goToNextLevel(lastLevelCompleted);

    }



    private void goToLevelTabletCsv(int lastLevelFinished){
        Gdx.app.log(TAG,"goToLevelTabletCsv: "+lastLevelFinished);
        int lev = lastLevelFinished+1;
        LevelCharacteristics levelCharacteristics =  LevelsCsv.instance.getLevelCharacteristics(lev);
        levelCharacteristics.printLevelCharacteristics();
        if (levelCharacteristics.isHorizontal){
            switch(levelCharacteristics.representation){
                case 1:
                    game.setScreen(new Level1HorizontalScreen(
                            game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2HorizontalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3HorizontalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
            }
        }else{
            switch(levelCharacteristics.representation){
                case 1:
                    game.setScreen(new Level1VerticalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2VerticalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3VerticalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
            }

        }
    }

    private void goToLevelCvCsv(int lastLevelFinished){
        Gdx.app.log(TAG,"goToLevelCvCsv: "+lastLevelFinished);
        int lev = lastLevelFinished+1;
        LevelCharacteristics levelCharacteristics =  LevelsCsv.instance.getLevelCharacteristics(lev);
        levelCharacteristics.printLevelCharacteristics();
        if (levelCharacteristics.isHorizontal){
            switch(levelCharacteristics.representation){
                case 1:
                    game.setScreen(new Level1HorizontalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2HorizontalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3HorizontalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
            }
        }else{
            switch(levelCharacteristics.representation){
                case 1:
                    game.setScreen(new Level1VerticalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2VerticalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3VerticalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.isHorizontal)),
                            transition);
                    break;
            }

        }
    }


    public void forceLevel(int forcedLevel){
        if(forcedLevel<1)
            Gdx.app.log(TAG,"wroooong");
        else {
            GamePreferences.instance.setLastLevel(forcedLevel - 1);
            lastLevelCompleted = forcedLevel - 1;
        }

    }

    private TextureAtlas.AtlasRegion getIslandBack(int islandNr, boolean isHorizontal){
        if(isHorizontal){ // HORIZONTAL
            switch (islandNr){
                case 1:
                    return Assets.instance.staticBackground.city1;
                case 2:
                    return Assets.instance.staticBackground.city3;
                case 3:
                    return Assets.instance.staticBackground.tubes1;
                case 4:
                    return Assets.instance.staticBackground.tubes3;
                case 5:
                    return Assets.instance.staticBackground.clouds1;
                case 6:
                    return Assets.instance.staticBackground.clouds3;
                default:
                    return Assets.instance.staticBackground.tubes5;
            }

        }
        else{ // VERTICAL
            switch (islandNr){
                case 1:
                    return Assets.instance.staticBackground.city2;
                case 2:
                    return Assets.instance.staticBackground.city4;
                case 3:
                    return Assets.instance.staticBackground.tubes2;
                case 4:
                    return Assets.instance.staticBackground.tubes4;
                case 5:
                    return Assets.instance.staticBackground.clouds2;
                case 6:
                    return Assets.instance.staticBackground.clouds4;
                default:
                    return Assets.instance.staticBackground.tubes5;
            }

        }
    }


    public int getLastLevelCompleted(){
        return lastLevelCompleted;
    }
    public int getCurrentLevel(){ return lastLevelCompleted+1;}
}
