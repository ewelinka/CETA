package ceta.game.managers;

import ceta.game.CetaGame;
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
    private int currentLevel;

    public LevelsManager(DirectedGame game){
        this.game = game;
        currentLevel = GamePreferences.instance.lastLevel;
    }

    public void goToNextLevel(){
        currentLevel+=1;
        updateGamePreferencesAndGo(currentLevel);
    }

    public void goToPreviousLevel(){
        currentLevel-=1;
        updateGamePreferencesAndGo(currentLevel);
    }

    private void updateGamePreferencesAndGo(int nowLevel){
        GamePreferences.instance.setLastLevel(nowLevel); // we notify game preferences that we are in this level
        // currentLevel = GamePreferences.instance.lastLevel;
        // GamePreferences.instance.setLastLevel(lastLevel+1); // we need it to load the correct level-params
        if(Constants.WITH_CV) {
            goToLevelCV(nowLevel);
        }
        else {
            goToLevelTablet(nowLevel);
        }
    }

    public void goToCurrentLevel(){
//        currentLevel = GamePreferences.instance.lastLevel;
        // GamePreferences.instance.setLastLevel(lastLevel+1); // we need it to load the correct level-params
        if(Constants.WITH_CV) {
            goToLevelCV(currentLevel);
        }
        else {
            goToLevelTablet(currentLevel);
        }

    }


    private void goToLevelCV(int nowLevel){
        switch(nowLevel){
            case 0:
                game.setScreen(new MenuScreen(game), transition);
                break;
            case 1:
                game.setScreen(new Level1HorizontalCvScreen(game, 1), transition);
                break;
            case 2:
                game.setScreen(new Level1VerticalCvScreen(game, 1), transition);
                break;
            case 3:
                game.setScreen(new Level2HorizontalCvScreen(game, 1), transition);
                break;
            case 4:
                game.setScreen(new Level2VerticalCvScreen(game, 1), transition);
                break;
            case 5:
                game.setScreen(new Level3HorizontalCvScreen(game, 1), transition);
                break;
            case 6:
                game.setScreen(new Level3VerticalCvScreen(game, 1), transition);
                break;
            default:
                GamePreferences.instance.setLastLevel(0); // we go to the beginning
                game.setScreen(new Level1HorizontalCvScreen(game,1), transition);
                break;
        }

    }

    private void goToLevelTablet(int nowLevel){
        switch(nowLevel){
            case 0:
                game.setScreen(new MenuScreen(game), transition);
                break;
            case 1:
                game.setScreen(new Level1HorizontalScreen(game, 1), transition);
                break;
            case 2:
                game.setScreen(new Level1VerticalScreen(game, 1), transition);
                break;
            case 3:
                game.setScreen(new Level2HorizontalScreen(game, 1), transition);
                break;
            case 4:
                game.setScreen(new Level2VerticalScreen(game, 1), transition);
                break;
            case 5:
                game.setScreen(new Level3HorizontalScreen(game, 1), transition);
                break;
            case 6:
                game.setScreen(new Level3VerticalScreen(game, 1), transition);
                break;
            default:
                GamePreferences.instance.setLastLevel(1); // we go to the beginning
                game.setScreen(new Level1HorizontalScreen(game,1), transition);
                break;
        }

    }


    public int getCurrentLevel(){
        return currentLevel;
    }
}
