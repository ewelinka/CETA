package ceta.game.game;

import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by ewe on 7/25/16.
 */
public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    private AssetManager assetManager;

    public AssetFonts fonts;

    public AssetBruno bruno;
    public AssetLatter latter;
    public AssetLatterDouble latterDouble;
    public AssetBox box;
    public AssetCoin coin;
    public AssetBackground background;
    public AssetObjectsToCollect toCollect;
    public AssetRoboticParts roboticParts;

    public AssetSounds sounds;
    public AssetMusic music;

    public AssetFinishBackground finishBackGround;

    // singleton: prevent instantiation from other classes
    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        // set asset manager error handler
        assetManager.setErrorListener(this);
        // load texture atlas
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
        // load sounds
//        assetManager.load("sounds/jump.wav", Sound.class);
//        assetManager.load("sounds/jump_with_feather.wav", Sound.class);
        assetManager.load("sounds/pickup_coin.wav", Sound.class);
//        assetManager.load("sounds/pickup_feather.wav", Sound.class);
//        assetManager.load("sounds/live_lost.wav", Sound.class);
        // load music
        assetManager.load("music/keith303_-_brand_new_highscore.mp3", Music.class);
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
        fonts = new AssetFonts();

        bruno = new AssetBruno(atlas);
        latter  = new AssetLatter(atlas);
        latterDouble = new AssetLatterDouble(atlas);
        box = new AssetBox(atlas);
        coin = new AssetCoin(atlas);
        background = new AssetBackground(atlas);
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);
        toCollect = new AssetObjectsToCollect(atlas);
        roboticParts = new AssetRoboticParts(atlas);

        finishBackGround = new AssetFinishBackground(atlas);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultSmall.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultBig.dispose();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" +  asset.fileName + "'", (Exception) throwable);
    }

    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;
        public AssetFonts () {
            // create three fonts using Libgdx's 15px bitmap font
            defaultSmall = new BitmapFont(Gdx.files.internal("fonts/silom-32.fnt"), false);
            defaultNormal = new BitmapFont(Gdx.files.internal("fonts/silom-32.fnt"), false);
            defaultBig = new BitmapFont(Gdx.files.internal("fonts/silom-32.fnt"), false);
            // set font sizes
            defaultSmall.getData().setScale(0.75f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(2.5f);
            // enable linear texture filtering for smooth fonts
            defaultSmall.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public class AssetBox {
        public final TextureAtlas.AtlasRegion box;

        public AssetBox (TextureAtlas atlas) {
            box = atlas.findRegion("box");

        }
    }

    public class AssetBackground {
        public final TextureAtlas.AtlasRegion back;
        public final TextureAtlas.AtlasRegion back2;
        public final TextureAtlas.AtlasRegion back3;


        public AssetBackground (TextureAtlas atlas) {
            back = atlas.findRegion("fondoC");
            back2 = atlas.findRegion("fondoA");
            back3 = atlas.findRegion("fondoB");

        }
    }

    public class AssetBruno {
        public final TextureAtlas.AtlasRegion body;

        public AssetBruno (TextureAtlas atlas) {
            body = atlas.findRegion("robot02");

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
            coin = atlas.findRegion("coin");

        }
    }

    public class AssetObjectsToCollect {
        public final TextureAtlas.AtlasRegion coin;
        public final TextureAtlas.AtlasRegion screw;


        public AssetObjectsToCollect (TextureAtlas atlas) {
            coin = atlas.findRegion("coin");
            screw = atlas.findRegion("screw");


        }
    }

    public class AssetRoboticParts {
        public final TextureAtlas.AtlasRegion copperFitting1;
        public final TextureAtlas.AtlasRegion copperFitting2;
        public final TextureAtlas.AtlasRegion copperFitting3;
        public final TextureAtlas.AtlasRegion copperFitting4;
        public final TextureAtlas.AtlasRegion copperFitting5;


        public AssetRoboticParts (TextureAtlas atlas) {
            copperFitting1 = atlas.findRegion("copper");
            copperFitting2 = atlas.findRegion("copper2");
            copperFitting3 = atlas.findRegion("copper3");
            copperFitting4 = atlas.findRegion("copper4");
            copperFitting5 = atlas.findRegion("copper5");



        }
    }

    public class AssetFinishBackground{
        public final TextureAtlas.AtlasRegion finishBack;

        public AssetFinishBackground(TextureAtlas atlas) {
            finishBack = atlas.findRegion("excelente");

        }
    }

    public class AssetSounds {
//        public final Sound jump;
//        public final Sound jumpWithFeather;
        public final Sound pickupCoin;
//        public final Sound pickupFeather;
//        public final Sound liveLost;
        public AssetSounds (AssetManager am) {
//            jump = am.get("sounds/jump.wav", Sound.class);
//            jumpWithFeather = am.get("sounds/jump_with_feather.wav", Sound.class);
            pickupCoin = am.get("sounds/pickup_coin.wav", Sound.class);
//            pickupFeather = am.get("sounds/pickup_feather.wav", Sound.class);
//            liveLost = am.get("sounds/live_lost.wav", Sound.class);
        }
    }
    public class AssetMusic {
        public final Music song01;
        public AssetMusic (AssetManager am) {
            song01 = am.get("music/keith303_-_brand_new_highscore.mp3", Music.class);
        }
    }
}
