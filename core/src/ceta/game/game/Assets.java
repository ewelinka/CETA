package ceta.game.game;

import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ewe on 7/25/16.
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    public AssetBruno bruno;
    public AssetLatter latter;
    public AssetLatterDouble latterDouble;
    public AssetBox box;
    public AssetCoin coin;
    // singleton: prevent instantiation from other classes
    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        // start loading assets and wait until finished
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
        // enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            //t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        }

        bruno = new AssetBruno(atlas);
        latter  = new AssetLatter(atlas);
        latterDouble = new AssetLatterDouble(atlas);
        box = new AssetBox(atlas);
        coin = new AssetCoin(atlas);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +  asset.fileName + "'", (Exception) throwable);
    }

    public class AssetBox {
        public final TextureAtlas.AtlasRegion box;

        public AssetBox (TextureAtlas atlas) {
            box = atlas.findRegion("box");

        }
    }

    public class AssetBruno {
        public final TextureAtlas.AtlasRegion body;

        public AssetBruno (TextureAtlas atlas) {
            body = atlas.findRegion("bruno");

        }
    }

    public class AssetLatter {
        public final TextureAtlas.AtlasRegion latter;

        public AssetLatter (TextureAtlas atlas) {
            latter = atlas.findRegion("latter");

        }
    }

    public class AssetLatterDouble {
        public final TextureAtlas.AtlasRegion latter;

        public AssetLatterDouble (TextureAtlas atlas) {
            latter = atlas.findRegion("latter-double");

        }
    }

    public class AssetCoin{
        public final TextureAtlas.AtlasRegion coin;

        public AssetCoin (TextureAtlas atlas) {
            coin = atlas.findRegion("circle");

        }
    }
}
