package ceta.game.game.levels;

import ceta.game.game.Assets;
import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Coin;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1 extends Level{
    public static final String TAG = Test.class.getName();
//    public BigBruno bruno;
//    public Coin coin;
    private Stage stage;
    private Image back;

    public Level1(Stage stage){
        this.stage = stage;
        init();
    };

    @Override
    public void init() {

        TextureRegion regTexBack = Assets.instance.background.back;
        back = new Image(regTexBack);
        back.setPosition(-Constants.VIEWPORT_WIDTH/2, 0);
        stage.addActor(back);

        bruno = new Bruno();
        bruno.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X ,0);
        stage.addActor(bruno);
        //TODO change to robotic stuff
        coin = new Coin();
        coin.setPosition(bruno.getX()+ (int)(MathUtils.random(1,10))*Constants.BASE, Constants.BASE );
        coin.setVelocity((short)0);
        stage.addActor(coin);
    }

    @Override
    public void update(float deltaTime) {
        stage.act(deltaTime);

    }

    @Override
    public void render(SpriteBatch batch) {
        // Sets the clear screen color to: Cornflower Blue
        Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);
        // Clears the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }


}
