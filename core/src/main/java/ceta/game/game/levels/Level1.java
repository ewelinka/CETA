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

        price = new Price();
        price.setVelocity((short)levelParams.priceVelocity);
        price.setStartAndEnd((short)levelParams.numberMin,(short)levelParams.numberMax);

        //TODO perhaps we should change more: size, position,...
        Gdx.app.log(TAG,"..." + levelParams.type+"...");
        if(levelParams.type.equals("horizontal")){
            Gdx.app.log(TAG,"...horizontal");
            price.setIsMovingVertical(true);
            if(levelParams.isDynamic) {
                Gdx.app.log(TAG,"...horizontal dynamic");
                price.setNewPosition(bruno.getX() + bruno.getWidth(), Constants.VIEWPORT_HEIGHT / 2 - price.getHeight());
            }
            else{
                Gdx.app.log(TAG,"... NOT dinamic");
                price.setNewPosition(bruno.getX()+bruno.getWidth(), Constants.BASE);
            }

        } else if (levelParams.type.equals("vertical")){
            Gdx.app.log(TAG,"...vertical");
            price.setIsMovingVertical(false);

        }

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
