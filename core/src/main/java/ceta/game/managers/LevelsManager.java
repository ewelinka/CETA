package ceta.game.managers;

import ceta.game.CetaGame;
import ceta.game.screens.*;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.GamePreferences;

/**
 * Created by ewe on 10/19/16.
 */
public class LevelsManager {
    public static final String TAG = LevelsManager.class.getName();

    private DirectedGame game;
    final ScreenTransition transition = ScreenTransitionFade.init(0.75f);

    public LevelsManager(DirectedGame game){
        this.game = game;
    }

    public void goToNextLevel(){
        int lastLevel = GamePreferences.instance.lastLevel;
       // GamePreferences.instance.setLastLevel(lastLevel+1); // we need it to load the correct level-params
        switch(lastLevel){
            case 1:
                game.setScreen(new Level1HorizontalScreen(game,1), transition);
                break;
            case 2:
                game.setScreen(new Level1VerticalScreen(game,1), transition);
                break;
            case 3:
                game.setScreen(new Level2HorizontalScreen(game,1), transition);
                break;
            case 4:
                game.setScreen(new Level2VerticalScreen(game,1), transition);
                break;
            case 5:
                game.setScreen(new Level3HorizontalScreen(game,1), transition);
                break;
            case 6:
                game.setScreen(new Level3VerticalScreen(game,1), transition);
                break;
            default:
                GamePreferences.instance.setLastLevel(1); // we go to the beginning
                game.setScreen(new Level1HorizontalScreen(game,1), transition);
                break;
        }
    }
}
