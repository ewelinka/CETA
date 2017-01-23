package ceta.game.game;

import ceta.game.util.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
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
    public AssetBox box;
    public AssetCoin coin;
    public AssetBackground background;
    public AssetObjectsToCollect toCollect;
    public AssetRoboticParts roboticParts;
    public AssetFeedback feedback;
    public AssetSounds sounds;
    public AssetMusic music;
    public AssetFinishBackground finishBackGround;
    public AssetMenu menu;
    public AssetButtons buttons;
    public AssetTree tree;

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
        assetManager.load("sounds/yuju.mp3", Sound.class);
        assetManager.load("sounds/pickup_coin.wav", Sound.class);
//        assetManager.load("sounds/pickup_feather.wav", Sound.class);
        assetManager.load("sounds/live_lost.wav", Sound.class);
        assetManager.load("sounds/buzz.wav", Sound.class);
        assetManager.load("sounds/meteroid.wav", Sound.class);
        assetManager.load("sounds/1.mp3", Sound.class);
        assetManager.load("sounds/2.mp3", Sound.class);
        assetManager.load("sounds/3.mp3", Sound.class);
        assetManager.load("sounds/4.mp3", Sound.class);
        assetManager.load("sounds/5.mp3", Sound.class);
        assetManager.load("sounds/muchas.mp3", Sound.class);
        assetManager.load("sounds/pocas.mp3", Sound.class);
        assetManager.load("sounds/quita.mp3", Sound.class);
        // load music
        assetManager.load("music/song1.mp3", Music.class);

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
        box = new AssetBox(atlas);
        coin = new AssetCoin(atlas);
        background = new AssetBackground(atlas);
        feedback = new AssetFeedback(atlas);
        sounds = new AssetSounds(assetManager);
        music = new AssetMusic(assetManager);
        toCollect = new AssetObjectsToCollect(atlas);
        roboticParts = new AssetRoboticParts(atlas);

        finishBackGround = new AssetFinishBackground(atlas);
        menu = new AssetMenu(atlas);
        buttons = new AssetButtons(atlas);
        tree = new AssetTree(atlas);
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
            defaultSmall = new BitmapFont(Gdx.files.internal("fonts/YuantiBold-24.fnt"), false);
            defaultNormal = new BitmapFont(Gdx.files.internal("fonts/silom-32.fnt"), false);
            defaultBig = new BitmapFont(Gdx.files.internal("fonts/silom-32.fnt"), false);
            // set font sizes
            defaultSmall.getData().setScale(1.0f);
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
        public final TextureAtlas.AtlasRegion box2;
        public final TextureAtlas.AtlasRegion box3;
        public final TextureAtlas.AtlasRegion box4;
        public final TextureAtlas.AtlasRegion box5;

        public AssetBox (TextureAtlas atlas) {
            box = atlas.findRegion("1");
            box2 = atlas.findRegion("2");
            box3 = atlas.findRegion("3");
            box4 = atlas.findRegion("4");
            box5 = atlas.findRegion("5");
        }
    }


    public class AssetFeedback {
        public final TextureAtlas.AtlasRegion hand;
        public final TextureAtlas.AtlasRegion wheel;

        public AssetFeedback (TextureAtlas atlas) {
            hand = atlas.findRegion("mano");
            wheel = atlas.findRegion("wheel");

        }
    }

    public class AssetBackground {
        public final TextureAtlas.AtlasRegion back;
        public final TextureAtlas.AtlasRegion back2;
        public final TextureAtlas.AtlasRegion back3;
        public final TextureAtlas.AtlasRegion backStart;
        public final TextureAtlas.AtlasRegion piecesBox;

        public final TextureAtlas.AtlasRegion blocksTablet;
        public final TextureAtlas.AtlasRegion feedbackZoneTablet;
        public final TextureAtlas.AtlasRegion feedbackZoneCV;
        public final TextureAtlas.AtlasRegion feedbackZoneV1CV;
        public final TextureAtlas.AtlasRegion feedbackZoneV1Tablet;

        public final TextureAtlas.AtlasRegion gearYellow1;
        public final TextureAtlas.AtlasRegion gearYellow2;
        public final TextureAtlas.AtlasRegion gearYellow3;
        public final TextureAtlas.AtlasRegion gearViolet1;
        public final TextureAtlas.AtlasRegion gearViolet2;
        public final TextureAtlas.AtlasRegion gearViolet3;
        public final TextureAtlas.AtlasRegion gearGray1;
        public final TextureAtlas.AtlasRegion gearGray2;
        public final TextureAtlas.AtlasRegion gearGray3;
        public final TextureAtlas.AtlasRegion gearGray4;
        public final TextureAtlas.AtlasRegion gearBlue1;
        public final TextureAtlas.AtlasRegion cloud1;
        public final TextureAtlas.AtlasRegion cloud2;
        public final TextureAtlas.AtlasRegion cloud3;
        public final TextureAtlas.AtlasRegion cloud4;
        public final TextureAtlas.AtlasRegion cloud5;

        public final TextureAtlas.AtlasRegion cityO1;
        public final TextureAtlas.AtlasRegion cityO2;



        public AssetBackground (TextureAtlas atlas) {
            back = atlas.findRegion("fondoC");
            back2 = atlas.findRegion("fondoN");
            back3 = atlas.findRegion("fondoV");
            backStart = atlas.findRegion("inicio");
            piecesBox = atlas.findRegion("parteB");
            blocksTablet = atlas.findRegion("blocksZoneTablet");
            feedbackZoneTablet= atlas.findRegion("feedbackTablet");
            feedbackZoneCV = atlas.findRegion("feedbackZone360x360") ;
            feedbackZoneV1CV = atlas.findRegion("workZoneV2");
            feedbackZoneV1Tablet = atlas.findRegion("workZoneV2tablet2");
            gearYellow1  = atlas.findRegion("doradoA");
            gearYellow2  = atlas.findRegion("doradoB");
            gearYellow3  = atlas.findRegion("doradoC");
            gearViolet1 = atlas.findRegion("violeta");
            gearViolet2 = atlas.findRegion("violetaB");
            gearViolet3 = atlas.findRegion("violetaD");
            gearGray1 = atlas.findRegion("engrana01");
            gearGray2 = atlas.findRegion("engrana02");
            gearGray3 = atlas.findRegion("engrana03");
            gearGray4 = atlas.findRegion("engrana04");
            gearBlue1 = atlas.findRegion("azul");
            cloud1 = atlas.findRegion("nubeA");
            cloud2 = atlas.findRegion("nubeB");
            cloud3 = atlas.findRegion("nubeC");
            cloud4 = atlas.findRegion("nubeD");
            cloud5 = atlas.findRegion("nubeE");
            cityO1 = atlas.findRegion("cityO1");
            cityO2 = atlas.findRegion("cityO2");


        }
    }

    public class AssetBruno {
        public final TextureAtlas.AtlasRegion body;
        public final TextureAtlas.AtlasRegion body01;
        public final TextureAtlas.AtlasRegion body01head;
        public final TextureAtlas.AtlasRegion body01body;
        public final TextureAtlas.AtlasRegion body02;
        public final TextureAtlas.AtlasRegion body02head;
        public final TextureAtlas.AtlasRegion body02body;
        public final TextureAtlas.AtlasRegion body03;
        public final TextureAtlas.AtlasRegion body03head;
        public final TextureAtlas.AtlasRegion body03body;
        public final TextureAtlas.AtlasRegion body04;
        public final TextureAtlas.AtlasRegion body04head;
        public final TextureAtlas.AtlasRegion body04body;
        public final TextureAtlas.AtlasRegion body05;
        public final TextureAtlas.AtlasRegion body05head;
        public final TextureAtlas.AtlasRegion body05body;
        public final TextureAtlas.AtlasRegion headEnergy;
        public final TextureAtlas.AtlasRegion baseEnergy;
        public final TextureAtlas.AtlasRegion energy;
        public final Animation walk;
        public final TextureAtlas.AtlasRegion walkHead;
        public final TextureAtlas.AtlasRegion still;
        public final TextureAtlas.AtlasRegion mega;
        public final TextureAtlas.AtlasRegion mega2;
        public final TextureAtlas.AtlasRegion thoraxEnergy;
        public final TextureAtlas.AtlasRegion jetPack;
        public final TextureAtlas.AtlasRegion fire;
        public final TextureAtlas.AtlasRegion initHead;
        public final TextureAtlas.AtlasRegion initBody;


        public AssetBruno (TextureAtlas atlas) {
            body = atlas.findRegion("robot02");
            body01 = atlas.findRegion("01bruno");
            body01head = atlas.findRegion("01cabeza");
            body01body = atlas.findRegion("01cuerpo");
            body02 = atlas.findRegion("02bruno");
            body02head = atlas.findRegion("02cabeza");
            body02body = atlas.findRegion("02cuerpo");
            body03 = atlas.findRegion("03bruno");
            body03head = atlas.findRegion("03cabeza");
            body03body = atlas.findRegion("03cuerpo");
            body04 = atlas.findRegion("04bruno");
            body04head = atlas.findRegion("04cabeza");
            body04body = atlas.findRegion("04cuerpo");
            body05 = atlas.findRegion("05bruno");
            body05head = atlas.findRegion("05cabeza");
            body05body = atlas.findRegion("05cuerpo");
            headEnergy = atlas.findRegion("03cabeza");
            baseEnergy = atlas.findRegion("baseEnergia");
            thoraxEnergy = atlas.findRegion("toraxEnergia");
            energy = atlas.findRegion("energia");
            walkHead = atlas.findRegion("caminaCabeza");
            still = atlas.findRegion("camina_parado");
            mega = atlas.findRegion("mega");
            mega2 = atlas.findRegion("mega2");
            jetPack = atlas.findRegion("jetpack");
            fire = atlas.findRegion("fuegoJetpack");
            initHead  = atlas.findRegion("brunoGrande_cabeza");
            initBody  = atlas.findRegion("brunoGrande_cuerpo");

            Array<TextureAtlas.AtlasRegion> regions = null;
            regions = atlas.findRegions("camina");
            walk = new Animation(0.3f , regions);



        }
    }


    public class AssetMenu {
        public final TextureAtlas.AtlasRegion play;


        public AssetMenu (TextureAtlas atlas) {
            play = atlas.findRegion("jugar");


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
        public final TextureAtlas.AtlasRegion price1;
        public final TextureAtlas.AtlasRegion price2;
        public final TextureAtlas.AtlasRegion price3;
        public final TextureAtlas.AtlasRegion price4;


        public AssetObjectsToCollect (TextureAtlas atlas) {
            coin = atlas.findRegion("coin");
            screw = atlas.findRegion("screw");
            price1 = atlas.findRegion("premio1");
            price2 = atlas.findRegion("premio2");
            price3 = atlas.findRegion("premio3");
            price4 = atlas.findRegion("premio4");


        }
    }

    public class AssetRoboticParts {
        public final TextureAtlas.AtlasRegion copperFitting1;
        public final TextureAtlas.AtlasRegion copperFitting2;
        public final TextureAtlas.AtlasRegion copperFitting3;
        public final TextureAtlas.AtlasRegion copperFitting4;
        public final TextureAtlas.AtlasRegion copperFitting5;
        public final TextureAtlas.AtlasRegion tubeUnit;
        public final TextureAtlas.AtlasRegion finalTube;
        public final TextureAtlas.AtlasRegion mask;
        public final TextureAtlas.AtlasRegion maskArm;
        public final TextureAtlas.AtlasRegion tubeVertical;

//        public final Animation animatedOneIn;
//        public final Animation animatedOneOut;
//        public final Animation animatedOneLoop;


        public AssetRoboticParts (TextureAtlas atlas) {
            copperFitting1 = atlas.findRegion("tubo40");
            copperFitting2 = atlas.findRegion("tubo80");
            copperFitting3 = atlas.findRegion("tubo120");
            copperFitting4 = atlas.findRegion("tubo160");
            copperFitting5 = atlas.findRegion("tubo200");
            tubeUnit = atlas.findRegion("tubo");
            finalTube = atlas.findRegion("tubo_final");
            mask = atlas.findRegion("mega_mascara");
            maskArm = atlas.findRegion("mega_brazo");
            tubeVertical = atlas.findRegion("tuboVertical2");


//            Array<TextureAtlas.AtlasRegion> regions = null;
//            regions = atlas.findRegions("boxA");
//            animatedOneLoop = new Animation(1.0f , regions,Animation.PlayMode.LOOP_PINGPONG);
//            regions = atlas.findRegions("boxA");
//            animatedOneIn = new Animation(1.0f, regions);
//            regions = atlas.findRegions("boxA");
//            animatedOneOut = new Animation(1.0f , regions, Animation.PlayMode.REVERSED);




        }
    }

    public class AssetFinishBackground{
        public final TextureAtlas.AtlasRegion finishBack;
        public final TextureAtlas.AtlasRegion excellentWork;
        public final TextureAtlas.AtlasRegion thumbUp;

        public AssetFinishBackground(TextureAtlas atlas) {
            finishBack = atlas.findRegion("engranajes");
            excellentWork = atlas.findRegion("excelentetrabajo");
            thumbUp = atlas.findRegion("manitoarriba");

        }
    }

    public class AssetTree{
        public final TextureAtlas.AtlasRegion tree;
        public final TextureAtlas.AtlasRegion gear1;
        public final TextureAtlas.AtlasRegion gear2;
        public final TextureAtlas.AtlasRegion gear3;
        public final TextureAtlas.AtlasRegion gear4;
        public final TextureAtlas.AtlasRegion gear5;
        public final TextureAtlas.AtlasRegion gear1inactive;
        public final TextureAtlas.AtlasRegion gear2inactive;
        public final TextureAtlas.AtlasRegion gear3inactive;
        public final TextureAtlas.AtlasRegion gear4inactive;
        public final TextureAtlas.AtlasRegion gear5inactive;
        public final TextureAtlas.AtlasRegion arrow;

        public  AssetTree(TextureAtlas atlas) {
            tree = atlas.findRegion("arbol-guia"); // is smaller but we will scale up
            gear1 = atlas.findRegion("engranaje");
            gear2 = atlas.findRegion("engranaje1");
            gear3 = atlas.findRegion("engranaje2");
            gear4 = atlas.findRegion("engranajes3");
            gear5 = atlas.findRegion("engranajes4");
            arrow = atlas.findRegion("flecha");
            gear1inactive = atlas.findRegion("faltante");
            gear2inactive = atlas.findRegion("faltante1");
            gear3inactive = atlas.findRegion("faltante2");
            gear4inactive = atlas.findRegion("faltante3");
            gear5inactive = atlas.findRegion("faltante4");


        }

    }

    public class AssetButtons{
        public final ImageButton.ImageButtonStyle playButtonStyle;
        public final ImageButton.ImageButtonStyle levelsButtonStyle;
        public final ImageButton.ImageButtonStyle exitButtonStyle;
        public final ImageButton.ImageButtonStyle understoodButtonStyle;

        public  AssetButtons(TextureAtlas atlas) {
            playButtonStyle = new ImageButton.ImageButtonStyle();  //Instaciate
            playButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("jugar-1")); //Set image for not pressed button
            playButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("jugar-2"));  //Set image for pressed
            playButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("jugar-2"));

            levelsButtonStyle = new ImageButton.ImageButtonStyle();  //Instaciate
            levelsButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("niveles-1")); //Set image for not pressed button
            levelsButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("niveles-2"));  //Set image for pressed
            levelsButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("niveles-2"));

            exitButtonStyle = new ImageButton.ImageButtonStyle();  //Instaciate
            exitButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("salir-1")); //Set image for not pressed button
            exitButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("salir-2"));  //Set image for pressed
            exitButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("salir-2"));

            understoodButtonStyle = new ImageButton.ImageButtonStyle();  //Instaciate
            understoodButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("entendido-1")); //Set image for not pressed button
            understoodButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("entendido-2"));  //Set image for pressed
            understoodButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("entendido-2"));

        }

    }

    public class AssetSounds {
//        public final Sound jump;
//        public final Sound jumpWithFeather;
        public final Sound pickupCoin;
//        public final Sound pickupFeather;
        public final Sound liveLost;
        public final Sound buzz;

        public final Sound one;
        public final Sound two;
        public final Sound three;
        public final Sound four;
        public final Sound five;
        public final Sound plus;

        public final Sound tooMuch;
        public final Sound tooFew;
        public final Sound cleanTable;


        public AssetSounds (AssetManager am) {
//            jump = am.get("sounds/jump.wav", Sound.class);
//            jumpWithFeather = am.get("sounds/jump_with_feather.wav", Sound.class);
            //pickupCoin = am.get("sounds/pickup_coin.wav", Sound.class);
            pickupCoin = am.get("sounds/yuju.mp3", Sound.class);
//            pickupFeather = am.get("sounds/pickup_feather.wav", Sound.class);
            liveLost = am.get("sounds/live_lost.wav", Sound.class);
           // buzz = am.get("sounds/buzz.wav", Sound.class);
            buzz = am.get("sounds/meteroid.wav", Sound.class);
            one = am.get("sounds/1.mp3", Sound.class);
            two = am.get("sounds/2.mp3", Sound.class);
            three = am.get("sounds/3.mp3", Sound.class);
            four = am.get("sounds/4.mp3", Sound.class);
            five = am.get("sounds/5.mp3", Sound.class);
            plus = am.get("sounds/buzz.wav", Sound.class);

            tooMuch = am.get("sounds/muchas.mp3", Sound.class);
            tooFew = am.get("sounds/pocas.mp3", Sound.class);
            cleanTable = am.get("sounds/quita.mp3", Sound.class);

        }
    }
    public class AssetMusic {
        public final Music song01;

        public AssetMusic (AssetManager am) {
            song01 = am.get("music/song1.mp3", Music.class);

        }
    }
}
