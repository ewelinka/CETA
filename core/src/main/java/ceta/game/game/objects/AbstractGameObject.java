package ceta.game.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
//import com.badlogic.gdx.physics.box2d.Body;


/**
 * Created by ewe on 7/25/16.
 */
public abstract class AbstractGameObject extends Actor {
    public static final String TAG = AbstractGameObject.class.getName();

    public Rectangle bounds;
    public TextureRegion regTex;
    public float rotation;

    public Animation animation;
    public float stateTime;
    public float terminalX;
    public float terminalY;
    protected float offsetX = 0;
    protected float offsetY = 0;


    public AbstractGameObject () {
        bounds = new Rectangle();

    }

    public void init(){
        // Center image on game object
        this.setOrigin(this.getWidth()/2, this.getHeight()/ 2);
        //Gdx.app.log(TAG,this.getWidth()+" "+this.getHeight());
        this.setScale(1,1);
        rotation = 0;
        this.setRotation(0);
        // Bounding box for collision detection!!
        bounds.set(0, 0, this.getWidth(), this.getHeight());
        this.setBounds(this.getX(),this.getY(),this.getWidth(),this.getHeight());
        this.setDebug(false);


    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        //batch.setProjectionMatrix(camera.combined);
       // batch.draw(regTex,this.getX(),this.getY());
        batch.setColor(this.getColor());
        batch.draw(regTex.getTexture(),
                this.getX()+offsetX, this.getY()+offsetY,
                this.getOriginX(), this.getOriginY(),
                this.getWidth() ,this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation(),
                regTex.getRegionX(), regTex.getRegionY(),
                regTex.getRegionWidth(), regTex.getRegionHeight(), false,false);
        batch.setColor(1,1,1,1);

        offsetX = 0;
        offsetY = 0;

    }

    public void setAnimation (Animation animation) {
        this.animation = animation;
        stateTime = 0;
    }

    @Override
    public void act(float delta) {

        stateTime += delta;
        super.act(delta);
    }

    public float getTerminalX(){
        return terminalX;
    }

    public void setTerminalX(float newTerminalX){
        terminalX = newTerminalX;
    }
    public float getTerminalY(){
        return terminalY;
    }

    public void setTerminalY(float newTerminalY){
        terminalY = newTerminalY;
    }

    public void shake(){

        long shakeAlpha = System.currentTimeMillis() % 360;
        float shakeDist = 1.5f;
        offsetX += MathUtils.sinDeg(shakeAlpha * 2.2f) * shakeDist;
        offsetY += MathUtils.sinDeg(shakeAlpha * 2.9f) * shakeDist;

    }





}
