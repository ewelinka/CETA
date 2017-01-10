package ceta.game.managers;

import ceta.game.screens.*;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
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

    public void onLevelCompleted(){
        lastLevelCompleted +=1;
        GamePreferences.instance.setLastLevel(lastLevelCompleted);
    }

    public void goToNextLevelWorkaround(){
        lastLevelCompleted +=1;
        updateGamePreferencesAndGoWorkaround(lastLevelCompleted);
    }

    public void goToPreviousLevelWorkaround(){ // please do not do it at home!
        lastLevelCompleted -=1;
        updateGamePreferencesAndGoWorkaround(lastLevelCompleted);
    }

    private void updateGamePreferencesAndGoWorkaround(int forcedLastCompletedLevel){
        GamePreferences.instance.setLastLevel(forcedLastCompletedLevel); // we notify game preferences that we are in this level
        // lastLevelCompleted = GamePreferences.instance.lastLevel;
        // GamePreferences.instance.setLastLevel(lastLevel+1); // we need it to load the correct level-params
        if(Constants.WITH_CV) {
            goToLevelCV(forcedLastCompletedLevel);
        }
        else {
            goToLevelTablet(forcedLastCompletedLevel);
        }
    }

    public void goToFirstUncompletedLevel(){
        //first we check if we deal with an important change
        if( lastLevelCompleted == Constants.L1_COMPLETED_NR
                || lastLevelCompleted == Constants.L2_COMPLETED_NR
                || lastLevelCompleted == Constants.L3_COMPLETED_NR
                || lastLevelCompleted == Constants.L4_COMPLETED_NR
                || lastLevelCompleted == Constants.L5_COMPLETED_NR){
            game.setScreen(new TreeScreen(game));
        }else { // if no important change -> we just go to next level
            if (Constants.WITH_CV) {
                goToLevelCV(lastLevelCompleted);
            } else {
                goToLevelTablet(lastLevelCompleted);
            }
        }
    }

    public void goToFirstUncompletedLevelFromTree(){
        if (Constants.WITH_CV) {
            goToLevelCV(lastLevelCompleted);
        } else {
            goToLevelTablet(lastLevelCompleted);
        }

    }


    private void goToLevelCV(int lastLevelFinished){
        switch(lastLevelFinished){
            case 0:
                game.setScreen(new Level1HorizontalCvScreen(game, 1), transition);
                break;
            case 1:
                game.setScreen(new Level1VerticalCvScreen(game, 1), transition);
                break;
            case 2:
                game.setScreen(new Level2HorizontalCvScreen(game, 1), transition);
                break;
            case 3:
                game.setScreen(new Level2VerticalCvScreen(game, 1), transition);
                break;
            case 4:
                game.setScreen(new Level3HorizontalCvScreen(game, 1), transition);
                break;
            case 5:
                game.setScreen(new Level3VerticalCvScreen(game, 1), transition);
                break;
            default:
                GamePreferences.instance.setLastLevel(0); // we go to the beginning
                lastLevelCompleted = 0;
                game.setScreen(new MenuScreen(game), transition);
                break;
        }

    }

    private void goToLevelTablet(int lastLevelFinished){
        switch(lastLevelFinished){
            case 0:
                game.setScreen(new Level1HorizontalScreen(game, 1), transition);
                break;
            case 1:
                game.setScreen(new Level1HorizontalScreen(game, 2), transition);
                break;
            case 2:
                game.setScreen(new Level1VerticalScreen(game, 1), transition);
                break;
            case 3:
                game.setScreen(new Level1VerticalScreen(game, 2), transition);
                break;
            case 4:
                game.setScreen(new Level2HorizontalScreen(game, 1), transition);
                break;
            case 5:
                game.setScreen(new Level2HorizontalScreen(game, 2), transition);
                break;
            case 6:
                game.setScreen(new Level2VerticalScreen(game, 1), transition);
                break;
            case 7:
                game.setScreen(new Level2VerticalScreen(game, 2), transition);
                break;
            case 8:
                game.setScreen(new Level3HorizontalScreen(game, 1), transition);
                break;
            case 9:
                game.setScreen(new Level3HorizontalScreen(game, 2), transition);
                break;
            case 10:
                game.setScreen(new Level3VerticalScreen(game, 1), transition);
                break;
            case 11:
                game.setScreen(new Level3VerticalScreen(game, 2), transition);
                break;
            default:
                GamePreferences.instance.setLastLevel(0); // we go to the beginning
                lastLevelCompleted = 0;
                game.setScreen(new MenuScreen(game), transition);
                break;
        }

    }


    public int getLastLevelCompleted(){
        return lastLevelCompleted;
    }
}
