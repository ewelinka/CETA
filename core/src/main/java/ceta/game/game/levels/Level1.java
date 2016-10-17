package ceta.game.game.levels;

import ceta.game.game.Assets;
import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1 extends Level{
    public static final String TAG = Test.class.getName();
//    public BigBruno bruno;
//    public Coin coin;
//    private Stage stage;
    private Image back;

    public Level1(Stage stage){
        this.stage = stage;
        init();
    };

    @Override
    public void init() {

        initLevelParams();
        back = new Image(Assets.instance.background.back);
       // back.setPosition(-Constants.VIEWPORT_WIDTH/2, 0);
        back.setPosition(-Constants.VIEWPORT_WIDTH/2, -Constants.VIEWPORT_HEIGHT/2 + (Constants.VIEWPORT_HEIGHT - back.getHeight()));
        stage.addActor(back);


        bruno = new Bruno();
        bruno.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X ,0);
        stage.addActor(bruno);
        //TODO change to robotic stuff
        price = new Price();
        price.setStartAndEnd((short)0,(short)10);
        price.setNewPosition(bruno.getX()+bruno.getWidth());
        price.setVelocity((short)-100);
        stage.addActor(price);
    }

    @Override
    public void update(float deltaTime) {
        price.update(deltaTime);
        stage.act(deltaTime);

    }

    private void initLevelParams(){
        Json json = new Json();
        levelParams = json.fromJson(LevelParams.class, Gdx.files.internal(Constants.LEVELS_FOLDER+"/1.json"));
    }

    @Override
    public void render(SpriteBatch batch) {
        // Sets the clear screen color to: Cornflower Blue
       // Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f,0xed / 255.0f, 0xff / 255.0f);

        stage.draw();
    }


}
