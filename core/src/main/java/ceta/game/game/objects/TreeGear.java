package ceta.game.game.objects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by ewe on 1/9/17.
 */
public class TreeGear extends Actor {
    public static final String TAG = TreeGear.class.getName();

    private TextureRegion regTex, regTexActive, regTexInactive;
    private boolean isActive, isMoving;
    private float gearRotation;

    public TreeGear (TextureAtlas.AtlasRegion regionActive, TextureAtlas.AtlasRegion regionInactive) {
        regTex = regionActive;
        regTexInactive = regionInactive;
        regTexActive  =regionActive;
        setOrigin(regTex.getRegionWidth()/2,regTex.getRegionHeight()/2);
        setColor(Color.WHITE);
        setActive(true);
        //isMoving = false;
        gearRotation = 0;
        setSize(regTex.getRegionWidth(),regTex.getRegionHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
       // Gdx.app.log(TAG, "gear act "+delta+" is active?? "+isMoving);
        if(isMoving){
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

    public void activateGear(){
        //addAction(Actions.color(Color.WHITE,0.5f));
        setActive(true);
        setColor(1,1,1,0);
        isMoving = true;
        addAction(Actions.alpha(1,1.5f));
        //addAction(Actions.color(Color.WHITE,0.5f));
    }

    public void setIsMoving(final boolean moving, float delay){
        addAction(sequence(
                Actions.delay(delay),
                run(new Runnable() {
                    @Override
                    public void run() {
                        isMoving = moving;
                    }
                })
        ));

    }

    public void setActiveTexture(TextureRegion aTex){
        regTexActive = aTex;
    }


    public void setActive(boolean setIsActive){
        isActive = setIsActive;
        if(!isActive) {
            regTex = regTexInactive;
            isMoving = false;
           // setColor(Color.BLUE);
        }else{
            regTex  =regTexActive;
            isMoving = true;
        }
    }
}
