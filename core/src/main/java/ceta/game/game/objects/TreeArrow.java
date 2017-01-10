package ceta.game.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * Created by ewe on 1/10/17.
 */
public class TreeArrow extends Actor {
    public static final String TAG = TreeArrow.class.getName();
    private TextureRegion regTex;
    private float arrowYoffset;
    private int arrowMaxOffest;
    private int arrowGoingUp;

    public TreeArrow (TextureAtlas.AtlasRegion region) {
        regTex = region;
        arrowMaxOffest = 15;
        arrowYoffset = 0;
        arrowGoingUp = 1;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!hasActions()){ // we do not go up and down when the arrow is moving to next goal
            arrowYoffset+=(delta*10*arrowGoingUp);
            if(arrowYoffset > arrowMaxOffest)
                arrowGoingUp = -1;
            if(arrowYoffset < -arrowYoffset)
                arrowGoingUp = 1;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        //Gdx.app.log(TAG, "this.getColor()  "+ this.getColor());
        //batch.setProjectionMatrix(camera.combined);
        // batch.draw(regTex,this.getX(),this.getY());
        //batch.setColor(this.getColor());
        batch.draw(regTex,this.getX(),this.getY()+arrowYoffset);

//        batch.draw(regTex.getTexture(),
//                this.getX(), this.getY()+ arrowYoffset,
//                this.getOriginX(), this.getOriginY(),
//                this.getWidth() ,this.getHeight(),
//                this.getScaleX(), this.getScaleY(),
//                0,
//                regTex.getRegionX(), regTex.getRegionY(),
//                regTex.getRegionWidth(), regTex.getRegionHeight(), false,false);
//        batch.setColor(1,1,1,1);

    }
}
