package ceta.game.util;

import ceta.game.CetaGame;
import ceta.game.screens.DirectedGame;
import ceta.game.screens.Level1HorizontalScreen;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import com.badlogic.gdx.Gdx;

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
        GamePreferences.instance.setLastLevel(lastLevel+1); // we need it to load the correct level-params
        switch(lastLevel){
            case 1:
            case 2:
            case 3:
            case 4:
                //TODO change after wizard of oz
                game.setScreen(((CetaGame)game).getLevel1HorizontalScreen(), transition); // we go to level 2!
               // game.setScreen(new Level1HorizontalScreen(game), transition);
                break;
            default:
                GamePreferences.instance.setLastLevel(1); // we go to the beginning
                game.setScreen(((CetaGame)game).getLevel1HorizontalScreen(), transition);
                break;
        }
    }
}
