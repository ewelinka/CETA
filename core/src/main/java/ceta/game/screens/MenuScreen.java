package ceta.game.screens;

import ceta.game.CetaGame;
import ceta.game.game.Assets;
import ceta.game.transitions.ScreenTransition;
import ceta.game.transitions.ScreenTransitionFade;
import ceta.game.util.Constants;

import ceta.game.util.GamePreferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.text.DecimalFormat;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 7/25/16.
 */
public class MenuScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getName();
    private Stage stage;
    private Skin skin;

    private Button btnMenuPlay;
    private Button btnMenuOptions;

    private Window winOptions;
    private TextButton btnWinOptSave;
    private TextButton btnWinOptCancel;
    private CheckBox chkActionSubmit;
    private Slider sldCountdownMax;
    private Slider sldVirtualBlocksAlpha;
    private Slider sldCollectedToWin;
    private Image imgBackground;

    private Label visibilityValueLabel;
    private Label countdownMaxValueLabel;
    private Label collectedToWinValueLabel;
    private DecimalFormat formatter = new DecimalFormat("0.0##");




    private TextButton btnStartGame;
    private TextButton btnConfigOz;




    private Level1Screen screen1;

    public MenuScreen (DirectedGame game) {
        super(game);
        this.screen1 = ((CetaGame)game).getLevel1Screen();
    }


    public void buildStage() {
        skin = new Skin(Gdx.files.internal(Constants.SKIN_UI));

        Table layerBackground = buildBackgroundLayer();
        Table layerControls = buildControlsLayer();
        Table layerOptionsWindow = buildOptionsWindowLayer();
        stage.clear();

        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/2);
        stack.add(layerBackground);
        stack.add(layerControls);

        stage.addActor(layerOptionsWindow);



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
        stage = new Stage(new FitViewport(Constants.VIEWPORT_WIDTH/2, Constants.VIEWPORT_HEIGHT/2));

        buildStage();
    };
    public void hide (){

        stage.dispose();
        skin.dispose();
    };
    public void pause (){};

    private void onPlayClicked () {
        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
        game.setScreen(screen1, transition);
    }
    
    private void onStartOzClicked(){
    	((CetaGame)game).initReceiver(screen1); //Level1Screen implements OSCListener
    	((CetaGame)game).startOSCReceiver();
    	btnConfigOz.setText(((CetaGame)game).getLocalIp());
    	//TODO Ewe: Display ((CetaGame)game).getLocalIp(); on screen. De momento cambio el texto del boton para poder ver la ip en algun lado    	
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
        // bfont.scale(1);
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
        tbl.add(btnStartGame).align(Align.top);
        tbl.setPosition(Constants.VIEWPORT_WIDTH/2 - tbl.getWidth() , Constants.VIEWPORT_HEIGHT/2);
        btnStartGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        
        tbl.row();
        btnConfigOz = new TextButton("START OZ", textButtonStyle);
        tbl.add(btnConfigOz).align(Align.bottom);
        btnConfigOz.addListener(new ChangeListener() {
			
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onStartOzClicked();
			}
		});
        
        return tbl;
    }

    private Table buildOptionsWindowLayer () {
        winOptions = new Window("Opciones", skin);

        // + Separator and Buttons (Save, Cancel)
        winOptions.add(buildOptWinButtons()).pad(10, 0, 0, 0);
        // Make options window slightly transparent
        winOptions.setColor(1, 1, 1, 0.8f);
        // Hide options window by default
        showOptionsWindow(false, false);
        if (false) winOptions.debug();
        // Let TableLayout recalculate widget sizes and positions
        winOptions.pack();
        // Move options window to bottom right corner
        winOptions.setPosition(Constants.VIEWPORT_WIDTH/4, Constants.VIEWPORT_HEIGHT/4);
        return winOptions;
    }

    private Table buildControlsLayer () {
        Table layer = new Table();
        layer.center().center();
        // + Play Button
        layer.pad(100, 0, 0, 10);

        btnMenuPlay = new TextButton("Jugar", skin);
        layer.add(btnMenuPlay).padBottom(10);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        layer.row();
        // + Options Button
        btnMenuOptions = new TextButton("Opciones",skin);
        layer.add(btnMenuOptions).padBottom(10);
        btnMenuOptions.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });
        layer.row();
        layer.row();
        btnConfigOz = new TextButton("START OZ", skin);
        layer.add(btnConfigOz).align(Align.bottom);
        btnConfigOz.addListener(new ChangeListener() {

            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onStartOzClicked();
            }
        });

        if (false) layer.debug();
        return layer;
    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        imgBackground = new Image(Assets.instance.background.back);
        layer.add(imgBackground);
        return layer;
    }

    private Table buildOptWinButtons () {
        Table tbl = new Table();
        tbl.left().top();
        tbl.pad(0, 10, 0, 10);
//        tbl.columnDefaults(0).padRight(10);
//        tbl.columnDefaults(1).padRight(10);
        // + Checkbox, "Sound" label, sound volume slider
        chkActionSubmit = new CheckBox("ActionSubmit", skin);
        tbl.add(chkActionSubmit).left().padBottom(10);

        tbl.row();
        countdownMaxValueLabel =  new Label("Countdown (0 a 10), ahora 5.0", skin);
        tbl.add(countdownMaxValueLabel).padRight(20);

        tbl.row();
        sldCountdownMax = new Slider(0.0f, 10.0f, 1.0f, false, skin);
        tbl.add(sldCountdownMax).padBottom(15);
        sldCountdownMax.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                countdownMaxValueLabel.setText("Countdown  (0 a 10), ahora: " + formatter.format(sldCountdownMax.getValue()));
            }
        });

        tbl.row();
        // visibility slider
        visibilityValueLabel = new Label("Visibilidad  (0 a 1), ahora 0.5", skin);
        tbl.add(visibilityValueLabel);

        tbl.row();
        sldVirtualBlocksAlpha = new Slider(0.0f, 1.0f, 0.1f, false, skin);
        tbl.add(sldVirtualBlocksAlpha).padBottom(15);
        sldVirtualBlocksAlpha.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                visibilityValueLabel.setText("Visibilidad  (0 a 1), ahora: " + formatter.format(sldVirtualBlocksAlpha.getValue()));
            }
        });


        tbl.row();
        // collected coins slider
        collectedToWinValueLabel = new Label("Recolactar para ganar (1 a 10), ahora: 10.0", skin);
        tbl.add(collectedToWinValueLabel);

        tbl.row();
        sldCollectedToWin = new Slider (1.0f,10.0f,1.0f,false,skin);
        tbl.add(sldCollectedToWin).padBottom(10);
        sldCollectedToWin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                collectedToWinValueLabel.setText("Recolactar para ganar (1 a 10), ahora: " + formatter.format(sldCollectedToWin.getValue()));
            }
        });

        // + Save Button with event handler
        tbl.row();
        btnWinOptSave = new TextButton("Save", skin);
        tbl.add(btnWinOptSave).left();
        btnWinOptSave.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onSaveClicked();
            }
        });
        // + Cancel Button with event handler
        btnWinOptCancel = new TextButton("Cancel", skin);
        tbl.add(btnWinOptCancel);
        btnWinOptCancel.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onCancelClicked();
            }
        });
        return tbl;
    }

    private void onOptionsClicked () {
        loadSettings();
        showMenuButtons(false);
        showOptionsWindow(true, true);
    }

    private void loadSettings() {
        GamePreferences prefs = GamePreferences.instance;
        prefs.load();
        chkActionSubmit.setChecked(prefs.actionSubmit);
        sldCountdownMax.setValue(prefs.countdownMax);
        sldVirtualBlocksAlpha.setValue(prefs.virtualBlocksAlpha);
        sldCollectedToWin.setValue(prefs.collectedToWin);
        visibilityValueLabel.setText("Visibilidad (0 to 1), ahora: "+prefs.virtualBlocksAlpha);
        countdownMaxValueLabel.setText("Countdown (0 a 10), ahora: "+prefs.countdownMax);
        collectedToWinValueLabel.setText("Recolactar para ganar (1 a 10), ahora:"+prefs.collectedToWin);
    }

    private void saveSettings() {
        GamePreferences prefs = GamePreferences.instance;
        prefs.load();
        prefs.actionSubmit = chkActionSubmit.isChecked();
        prefs.countdownMax = sldCountdownMax.getValue();
        prefs.virtualBlocksAlpha = sldVirtualBlocksAlpha.getValue();
        prefs.collectedToWin = sldCollectedToWin.getValue();
        prefs.save();
    }

    private void onSaveClicked() {
        saveSettings();
        onCancelClicked();
    }
    private void onCancelClicked() {
        showMenuButtons(true);
        showOptionsWindow(false, true);
    }

    private void showMenuButtons (boolean visible) {
        float moveDuration = 1.0f;
        Interpolation moveEasing = Interpolation.swing;
        float delayOptionsButton = 0.25f;
        float moveX = 300 * (visible ? -1 : 1);
        float moveY = 0 * (visible ? -1 : 1);
        final Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
        btnMenuPlay.addAction(moveBy(moveX, moveY, moveDuration, moveEasing));
        btnMenuOptions.addAction(sequence(
                delay(delayOptionsButton),
                moveBy(moveX, moveY, moveDuration, moveEasing)));
        btnConfigOz.addAction(sequence(
                delay(delayOptionsButton*2),
                moveBy(moveX, moveY, moveDuration, moveEasing)));
        SequenceAction seq = sequence();
        if (visible) seq.addAction(delay(delayOptionsButton + moveDuration));
        seq.addAction(run(new Runnable() {
            public void run () {
                btnMenuPlay.setTouchable(touchEnabled);
                btnMenuOptions.setTouchable(touchEnabled);
                btnConfigOz.setTouchable(touchEnabled);
            } }));
        stage.addAction(seq);
    }

    private void showOptionsWindow (boolean visible, boolean animated) {
        float alphaTo = visible ? 0.8f : 0.0f;
        float duration = animated ? 1.0f : 0.0f;
        Touchable touchEnabled = visible ? Touchable.enabled : Touchable.disabled;
        winOptions.addAction(sequence(
                touchable(touchEnabled),
                alpha(alphaTo, duration)));
    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }


}