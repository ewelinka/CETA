package ceta.game.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

/**
 * Created by ewe on 1/9/17.
 */
public class TreeGear extends Actor {
    public static final String TAG = TreeGear.class.getName();

    public TextureRegion regTex;
    private boolean isActive;
    private float gearRotation;

    public TreeGear (TextureAtlas.AtlasRegion region) {
        regTex = region;
        setOrigin(regTex.getRegionWidth()/2,regTex.getRegionHeight()/2);
        setColor(Color.WHITE);
        setActive(true);
        gearRotation = 0;
        setSize(regTex.getRegionWidth(),regTex.getRegionHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
       // Gdx.app.log(TAG, "gear act "+delta+" is active?? "+isActive);
        if(isActive){
            gearRotation-=(delta*100);
            gearRotation = gearRotation%360;
           // Gdx.app.log(TAG, "gear rot "+gearRotation);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Gdx.app.log(TAG, "this.getColor()  "+ this.getColor());
        //batch.setProjectionMatrix(camera.combined);
        // batch.draw(regTex,this.getX(),this.getY());
        batch.setColor(this.getColor());
        //batch.draw(regTex,this.getX(),this.getY());

        batch.draw(regTex.getTexture(),
                this.getX(), this.getY(),
                this.getOriginX(), this.getOriginY(),
                this.getWidth() ,this.getHeight(),
                this.getScaleX(), this.getScaleY(),
                gearRotation,
                regTex.getRegionX(), regTex.getRegionY(),
                regTex.getRegionWidth(), regTex.getRegionHeight(), false,false);
        batch.setColor(1,1,1,1);

    }

    public void setActive(boolean setIsActive){
        isActive = setIsActive;
        if(!isActive) {
           // Gdx.app.log(TAG, " set me gray ");
            setColor(Color.BLUE);
        }
    }
}
