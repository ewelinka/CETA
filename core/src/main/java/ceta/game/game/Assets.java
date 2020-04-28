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
    public AssetVirtualBlocks box;
    public AssetBruno bruno;
    public AssetBackground background;
    public AssetStaticBackground staticBackground;
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
        assetManager.load("positive/a1-iupi.mp3", Sound.class);
        assetManager.load("positive/a2-MuyBien.mp3", Sound.class);
        assetManager.load("positive/a3-Buenisimo.mp3", Sound.class);
        assetManager.load("positive/b2-AndasVolando.mp3", Sound.class);
        assetManager.load("positive/b3-QueBuenosAmigos.mp3", Sound.class);
        assetManager.load("positive/b4-BienPescado.mp3", Sound.class);
        assetManager.load("positive/b5-QueBuenSalto.mp3", Sound.class);
        assetManager.load("positive/b6-YaEstamosMasCerca.mp3", Sound.class);
        assetManager.load("positive/BrUNO+.mp3", Sound.class);
        assetManager.load("sounds/BrUNO-.mp3", Sound.class);
        assetManager.load("sounds/BrUNOneutro.mp3", Sound.class);
        assetManager.load("sounds/0.mp3", Sound.class);
        assetManager.load("sounds/1.mp3", Sound.class);
        assetManager.load("sounds/2.mp3", Sound.class);
        assetManager.load("sounds/3.mp3", Sound.class);
        assetManager.load("sounds/4.mp3", Sound.class);
        assetManager.load("sounds/5.mp3", Sound.class);
        assetManager.load("sounds/6.mp3", Sound.class);
        assetManager.load("sounds/7.mp3", Sound.class);
        assetManager.load("sounds/8.mp3", Sound.class);
        assetManager.load("sounds/9.mp3", Sound.class);
        assetManager.load("sounds/10.mp3", Sound.class);
        assetManager.load("sounds/11.mp3", Sound.class);
        assetManager.load("sounds/12.mp3", Sound.class);
        assetManager.load("sounds/13.mp3", Sound.class);
        assetManager.load("sounds/14.mp3", Sound.class);
        assetManager.load("sounds/15.mp3", Sound.class);
        assetManager.load("sounds/16.mp3", Sound.class);
        assetManager.load("sounds/17.mp3", Sound.class);
        assetManager.load("sounds/18.mp3", Sound.class);
        assetManager.load("sounds/19.mp3", Sound.class);
        assetManager.load("sounds/20.mp3", Sound.class);
        assetManager.load("sounds/21.mp3", Sound.class);
        assetManager.load("sounds/22.mp3", Sound.class);
        assetManager.load("sounds/23.mp3", Sound.class);
        assetManager.load("sounds/ProbaConMenosFichas.mp3", Sound.class);
        assetManager.load("sounds/ProbaConMasFichas.mp3", Sound.class);
        assetManager.load("sounds/TenesQueSacarLasFichas.mp3", Sound.class);
        // assetManager.load("sounds/yupii.mp3", Sound.class);
        assetManager.load("sounds/PasasteDeNIvel.mp3", Sound.class);
        assetManager.load("sounds/BuenTrabajo.mp3", Sound.class);
        assetManager.load("sounds/ExcelenteSeguimosAvanzando.mp3", Sound.class);
        assetManager.load("sounds/MuyBienProximoNivel.mp3", Sound.class);
        assetManager.load("sounds/VamosAHacerEsteNivelUNaVezMas.mp3", Sound.class);
        assetManager.load("sounds/HayQuePonerLasFichas.mp3", Music.class);

        assetManager.load("sounds/PreIntro.mp3", Sound.class);
        assetManager.load("sounds/erase.mp3", Music.class);
        assetManager.load("sounds/malvado.mp3", Music.class);
        assetManager.load("sounds/estirarse.mp3", Music.class);
        assetManager.load("sounds/agarrar.mp3", Music.class);
        assetManager.load("sounds/fichasRobo.mp3", Music.class);
        assetManager.load("sounds/GraciasFin.mp3", Music.class);
        assetManager.load("sounds/poniendoFichas.mp3", Music.class);
        assetManager.load("sounds/creepy-min.mp3", Music.class);
        assetManager.load("sounds/plus10.mp3", Sound.class);

        assetManager.load("island-intro/de1a2.mp3", Sound.class);
        assetManager.load("island-intro/de2a3.mp3", Sound.class);
        assetManager.load("island-intro/de3a4.mp3", Music.class);
        assetManager.load("island-intro/de4a5.mp3", Sound.class);
        assetManager.load("island-intro/de5a6.mp3", Sound.class);
        assetManager.load("island-intro/de6a7.mp3", Sound.class);

        // load music
        //assetManager.load("music/song1.mp3", Music.class);
        assetManager.load("music/1_CIUDAD.mp3", Music.class);
        assetManager.load("music/2_NUBES.mp3", Music.class);
        assetManager.load("music/3_TUBERIAS.mp3", Music.class);
        assetManager.load("music/4_SUBMARINO.mp3", Music.class);
        assetManager.load("music/5_RESORTES.mp3", Music.class);
        assetManager.load("music/6_BATALLAFINAL.mp3", Music.class);

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
        box = new AssetVirtualBlocks(atlas);
        background = new AssetBackground(atlas);
        staticBackground = new AssetStaticBackground(atlas);
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
        public final BitmapFont defaultNormal, defaultNumberLine;
        public final BitmapFont defaultBig;
        public AssetFonts () {
            // create three fonts using Libgdx's 15px bitmap fontNumberLine
            defaultNumberLine = new BitmapFont(Gdx.files.internal("fonts/YuantiBold-24.fnt"), false);
            defaultSmall = new BitmapFont(Gdx.files.internal("fonts/silom-32.fnt"), false);
            defaultNormal = new BitmapFont(Gdx.files.internal("fonts/silom-32.fnt"), false);
            defaultBig = new BitmapFont(Gdx.files.internal("fonts/silom-60.fnt"), false);
            // set fontNumberLine sizes
            defaultNumberLine.getData().setScale(1.0f);
            defaultSmall.getData().setScale(0.7f);
            defaultNormal.getData().setScale(1.0f);
            defaultBig.getData().setScale(1.0f);
            // enable linear texture filtering for smooth fonts
            defaultNumberLine.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultSmall.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultNormal.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            defaultBig.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        }
    }

    public class AssetVirtualBlocks {
        public final TextureAtlas.AtlasRegion box;
        public final TextureAtlas.AtlasRegion box2;
        public final TextureAtlas.AtlasRegion box3;
        public final TextureAtlas.AtlasRegion box4;
        public final TextureAtlas.AtlasRegion box5;

        public AssetVirtualBlocks(TextureAtlas atlas) {
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
        public final TextureAtlas.AtlasRegion tutorial1;
        public final TextureAtlas.AtlasRegion tutorial2;
        public final TextureAtlas.AtlasRegion justHand;
        public final TextureAtlas.AtlasRegion prices;

        public AssetFeedback (TextureAtlas atlas) {
            hand = atlas.findRegion("mano");
            wheel = atlas.findRegion("wheel");
            tutorial1 = atlas.findRegion("tutorial1");
            tutorial2 =  atlas.findRegion("tutorial2");
            justHand = atlas.findRegion("manoSola");
            prices= atlas.findRegion("3premios");

        }
    }

    public class AssetStaticBackground{
        public final TextureAtlas.AtlasRegion backStart,intro;
        public final TextureAtlas.AtlasRegion city1,city2,city3,city4;
        public final TextureAtlas.AtlasRegion clouds1,clouds2,clouds3,clouds4;
        public final TextureAtlas.AtlasRegion tubes1, tubes2,tubes3,tubes4,tubes5;
        public final TextureAtlas.AtlasRegion belowTheGround;
        public final TextureAtlas.AtlasRegion sea1, sea2, sea3, sea4, sea5, factory1, factory2, factory3, factory4, factory5, night1, night2, night3, battle1;

        public AssetStaticBackground (TextureAtlas atlas) {
            backStart = atlas.findRegion("inicio");
            intro = atlas.findRegion("intro");

            city1 = atlas.findRegion("ciudad01");
            city2 = atlas.findRegion("ciudad02");
            city3 = atlas.findRegion("ciudad03");
            city4 = atlas.findRegion("ciudad04");

            clouds1 = atlas.findRegion("nubes01");
            clouds2 = atlas.findRegion("nubes02");
            clouds3 = atlas.findRegion("nubes03");
            clouds4 = atlas.findRegion("nubes04");

            tubes1 = atlas.findRegion("tubo01");
            tubes2 = atlas.findRegion("tubo02");
            tubes3 = atlas.findRegion("tubo03");
            tubes4 = atlas.findRegion("tubo04");
            tubes5 = atlas.findRegion("tubo02B");

            sea1 = atlas.findRegion("mar-1");
            sea2 = atlas.findRegion("mar-2");
            sea3 = atlas.findRegion("mar-3");
            sea4 = atlas.findRegion("mar-4");
            sea5 = atlas.findRegion("mar-5");

            factory1 = atlas.findRegion("fabrica-1");
            factory2 = atlas.findRegion("fabrica-2");
            factory3 = atlas.findRegion("fabrica-3");
            factory4 = atlas.findRegion("fabrica-4");
            factory5 = atlas.findRegion("fabrica-5");

            battle1 = atlas.findRegion("batalla-1");
            night1 = atlas.findRegion("noche-4");
            night2 = atlas.findRegion("noche-2");
            night3 = atlas.findRegion("noche-3");


            belowTheGround= atlas.findRegion("abajo5");
        }
    }

    public class AssetBackground {
        public final TextureAtlas.AtlasRegion feedbackZoneTablet, feedbackZoneTabletPlus10;
        public final TextureAtlas.AtlasRegion feedbackZoneCV, feedbackZoneCVPlus10;
        public final TextureAtlas.AtlasRegion feedbackZoneV1CV;
        public final TextureAtlas.AtlasRegion feedbackZoneV1Tablet;
        public final TextureAtlas.AtlasRegion gearGray1;
        public final TextureAtlas.AtlasRegion gearGray2;
        public final TextureAtlas.AtlasRegion cloud1;
        public final TextureAtlas.AtlasRegion cloud2;
        public final TextureAtlas.AtlasRegion cloud3;
        public final TextureAtlas.AtlasRegion cloud4;
        public final TextureAtlas.AtlasRegion cloud5;

        public final TextureAtlas.AtlasRegion cityO1;
        public final TextureAtlas.AtlasRegion cityO2;

        public final TextureAtlas.AtlasRegion bigScrew,shadow;
        public final TextureAtlas.AtlasRegion guiGearL,guiGearR;
        public final TextureAtlas.AtlasRegion numberLineV,numberLineH;



        public AssetBackground (TextureAtlas atlas) {
            feedbackZoneTablet= atlas.findRegion("feedbackTablet");
            feedbackZoneTabletPlus10= atlas.findRegion("plus10tablet");
            feedbackZoneCV = atlas.findRegion("feedbackZone360x360") ;
            feedbackZoneCVPlus10 = atlas.findRegion("plus10") ;
            feedbackZoneV1CV = atlas.findRegion("workZoneV2");
            feedbackZoneV1Tablet = atlas.findRegion("workZoneV2tablet2");
            gearGray1 = atlas.findRegion("engrana01");
            gearGray2 = atlas.findRegion("engrana02");
            cloud1 = atlas.findRegion("nubeA");
            cloud2 = atlas.findRegion("nubeB");
            cloud3 = atlas.findRegion("nubeC");
            cloud4 = atlas.findRegion("nubeD");
            cloud5 = atlas.findRegion("nubeE");
            cityO1 = atlas.findRegion("cityO1");
            cityO2 = atlas.findRegion("cityO2");
            bigScrew = atlas.findRegion("tornillo-intro");
            guiGearL = atlas.findRegion("guiGearL");
            guiGearR = atlas.findRegion("guiGearR");
            numberLineV = atlas.findRegion("rectaV");
            numberLineH = atlas.findRegion("rectaH");
            shadow = atlas.findRegion("sombrabruno");
        }
    }

    public class AssetBruno {
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
        public final TextureAtlas.AtlasRegion mega;
        public final TextureAtlas.AtlasRegion mega2;
        public final TextureAtlas.AtlasRegion jetPack;
        public final TextureAtlas.AtlasRegion fire;
        public final TextureAtlas.AtlasRegion initHead;
        public final TextureAtlas.AtlasRegion initBody;
        public final TextureAtlas.AtlasRegion underWater;
        public final TextureAtlas.AtlasRegion spring;


        public AssetBruno (TextureAtlas atlas) {
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
            energy = atlas.findRegion("energia");
            walkHead = atlas.findRegion("caminaCabeza");
            mega = atlas.findRegion("mega");
            mega2 = atlas.findRegion("mega2");
            jetPack = atlas.findRegion("jetpack");
            fire = atlas.findRegion("fuegoJetpack");
            initHead  = atlas.findRegion("brunoGrande_cabeza");
            initBody  = atlas.findRegion("brunoGrande_cuerpo");
            underWater = atlas.findRegion("submarino");
            spring = atlas.findRegion("resorte");

            Array<TextureAtlas.AtlasRegion> regions = null;
            regions = atlas.findRegions("camina");
            walk = new Animation(0.1f , regions);
        }
    }


    public class AssetMenu {
        public final TextureAtlas.AtlasRegion play;
        public final TextureAtlas.AtlasRegion logoBruno;

        public AssetMenu (TextureAtlas atlas) {
            play = atlas.findRegion("jugar");
            logoBruno = atlas.findRegion("logoBruno");
        }
    }


    public class AssetObjectsToCollect {
        public final TextureAtlas.AtlasRegion price1,price2,price3,price4,price5,price6;

        public AssetObjectsToCollect (TextureAtlas atlas) {
            price1 = atlas.findRegion("premio1");
            price2 = atlas.findRegion("premio2");
            price3 = atlas.findRegion("premio3");
            price4 = atlas.findRegion("premio4");
            price5 = atlas.findRegion("premio5");
            price6 = atlas.findRegion("premio6");
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
        }
    }

    public class AssetFinishBackground{
        public final TextureAtlas.AtlasRegion excellentWork;
        public final TextureAtlas.AtlasRegion thumbUp;
        public final TextureAtlas.AtlasRegion repeat;

        public AssetFinishBackground(TextureAtlas atlas) {
            excellentWork = atlas.findRegion("excelentetrabajo");
            thumbUp = atlas.findRegion("manitoarriba");
            repeat = atlas.findRegion("deVuelta");
        }
    }


    public class AssetTree{
        public final TextureAtlas.AtlasRegion tree;
        public final TextureAtlas.AtlasRegion gear1;
        public final TextureAtlas.AtlasRegion gear2;
        public final TextureAtlas.AtlasRegion gear3;
        public final TextureAtlas.AtlasRegion gear4;
        public final TextureAtlas.AtlasRegion gear5;
        public final TextureAtlas.AtlasRegion gear6;
        public final TextureAtlas.AtlasRegion gear1inactive;
        public final TextureAtlas.AtlasRegion gear2inactive;
        public final TextureAtlas.AtlasRegion gear3inactive;
        public final TextureAtlas.AtlasRegion gear4inactive;
        public final TextureAtlas.AtlasRegion gear5inactive;
        public final TextureAtlas.AtlasRegion gear6inactive;
        public final TextureAtlas.AtlasRegion arrow,arrowWhite;
        public final TextureAtlas.AtlasRegion part1,part2,part3,part4,part5,part6;
        public final TextureAtlas.AtlasRegion magnet;
        public final TextureAtlas.AtlasRegion gear1half,gear2half,gear3half,gear4half,gear5half,gear6half;

        public  AssetTree(TextureAtlas atlas) {
            tree = atlas.findRegion("arbol-guia"); // is smaller but we will scale up
            gear1 = atlas.findRegion("engranaje");
            gear2 = atlas.findRegion("engranaje1");
            gear3 = atlas.findRegion("engranaje2");
            gear4 = atlas.findRegion("engranajes3");
            gear5 = atlas.findRegion("engranajes4");
            gear6 = atlas.findRegion("engranajes5");
            arrow = atlas.findRegion("flecha");
            arrowWhite = atlas.findRegion("flechaBlanca");
            gear1inactive = atlas.findRegion("faltante");
            gear2inactive = atlas.findRegion("faltante1");
            gear3inactive = atlas.findRegion("faltante2");
            gear4inactive = atlas.findRegion("faltante3");
            gear5inactive = atlas.findRegion("faltante4");
            gear6inactive = atlas.findRegion("faltante5");
            part1= atlas.findRegion("hojitas");
            part2= atlas.findRegion("bombillaA");
            part3= atlas.findRegion("casitarbol");
            part4= atlas.findRegion("flor");
            part5= atlas.findRegion("bombillaB");
            part6= atlas.findRegion("fruto");
            magnet= atlas.findRegion("iman");
            gear1half = atlas.findRegion("engranaje A");
            gear2half = atlas.findRegion("engranaje1 A");
            gear3half = atlas.findRegion("engranaje2 A");
            gear4half = atlas.findRegion("engranajes3 A");
            gear5half = atlas.findRegion("engranajes4 A");
            gear6half = atlas.findRegion("engranajes5 A");
        }

    }

    public class AssetButtons{
        public final ImageButton.ImageButtonStyle playButtonStyle;
        public final ImageButton.ImageButtonStyle levelsButtonStyle;
        public final ImageButton.ImageButtonStyle exitButtonStyle;
        public final ImageButton.ImageButtonStyle understoodButtonStyle;
        public final ImageButton.ImageButtonStyle resetButtonStyle, tutorialButtonStyle;


        public  AssetButtons(TextureAtlas atlas) {
            playButtonStyle = new ImageButton.ImageButtonStyle();
            playButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("jugar-1")); //Set image for not pressed button
            playButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("jugar-2"));  //Set image for pressed
            playButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("jugar-2"));

            levelsButtonStyle = new ImageButton.ImageButtonStyle();
            levelsButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("niveles-1")); //Set image for not pressed button
            levelsButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("niveles-2"));  //Set image for pressed
            levelsButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("niveles-2"));

            exitButtonStyle = new ImageButton.ImageButtonStyle();
            exitButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("salir-1")); //Set image for not pressed button
            exitButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("salir-2"));  //Set image for pressed
            exitButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("salir-2"));

            understoodButtonStyle = new ImageButton.ImageButtonStyle();
            understoodButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("entendido-1")); //Set image for not pressed button
            understoodButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("entendido-2"));  //Set image for pressed
            understoodButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("entendido-2"));

            resetButtonStyle = new ImageButton.ImageButtonStyle();
            resetButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("reset-1")); //Set image for not pressed button
            resetButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("reset-2"));  //Set image for pressed
            resetButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("reset-2"));

            tutorialButtonStyle = new ImageButton.ImageButtonStyle();
            tutorialButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("tutorial-1")); //Set image for not pressed button
            tutorialButtonStyle.down= new TextureRegionDrawable(atlas.findRegion("tutorial-2"));  //Set image for pressed
            tutorialButtonStyle.over= new TextureRegionDrawable(atlas.findRegion("tutorial-2"));

        }

    }

    public class AssetSounds {
        public final Sound a1, a2, a3, b2,b3,b4,b5,b6;
        public final Sound error;
        public final Sound buzz;
        public final Sound zero, one;
        public final Sound two;
        public final Sound three;
        public final Sound four;
        public final Sound five,six,seven,eight,nine,ten,eleven,twelve,thirteen,fourteen,fifteen, s16, s17, s18, s19, s20, s21, s22, s23, positive;
        public final Sound tooMuch;
        public final Sound tooFew;
        public final Sound cleanTable;
        public final Sound levelPassed,levelPassed2, levelPassed3, levelPassed4, repeatLevel;

        public final Sound welcome;
        public final Sound intro2, intro3, intro5, intro6, finalBattleIntro, plus10;

        public final Music erase, malvado,estirarse,agarrar,inTheZone,fichas,poniendo,creepy,gracias,intro4;


        public AssetSounds (AssetManager am) {

            a1 = am.get("positive/a1-iupi.mp3", Sound.class);
            a2 = am.get("positive/a2-MuyBien.mp3", Sound.class);
            a3 = am.get("positive/a3-Buenisimo.mp3", Sound.class);
            b2 = am.get("positive/b2-AndasVolando.mp3", Sound.class);
            b3 = am.get("positive/b3-QueBuenosAmigos.mp3", Sound.class);
            b4 = am.get("positive/b4-BienPescado.mp3", Sound.class);
            b5 = am.get("positive/b5-QueBuenSalto.mp3", Sound.class);
            b6 = am.get("positive/b6-YaEstamosMasCerca.mp3", Sound.class);
            positive = am.get("positive/BrUNO+.mp3", Sound.class);

            error = am.get("sounds/BrUNO-.mp3", Sound.class);
            buzz = am.get("sounds/BrUNOneutro.mp3", Sound.class);
            zero = am.get("sounds/0.mp3", Sound.class);
            one = am.get("sounds/1.mp3", Sound.class);
            two = am.get("sounds/2.mp3", Sound.class);
            three = am.get("sounds/3.mp3", Sound.class);
            four = am.get("sounds/4.mp3", Sound.class);
            five = am.get("sounds/5.mp3", Sound.class);
            six=am.get("sounds/6.mp3", Sound.class);
            seven=am.get("sounds/7.mp3", Sound.class);
            eight=am.get("sounds/8.mp3", Sound.class);
            nine =am.get("sounds/9.mp3", Sound.class);
            ten = am.get("sounds/10.mp3", Sound.class);
            eleven= am.get("sounds/11.mp3", Sound.class);
            twelve= am.get("sounds/12.mp3", Sound.class);
            thirteen= am.get("sounds/13.mp3", Sound.class);
            fourteen= am.get("sounds/14.mp3", Sound.class);
            fifteen= am.get("sounds/15.mp3", Sound.class);
            s16 = am.get("sounds/16.mp3", Sound.class);
            s17 = am.get("sounds/17.mp3", Sound.class);
            s18 = am.get("sounds/18.mp3", Sound.class);
            s19 = am.get("sounds/19.mp3", Sound.class);
            s20 = am.get("sounds/20.mp3", Sound.class);
            s21 = am.get("sounds/21.mp3", Sound.class);
            s22 = am.get("sounds/22.mp3", Sound.class);
            s23 = am.get("sounds/23.mp3", Sound.class);

            levelPassed = am.get("sounds/PasasteDeNIvel.mp3", Sound.class);
            levelPassed2 = am.get("sounds/BuenTrabajo.mp3", Sound.class);
            levelPassed3 = am.get("sounds/ExcelenteSeguimosAvanzando.mp3", Sound.class);
            levelPassed4 = am.get("sounds/MuyBienProximoNivel.mp3", Sound.class);

            repeatLevel = am.get("sounds/VamosAHacerEsteNivelUNaVezMas.mp3", Sound.class);
            inTheZone= am.get("sounds/HayQuePonerLasFichas.mp3", Music.class);
            tooMuch = am.get("sounds/ProbaConMenosFichas.mp3", Sound.class);
            tooFew = am.get("sounds/ProbaConMasFichas.mp3", Sound.class);
            cleanTable = am.get("sounds/TenesQueSacarLasFichas.mp3", Sound.class);
            welcome = am.get("sounds/PreIntro.mp3", Sound.class);
            erase = am.get("sounds/erase.mp3", Music.class);
            malvado = am.get("sounds/malvado.mp3", Music.class);
            estirarse = am.get("sounds/estirarse.mp3", Music.class);
            agarrar = am.get("sounds/agarrar.mp3", Music.class);
            fichas = am.get("sounds/fichasRobo.mp3", Music.class);
            gracias = am.get("sounds/GraciasFin.mp3", Music.class);
            poniendo = am.get("sounds/poniendoFichas.mp3", Music.class);
            creepy = am.get("sounds/creepy-min.mp3", Music.class);

            intro2 = am.get("island-intro/de1a2.mp3", Sound.class);
            intro3 = am.get("island-intro/de2a3.mp3", Sound.class);
            intro4 = am.get("island-intro/de3a4.mp3", Music.class);
            intro5 = am.get("island-intro/de4a5.mp3", Sound.class);
            intro6 = am.get("island-intro/de5a6.mp3", Sound.class);
            finalBattleIntro = am.get("island-intro/de6a7.mp3", Sound.class);
            plus10 = am.get("sounds/plus10.mp3", Sound.class);

        }
    }
    public class AssetMusic {
        public final Music song01, musicCity, musicClouds, musicTubes, musicUnderwater, musicFactory, musicNight, musicBattle;

        public AssetMusic (AssetManager am) {

            musicCity = am.get("music/1_CIUDAD.mp3", Music.class);
            song01 = musicCity;
            musicClouds = am.get("music/2_NUBES.mp3", Music.class);
            musicTubes = am.get("music/3_TUBERIAS.mp3", Music.class);
            musicUnderwater = am.get("music/4_SUBMARINO.mp3", Music.class);
            musicFactory = am.get("music/5_RESORTES.mp3", Music.class);
            musicNight = musicCity;
            musicBattle = am.get("music/6_BATALLAFINAL.mp3", Music.class);

        }
    }
}
