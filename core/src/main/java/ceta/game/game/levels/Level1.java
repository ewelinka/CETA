package ceta.game.game.levels;

import ceta.game.game.objects.Bruno;
import ceta.game.game.objects.Price;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Json;

/**
 * Created by ewe on 8/23/16.
 */
public class Level1 extends AbstractLevel {
    public static final String TAG = Level1.class.getName();
    private int levelNr;

    public Level1(Stage stage, int level){
        this.stage = stage;
        levelNr = level;
        init();
    };

    @Override
    public void init() {
        initLevelParams();

        bruno = new Bruno();
        bruno.setPosition(-Constants.VIEWPORT_WIDTH/2 + Constants.OFFSET_X ,0);
        // dafualt horizontal
        price = new Price((short)levelParams.priceVelocity,(short)levelParams.numberMin, levelParams.priceReturn);



        // add actors
        stage.addActor(bruno);
        stage.addActor(price);
    }

    @Override
    public void update(float deltaTime) {
        price.update(deltaTime);
        stage.act(deltaTime);
    }

    private void initLevelParams(){
        Json json = new Json();
        levelParams = json.fromJson(LevelParams.class, Gdx.files.internal(Constants.LEVELS_FOLDER+"/"+levelNr+".json"));
    }

    @Override
    public void render(SpriteBatch batch) {
        stage.draw();
    }



}
