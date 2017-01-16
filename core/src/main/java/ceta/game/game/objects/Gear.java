package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    public Gear (){
        regTex = Assets.instance.background.gearBlue1;
        init();
        initRandom();

    }

    public Gear (float x, float y){
        regTex = Assets.instance.background.gearYellow2;
        init();
        rotation = 0;
        rotationSpeed = 10;
        this.setScale(2);
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
        float scale = MathUtils.random(0.7f,2.2f);
        this.setScale(scale,scale);
        this.setPosition(MathUtils.random(0,Constants.VIEWPORT_WIDTH),MathUtils.random(0,Constants.VIEWPORT_HEIGHT/3));
        transparency =  MathUtils.random(0.4f,0.9f);

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


    @Override
    public void act(float delta) {
        super.act(delta);
        rotation-=(delta*rotationSpeed);
        rotation = rotation%360;
    }
}
