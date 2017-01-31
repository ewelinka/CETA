package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by ewe on 1/16/17.
 */
public class City {
    public static final String TAG = City.class.getName();
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private TextureAtlas.AtlasRegion cloud;
    private float cloudX;
    private int cloudSpeed;

    private TextureAtlas.AtlasRegion cityHalf;
    private float cityX;
    private float cityX2;
    private int citySpeed;

    private TextureAtlas.AtlasRegion cityFrontHalf;
    private float cityFrontX;
    private float cityFrontX2;
    private int cityFrontSpeed;

    private int colorRectHeight;

    public City(SpriteBatch batch, OrthographicCamera camera){
        this.batch = batch;
        this.camera = camera;
        cloud = Assets.instance.background.cloud1;
        cloudX = 0;
        cloudSpeed = 35;

        cityHalf = Assets.instance.background.cityO1;
        cityX = 0;
        cityX2 = cityX + cityHalf.getRegionWidth();
        citySpeed =40;

        cityFrontHalf = Assets.instance.background.cityO2;
        cityFrontX = 0;
        cityFrontX2 = cityFrontX + cityFrontHalf.getRegionWidth()-5;
        cityFrontSpeed = 55;

        colorRectHeight = 200;

    }

    public void update (float deltaTime) {
        cloudX =  (cloudX+(deltaTime*cloudSpeed))%(Constants.VIEWPORT_WIDTH+100);
        cityX =  (cityX+(deltaTime*citySpeed))%(Constants.VIEWPORT_WIDTH+cityHalf.getRegionWidth());
        cityX2 =  (cityX2+(deltaTime*citySpeed))%(Constants.VIEWPORT_WIDTH+cityHalf.getRegionWidth());
        cityFrontX =  (cityFrontX+(deltaTime*cityFrontSpeed));
        cityFrontX2 =  (cityFrontX2+(deltaTime*cityFrontSpeed));
        if(cityFrontX >= 1240) //1240 = Constants.VIEWPORT_WIDTH +cityHalf.getRegionWidth()
            cityFrontX = -30;

        if(cityFrontX2 >= 1240)
            cityFrontX2 = -30;

        //Gdx.app.log(TAG," x "+cityFrontX + " x2 "+cityFrontX2);
    }

    public void drawAll(){
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        drawClouds();
        drawCity();
        drawFrontCity();
        drawFillRect();
        batch.end();

    }

    private void drawClouds(){
        drawOneCloud(300 - cloudX, 390, 1);
        drawOneCloud(300 - cloudX, 270, 1.3f);
        drawOneCloud(300 - cloudX, 330, 1.7f);
    }

    private void drawOneCloud(float x, float y, float scale){
        batch.draw(cloud.getTexture(),
                x*scale, y,
                0,0,
                cloud.getRegionWidth() , cloud.getRegionHeight(),
                scale, scale,
                0,
                cloud.getRegionX(), cloud.getRegionY(),
                cloud.getRegionWidth(), cloud.getRegionHeight(), false,false);
    }

    private void drawFillRect(){
//
//        batch.draw(cityHalf.getTexture(),
//                -Constants.VIEWPORT_WIDTH/2, Constants.DETECTION_ZONE_END,
//                Constants.VIEWPORT_WIDTH,200,
//                cityHalf.getRegionX()+10,cityHalf.getRegionY()+cityHalf.getRegionHeight()-10,10,10,false,false);

        batch.draw(cityFrontHalf.getTexture(),
                -Constants.VIEWPORT_WIDTH/2, Constants.GROUND_LEVEL,
                Constants.VIEWPORT_WIDTH,colorRectHeight,
                cityFrontHalf.getRegionX()+10,cityFrontHalf.getRegionY()+cityFrontHalf.getRegionHeight()-15,10,10,false,false);
    }

    private void drawCity(){
        drawCityPart(300 - cityX ,Constants.GROUND_LEVEL+colorRectHeight+15);
        drawCityPart(300 - cityX2,Constants.GROUND_LEVEL+colorRectHeight+15);


    }

    private void drawCityPart(float x, float y) {
        batch.draw(cityHalf.getTexture(),
                x, y,
                0,0,
                cityHalf.getRegionWidth() , cityHalf.getRegionHeight(),
                1, 1,
                0,
                cityHalf.getRegionX(), cityHalf.getRegionY(),
                cityHalf.getRegionWidth(), cityHalf.getRegionHeight(), false,false);

    }

    private void drawFrontCity(){
        drawFrontCityPart(300 - cityFrontX,Constants.GROUND_LEVEL+colorRectHeight);
        drawFrontCityPart(300 - cityFrontX2,Constants.GROUND_LEVEL+colorRectHeight);

    }

    private void drawFrontCityPart(float x, float y) {
        batch.draw(cityFrontHalf.getTexture(),
                x, y,
                0,0,
                cityFrontHalf.getRegionWidth() , cityFrontHalf.getRegionHeight(),
                1, 1,
                0,
                cityFrontHalf.getRegionX(), cityFrontHalf.getRegionY(),
                cityFrontHalf.getRegionWidth(), cityFrontHalf.getRegionHeight(), false,false);

    }



}
