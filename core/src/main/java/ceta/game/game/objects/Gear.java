package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by ewe on 1/12/17.
 */
public class Gear extends Actor {
    public TextureRegion regTex;
    private float rotation;
    private int rotationSpeed;
    private float transparency;
    private int nr;
    private TextureAtlas.AtlasRegion [] gears = {Assets.instance.background.gearGray1,Assets.instance.background.gearGray2};

    public Gear (int nr){
        this.nr = nr;
        regTex = gears[nr%2];
        init();
        initRandom();

    }

    public Gear (float x, float y){
        regTex = Assets.instance.background.gearGray1;
        init();
        rotation = 0;
        rotationSpeed = 0;
        this.setScale(1);
        this.setPosition(x,y);
        transparency = 1;
    }


    private void init(){

        this.setSize(regTex.getRegionWidth(),regTex.getRegionHeight());
        this.setOrigin(this.getWidth()/2, this.getHeight()/ 2);


    }

    private void initRandom(){
        rotationSpeed = MathUtils.random(10,100);
        rotation = MathUtils.random(360);
        this.setRotation(rotation);
        setScale(MathUtils.random(0.7f,1.0f));
        this.setPosition(nr *90 +MathUtils.random(10,20),MathUtils.random(10,100));
        transparency =  MathUtils.random(0.8f,1.0f);

    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.setProjectionMatrix(camera.combined);
        // batch.draw(regTex,this.getX(),this.getY());
        batch.setColor(1,1,1,transparency*parentAlpha);
        batch.draw(regTex.getTexture(),
                this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth() ,this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                rotation,
                regTex.getRegionX(), regTex.getRegionY(),
                regTex.getRegionWidth(), regTex.getRegionHeight(), false,false);
        batch.setColor(1,1,1,1);

    }

    public void setRotationSpeed(int nowRotationSpeed){
        rotationSpeed = nowRotationSpeed;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        rotation-=(delta*rotationSpeed);
        rotation = rotation%360;
    }
}
