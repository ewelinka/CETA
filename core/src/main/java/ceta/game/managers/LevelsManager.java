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
        Gdx.app.log(TAG,"save last completed level "+lastLevelCompleted);
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

    private void resetToZeroAndAddRepeat(){
        lastLevelCompleted = 0;
        GamePreferences.instance.setLastLevel(lastLevelCompleted);
        GamePreferences.instance.addOneRepeat();

    }

    public void goToFirstUncompletedLevel(boolean isInit){
        Gdx.app.log(TAG,"lastLevelCompleted ----> "+lastLevelCompleted);
        //first we check if we deal with an important change
        if(isInit) {
            if(lastLevelCompleted >=0 && lastLevelCompleted<Constants.L1_HALF_COMPLETED_NR){
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            } else if(lastLevelCompleted >= Constants.L1_HALF_COMPLETED_NR && lastLevelCompleted<Constants.L1_COMPLETED_NR){
                game.setScreen(new TreeHalfScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }else if(lastLevelCompleted >= Constants.L1_COMPLETED_NR && lastLevelCompleted<Constants.L2_HALF_COMPLETED_NR){
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }else if(lastLevelCompleted >= Constants.L2_HALF_COMPLETED_NR && lastLevelCompleted<Constants.L2_COMPLETED_NR){
                game.setScreen(new TreeHalfScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }
            else if(lastLevelCompleted >= Constants.L2_COMPLETED_NR && lastLevelCompleted<Constants.L3_HALF_COMPLETED_NR){
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }
            else if(lastLevelCompleted >= Constants.L3_HALF_COMPLETED_NR && lastLevelCompleted<Constants.L3_COMPLETED_NR){
                game.setScreen(new TreeHalfScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }else if(lastLevelCompleted >= Constants.L3_COMPLETED_NR && lastLevelCompleted<Constants.L4_HALF_COMPLETED_NR){
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }
            else if(lastLevelCompleted >= Constants.L4_HALF_COMPLETED_NR && lastLevelCompleted<Constants.L4_COMPLETED_NR){
                game.setScreen(new TreeHalfScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }
            else if(lastLevelCompleted >= Constants.L4_COMPLETED_NR && lastLevelCompleted<Constants.L5_HALF_COMPLETED_NR){
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }
            else if(lastLevelCompleted >= Constants.L5_HALF_COMPLETED_NR && lastLevelCompleted<Constants.L5_COMPLETED_NR){
                game.setScreen(new TreeHalfScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }
            else if(lastLevelCompleted >= Constants.L5_COMPLETED_NR && lastLevelCompleted<Constants.L6_HALF_COMPLETED_NR){
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }
            else if(lastLevelCompleted >= Constants.L6_HALF_COMPLETED_NR && lastLevelCompleted<Constants.L6_COMPLETED_NR){
                game.setScreen(new TreeHalfScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }else{
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            }

        }else {


            if (lastLevelCompleted == 0
                    || lastLevelCompleted == Constants.L1_COMPLETED_NR
                    || lastLevelCompleted == Constants.L2_COMPLETED_NR
                    || lastLevelCompleted == Constants.L3_COMPLETED_NR
                    || lastLevelCompleted == Constants.L4_COMPLETED_NR
                    || lastLevelCompleted == Constants.L5_COMPLETED_NR
                    || lastLevelCompleted == Constants.L6_COMPLETED_NR) {
                game.setScreen(new TreeScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            } else if (lastLevelCompleted == Constants.L1_HALF_COMPLETED_NR
                    || lastLevelCompleted == Constants.L2_HALF_COMPLETED_NR
                    || lastLevelCompleted == Constants.L3_HALF_COMPLETED_NR
                    || lastLevelCompleted == Constants.L4_HALF_COMPLETED_NR
                    || lastLevelCompleted == Constants.L5_HALF_COMPLETED_NR
                    || lastLevelCompleted == Constants.L6_HALF_COMPLETED_NR) {
                game.setScreen(new TreeHalfScreen(game, isInit), ScreenTransitionFade.init(0.75f));
            } else { // if no important change -> we just go to next level
                goToNextLevel(lastLevelCompleted);
            }
        }
    }

    public void goToNextLevel(int completedLevel){
        if(completedLevel >= Constants.LAST_LEVEL_NR){
            resetToZeroAndAddRepeat();
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
                            game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2HorizontalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3HorizontalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
            }
        }else{
            switch(levelCharacteristics.representation){
                case 1:
                    game.setScreen(new Level1VerticalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2VerticalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3VerticalScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
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
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2HorizontalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3HorizontalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
            }
        }else{
            switch(levelCharacteristics.representation){
                case 1:
                    game.setScreen(new Level1VerticalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 2:
                    game.setScreen(new Level2VerticalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
                            transition);
                    break;
                case 3:
                    game.setScreen(new Level3VerticalCvScreen(
                                    game, lev, getIslandBack(levelCharacteristics.island,levelCharacteristics.backgroundNr)),
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

    private TextureAtlas.AtlasRegion getIslandBack(int islandNr, int backgroundNr){
        String islandBack = islandNr+"-"+backgroundNr;
        switch (islandBack) {
            case "1-1": //city
                return Assets.instance.staticBackground.city1;
            case "1-2":
                return Assets.instance.staticBackground.city2;
            case "1-3":
                return Assets.instance.staticBackground.city3;
            case "1-4":
                return Assets.instance.staticBackground.city3;
            case "2-1": // clouds
                return Assets.instance.staticBackground.clouds1;
            case "2-2":
                return Assets.instance.staticBackground.clouds2;
            case "2-3":
                return Assets.instance.staticBackground.clouds3;
            case "2-4":
                return Assets.instance.staticBackground.clouds4;
            case "3-1": //tubes
                return Assets.instance.staticBackground.tubes1;
            case "3-2":
                return Assets.instance.staticBackground.tubes2;
            case "3-3":
                return Assets.instance.staticBackground.tubes3;
            case "3-4":
                return Assets.instance.staticBackground.tubes4;
            case "4-1": // underwater
                return Assets.instance.staticBackground.sea1;
            case "4-2":
                return Assets.instance.staticBackground.sea2;
            case "4-3":
                return Assets.instance.staticBackground.sea3;
            case "4-4":
                return Assets.instance.staticBackground.sea4;
            case "4-5":
                return Assets.instance.staticBackground.sea5;
            case "5-1": // factory
                return Assets.instance.staticBackground.factory1;
            case "5-2":
                return Assets.instance.staticBackground.factory2;
            case "5-3":
                return Assets.instance.staticBackground.factory3;
            case "5-4":
                return Assets.instance.staticBackground.factory4;
            case "5-5":
                return Assets.instance.staticBackground.factory5;
            case "6-1": //night city and final battle
                return Assets.instance.staticBackground.night1;
            case "6-2":
                return Assets.instance.staticBackground.night2;
            case "6-3":
                return Assets.instance.staticBackground.night3;
            case "6-4":
                return Assets.instance.staticBackground.battle1;
            default:
                return Assets.instance.staticBackground.tubes5;

        }
    }


    public int getLastLevelCompleted(){
        return lastLevelCompleted;
    }
    public int getCurrentLevel(){ return lastLevelCompleted+1;}
}
