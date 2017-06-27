package ceta.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by ewe on 2/16/17.
 */
public class CongratulationsScreenTutorial extends CongratulationsScreen {
    public CongratulationsScreenTutorial(DirectedGame game, int score) {
        super(game, score);
    }

    @Override
    public void render (float deltaTime){
        if(moveToNextLevel){
            game.getLevelsManager().goToFirstUncompletedLevel(true);
            moveToNextLevel = false;
        }
        //blue!
        //Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        Gdx.gl.glClearColor(184 / 255.0f, 1 , 226/ 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    protected void saveLevelAndScore(int score){
       //nothing!! it's just tutorial!!'
    }

    @Override
    protected void playLevelPassed(){
    }
}
