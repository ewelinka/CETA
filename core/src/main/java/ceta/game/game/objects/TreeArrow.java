package ceta.game.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

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
        batch.setColor(getColor());
        batch.draw(regTex,this.getX(),this.getY()+arrowYoffset);
        batch.setColor(1,1,1,1);
    }
}
