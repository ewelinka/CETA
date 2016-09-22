package ceta.game.screens;

import ceta.game.game.Assets;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by ewe on 9/21/16.
 */
public class FinishScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getName();
    private Stage stage;
    private Skin skin;

    private TextButton btnStartGame;
    private Image imgBackground;


    public FinishScreen (DirectedGame game) {
        super(game);
    }


    public void buildStage() {

        Table layerBackground = buildBackgroundLayer();
        Table playMenu = buildPlayMenu();

        stage.addActor(layerBackground);
        stage.addActor(playMenu);
    }

    public void render (float deltaTime){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();

    };
    public void resize (int width, int height){
        stage.getViewport().update(width, height, true);
        buildStage();
    };
    public void show (){
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));

        buildStage();
    };
    public void hide (){

        stage.dispose();
        skin.dispose();
    };
    public void pause (){};

    private void onPlayClicked () {
        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
        game.setScreen(new Level1Screen(game), transition);
    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        imgBackground = new Image(Assets.instance.finishBackGround.finishBack);
        layer.addActor(imgBackground);
        //TODO add actions, movements
        return layer;
    }

    private Table buildPlayMenu () {
        /// ------------------ start -- just to create a simple button!! what a caos!!
        skin = new Skin();
        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(140, 70, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));
        // Store the default libgdx font under the name "default".
        BitmapFont bfont=new BitmapFont();
        bfont.getData().scale(2);

        skin.add("default",bfont);
        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
        textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        /// ------------------ end --
        btnStartGame =new TextButton("JUGAR",textButtonStyle);

        Table tbl = new Table();
        //tbl.left().bottom();
        tbl.add(btnStartGame);
        tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth() , Constants.VIEWPORT_HEIGHT/2);
        btnStartGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        return tbl;
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }


    }