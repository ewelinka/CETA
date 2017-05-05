package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Created by ewe on 2/7/17.
 */
public class EmergencyScreen extends AbstractGameScreen  {
    private static final String TAG = EmergencyScreen.class.getName();
    private TextField txtLastLevel,txtLastScore;
    private Skin skin;
    private ImageButton btnPlay;
    private int goToLevel = 1;
    private int lastScore = 0;

    public EmergencyScreen(DirectedGame game){
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        //Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal(Constants.SKIN_UI));
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH , Constants.VIEWPORT_HEIGHT));
        txtLastLevel = new TextField("",skin);
        txtLastLevel.setMessageText("Go to level");
        txtLastLevel.setPosition(230, 830);
        stage.addActor(txtLastLevel);

        txtLastScore= new TextField("",skin);
        txtLastScore.setMessageText("Last score");
        txtLastScore.setPosition(230, 730);
        stage.addActor(txtLastScore);

        btnPlay = new ImageButton(Assets.instance.buttons.playButtonStyle);
        btnPlay.setPosition(230,530);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        stage.addActor(btnPlay);

//        Gdx.input.getTextInput(new Input.TextInputListener() {
//            @Override
//            public void input (String inputTxt) {
//                Gdx.app.log(TAG,"we have txt! "+inputTxt);
//
//                try {
//                    goToLevel = Integer.parseInt(inputTxt);
//
//                } catch (Exception e) {
//                    goToLevel = 0;
//                    e.printStackTrace();
//                }
//                game.levelsManager.forceLevel(goToLevel);
//
//                game.setScreen(new TreeScreen(game,true));
//            }
//
//            @Override
//            public void canceled () {
//                game.setScreen(new TreeScreen(game,true));
//            }
//        }, "Go to level", "", "Go to level");
//
//        Gdx.input.getTextInput(new Input.TextInputListener() {
//            @Override
//            public void input (String inputTxt) {
//                Gdx.app.log(TAG,"we have txt! "+inputTxt);
//
//                try {
//                    lastScore = Integer.parseInt(inputTxt);
//
//                } catch (Exception e) {
//                    lastScore=0;
//                    e.printStackTrace();
//                }
//                GamePreferences.instance.forceGlobalScore(lastScore);
//            }
//
//            @Override
//            public void canceled () {
//            }
//        }, "Global score", "", "Global score");

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }

    private void onPlayClicked(){
        try {
            lastScore = Integer.parseInt(txtLastScore.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            goToLevel = Integer.parseInt(txtLastLevel.getText());

        } catch (Exception e) {
                e.printStackTrace();
        }
        game.levelsManager.forceLevel(goToLevel);
        GamePreferences.instance.forceGlobalScore(lastScore);
        game.levelsManager.goToFirstUncompletedLevel(true);

    }


}
