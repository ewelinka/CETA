package ceta.game.util;

import ceta.game.CetaGame;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.Level1HorizontalScreen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;

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
        GamePreferences.instance.setLastLevel(lastLevel+1);
        switch(lastLevel){
            case 1:
                //TODO change after wizard of oz
                game.setScreen(((CetaGame)game).getLevel1HorizontalScreen(), transition);
               // game.setScreen(new Level1HorizontalScreen(game), transition); //level params sets in world.controller.level using GamePreferences
                break;
            case 2:
                game.setScreen(((CetaGame)game).getLevel1HorizontalScreen(), transition);
                break;
            case 3:
                game.setScreen(new Level1HorizontalScreen(game), transition);
                break;
            case 4:
                game.setScreen(new Level1HorizontalScreen(game), transition);
                break;

        }
    }
}
