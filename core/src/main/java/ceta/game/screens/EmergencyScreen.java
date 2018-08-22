package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
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
    private TextField txtLastLevel,txtLastScore, txtRepeat;
   // private CheckBox chkUseDebug; also out txtTopCodeFValue
    private Skin skin;
    private ImageButton btnPlay;
    private int goToLevel = 1;
    private int lastScore = 0;
    private int repeat = 0;
    private float fvalue = GamePreferences.instance.getFvalue();// 0.85f; //default TopCode f value for adaptive thresholding algorithm

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

        txtRepeat= new TextField("",skin);
        txtRepeat.setMessageText("Repeat round");
        txtRepeat.setPosition(230, 630);
        stage.addActor(txtRepeat);

//        txtTopCodeFValue= new TextField("",skin);
//        txtTopCodeFValue.setMessageText("FValue");
//        txtTopCodeFValue.setText(String.valueOf(fvalue));
//        txtTopCodeFValue.setPosition(230, 530);
//        stage.addActor(txtTopCodeFValue);
//
//        chkUseDebug = new CheckBox("Use debug", skin);
//        chkUseDebug.setPosition(230,430);
//        chkUseDebug.setChecked(GamePreferences.instance.getUseDebug());
//        stage.addActor(chkUseDebug);

        
        btnPlay = new ImageButton(Assets.instance.buttons.playButtonStyle);
        btnPlay.setPosition(230,430);
        btnPlay.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        stage.addActor(btnPlay);


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
            if(!txtLastScore.getText().equals("")) {
                lastScore = Integer.parseInt(txtLastScore.getText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(!txtLastLevel.getText().equals("")) {
                goToLevel = Integer.parseInt(txtLastLevel.getText());
            }

        } catch (Exception e) {
                e.printStackTrace();
        }

        try {
            if(!txtRepeat.getText().equals("")) {
                repeat = Integer.parseInt(txtRepeat.getText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
//        try {
//            if(!txtTopCodeFValue.getText().equals("")) {
//                fvalue = Float.parseFloat(txtTopCodeFValue.getText());
//            }
//
//        } catch (Exception e) {
//            Gdx.app.log(TAG,"fvalue "+fvalue);
//                e.printStackTrace();
//        }


        
        game.levelsManager.forceLevel(goToLevel);
                
        GamePreferences.instance.forceGlobalScore(lastScore);
        GamePreferences.instance.forceRepeatNr(repeat );
 //       GamePreferences.instance.setFvalue(fvalue);
//        GamePreferences.instance.setUseDebug(chkUseDebug.isChecked());
        game.levelsManager.goToFirstUncompletedLevel(true);



    }


}