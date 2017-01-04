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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.text.DecimalFormat;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by ewe on 7/25/16.
 */
public class MenuScreen extends AbstractGameScreen {
    private static final String TAG = MenuScreen.class.getName();
    //private Stage stage;
    private Skin skin;

    private Button btnMenuPlay;

    private Button btnMenuOptions;

    private Window winOptions;
    private TextButton btnWinOptSave;
    private TextButton btnWinOptCancel;
    private CheckBox chkActionSubmit;
    private Slider sldCountdownMax;
    private Slider sldVirtualBlocksAlpha;
    private Image imgBackground;
    private Label visibilityValueLabel;
    private Label countdownMaxValueLabel;
    private DecimalFormat formatter = new DecimalFormat("0.0##");



    public MenuScreen (DirectedGame game) {
        super(game,0);
        GamePreferences.instance.setLastLevel(1); // TODO delete after testing
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
        Gdx.input.setCatchBackKey(false);
        buildStage();
    };
    public void hide (){

        stage.dispose();
        skin.dispose();
    };
    public void pause (){};

    private void onPlayClicked () {
        ScreenTransition transition = ScreenTransitionFade.init(0.75f);
        //game.setScreen(screen1, transition);
        game.getLevelsManager().goToNextLevel();
        //game.setScreen(new Level1VerticalScreen(game,1), transition);
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

        //layer.pad(100, 0, 0, 10);

        btnMenuPlay = new ImageButton(Assets.instance.buttons.playButtonStyle); // TODO should be resolved in ui-skin...
       // table.add (btnB).size (150, 200);
        layer.add(btnMenuPlay).size(88,60).padBottom(10);
        btnMenuPlay.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        layer.row();
        // + Play Button
//        btnMenuPlay = new TextButton("Jugar", skin);
//        layer.add(btnMenuPlay).padBottom(10);
//        btnMenuPlay.addListener(new ChangeListener() {
//            @Override
//            public void changed (ChangeEvent event, Actor actor) {
//                onPlayClicked();
//            }
//        });
//        layer.row();
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


        if (false) layer.debug();
        return layer;
    }

    private Table buildBackgroundLayer () {
        Table layer = new Table();
        imgBackground = new Image(Assets.instance.background.backStart);
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
        tbl.add(btnWinOptCancel).left();
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
        visibilityValueLabel.setText("Visibilidad (0 to 1), ahora: "+prefs.virtualBlocksAlpha);
        countdownMaxValueLabel.setText("Countdown (0 a 10), ahora: "+prefs.countdownMax);
    }

    private void saveSettings() {
        GamePreferences prefs = GamePreferences.instance;
        prefs.load();
        prefs.actionSubmit = chkActionSubmit.isChecked();
        prefs.countdownMax = sldCountdownMax.getValue();
        prefs.virtualBlocksAlpha = sldVirtualBlocksAlpha.getValue();
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
        SequenceAction seq = sequence();
        if (visible) seq.addAction(delay(delayOptionsButton + moveDuration));
        seq.addAction(run(new Runnable() {
            public void run () {
                btnMenuPlay.setTouchable(touchEnabled);
                btnMenuOptions.setTouchable(touchEnabled);
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