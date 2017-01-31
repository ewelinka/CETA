package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.managers.BrunosManager;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 12/29/16.
 */
public class BrunoJetPack extends BrunoVertical {
    private int jetPackXadjust, fireXadjust;
    private TextureRegion fire;
    private float fireYScale, fireXScale, oscillationY;
    private int fireYDirection, fireXDirection, oscillationYDirection;
    private float maxYFlame;
    private int inclination;



    public BrunoJetPack(int value, BrunosManager brunosManager) {
        super(value, brunosManager);
        regTex = Assets.instance.bruno.jetPack;
        jetPackXadjust = regTex.getRegionWidth()-10;
        fire = Assets.instance.bruno.fire;
        fireXadjust = 5;
        fireYScale = fireXScale = 0.5f;
        fireYDirection = fireXDirection = oscillationYDirection =1;
        oscillationY =0;
        inclination = -8;

    }

    @Override
    public void act(float delta) {
        super.act(delta);

        float maxMagnitude = (getY() - Constants.GROUND_LEVEL + 20)/200;
        maxYFlame = 0.5f + maxMagnitude;
       // Gdx.app.log(TAG," max flame "+maxYFlame+" maxMagnitude " +maxMagnitude +" fireScale "+fireYScale + " fireDirection "+fireYDirection);
        //Gdx.app.log(TAG," ad "+((maxMagnitude*10+1)/1000));
        fireYScale += (delta*fireYDirection+((maxMagnitude*10+1)/1000)*fireYDirection);
        if(fireYScale > maxYFlame )
            fireYDirection=-1;
        if(fireYScale < maxYFlame/2)
            fireYDirection = 1;

        fireXScale += (delta*fireXDirection);

        if(fireXScale > 1.2f)
            fireXDirection = -1;
        if(fireXScale < 0.7f)
            fireXDirection = 1;


        oscillationY +=(delta*oscillationYDirection*8);
        if(oscillationY > 4)
            oscillationYDirection = -1;
        if(oscillationY < -4)
            oscillationYDirection = 1;


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Gdx.app.log(TAG, "draw baby draw");
        //batch.setProjectionMatrix(camera.combined);
        // batch.draw(regTex,this.getX(),this.getY());

        batch.setColor(1, 1, 1, 1);
        batch.draw(brunoBodyReg.getTexture(),
                this.getX() + offsetX , this.getY() + offsetY +oscillationY,
                this.getOriginX(), this.getOriginY(),
                brunoBodyReg.getRegionWidth() + 4, brunoBodyReg.getRegionHeight(),
                this.getScaleX(), this.getScaleY(),
                inclination,
                brunoBodyReg.getRegionX(), brunoBodyReg.getRegionY(),
                brunoBodyReg.getRegionWidth(), brunoBodyReg.getRegionHeight(), lookingLeft, false);

        batch.draw(brunoHeadReg.getTexture(),
                this.getX() + offsetX, this.getY() + brunoBodyReg.getRegionHeight() - 3 + offsetY +oscillationY, // -3  = head Y offset
                0, 0,
                brunoHeadReg.getRegionWidth(), brunoHeadReg.getRegionHeight(),
                this.getScaleX(), this.getScaleY(),
                this.getRotation()+inclination,
                brunoHeadReg.getRegionX(), brunoHeadReg.getRegionY(),
                brunoHeadReg.getRegionWidth(), brunoHeadReg.getRegionHeight(), lookingLeft, false);

        batch.draw(regTex.getTexture(), //jetpack
                this.getX() + offsetX - jetPackXadjust, this.getY()  + offsetY +oscillationY,
                0, 0,
                regTex.getRegionWidth(), regTex.getRegionHeight(),
                this.getScaleX(), this.getScaleY(),
                0,
                regTex.getRegionX(), regTex.getRegionY(),
                regTex.getRegionWidth(), regTex.getRegionHeight(), lookingLeft, false);

        batch.draw(fire.getTexture(),
                this.getX() + offsetX - jetPackXadjust+ fireXadjust, this.getY()  + offsetY - fire.getRegionHeight()+oscillationY,
                fire.getRegionWidth()/2, fire.getRegionHeight(),
                fire.getRegionWidth(), fire.getRegionHeight(),
                fireXScale, fireYScale,
                0,
                fire.getRegionX(), fire.getRegionY(),
                fire.getRegionWidth(), fire.getRegionHeight(), lookingLeft, false);



        //


        offsetX = 0;
        offsetY = 0;


    }
}
