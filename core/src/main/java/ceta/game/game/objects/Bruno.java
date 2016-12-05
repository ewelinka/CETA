package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

/**
 * Created by ewe on 7/26/16.
 */
public class Bruno extends AbstractGameObject {
    public static final String TAG = Bruno.class.getName();

    protected boolean lookingLeft;



    public Bruno () {
        init();
    }


    public void init () {
        regTex = Assets.instance.bruno.body05;
        this.setSize(Constants.BASE*2,Constants.BASE*6);
        superinit();
        lookingLeft = false;

    }

    protected void superinit(){
        super.init();

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
                regTex.getRegionWidth(), regTex.getRegionHeight(), lookingLeft,false);
        batch.setColor(1,1,1,1);

        offsetX = 0;
        offsetY = 0;
    }

    public void moveMeToAndSetTerminalX(float x, float y){
        //clearActions();
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setPosition(x,y);
        moveToAction.setDuration(1f);
        setTerminalX(x);

        addAction(moveToAction);
    }

    public void setLookingLeft(boolean isLeft){
        lookingLeft = isLeft;
    }


}
