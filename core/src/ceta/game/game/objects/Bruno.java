package ceta.game.game.objects;

import ceta.game.game.Assets;
import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by ewe on 7/26/16.
 */
public class Bruno extends AbstractGameObject {
    public static final String TAG = Bruno.class.getName();



    public Bruno () {
        init();
    }


    public void init () {
        regTex = Assets.instance.bruno.body;
        this.setSize(Constants.BASE,Constants.BASE*2);
        super.init();

    }


}
