package ceta.game.screens;

/**
 * Created by ewe on 2/16/17.
 */
public class CongratulationsScreenTutorial extends CongratulationsScreen {
    public CongratulationsScreenTutorial(DirectedGame game, int score) {
        super(game, score);
    }

    @Override
    protected void saveLevelAndScore(int score){
       //nothing!! it's just tutorial!!'
    }
}
