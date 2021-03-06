package com.csanydroid.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	// https://github.com/libgdx/libgdx/wiki/Managing-your-assets
	// http://www.jacobplaster.net/using-libgdx-asset-manager
	// https://www.youtube.com/watch?v=JXThbQir2gU
	// https://github.com/Matsemann/libgdx-loading-screen/blob/master/Main/src/com/matsemann/libgdxloadingscreen/screen/LoadingScreen.java

	public static AssetManager manager;

	public static final AssetDescriptor<TextureAtlas> LOADING_ATLAS
			= new AssetDescriptor<TextureAtlas>("loading.atlas", TextureAtlas.class);

	//public static final AssetDescriptor<Texture> PLAY
	//		= new AssetDescriptor<Texture>("play.png", Texture.class);

	public static final AssetDescriptor<Texture> MENU_MOLE
			= new AssetDescriptor<Texture>("menumole.png", Texture.class);

	public static final AssetDescriptor<Texture> CSANY_LOGO
			= new AssetDescriptor<Texture>("csany.png", Texture.class);

	public static final AssetDescriptor<Texture> PenDroid_LOGO
			= new AssetDescriptor<Texture>("pd.png", Texture.class);

    public static final AssetDescriptor<Texture> LOCK
            = new AssetDescriptor<Texture>("lock.png", Texture.class);

	public static final AssetDescriptor<Texture> MENU_LOGO
			= new AssetDescriptor<Texture>("menulogo.png", Texture.class);

	public static final AssetDescriptor<Texture> MENU_BACKGROUND
			= new AssetDescriptor<Texture>("menubackground.png", Texture.class);


	public static final AssetDescriptor<Texture> EXPLOSIVE_WALL
			= new AssetDescriptor<Texture>("expwall.png", Texture.class);

	public static final AssetDescriptor<Texture> WALL
			= new AssetDescriptor<Texture>("wall.png", Texture.class);

	public static final AssetDescriptor<Texture> HOLE
			= new AssetDescriptor<Texture>("hole.png", Texture.class);

	public static final AssetDescriptor<Texture> BACKGROUND
			= new AssetDescriptor<Texture>("background.png", Texture.class);

	public static final AssetDescriptor<Texture> PUDDLE
			= new AssetDescriptor<Texture>("mud_puddle.png", Texture.class);

	public static final AssetDescriptor<Texture> WINDOW
			= new AssetDescriptor<Texture>("window.png", Texture.class);

	public static final AssetDescriptor<TextureAtlas> BLACK_HOLE
			= new AssetDescriptor<TextureAtlas>("black_hole.atlas", TextureAtlas.class);

	public static final AssetDescriptor<Texture> WORMHOLE
			= new AssetDescriptor<Texture>("wormhole.png", Texture.class);

	public static final AssetDescriptor<TextureAtlas> STAR_ATLAS
			= new AssetDescriptor<TextureAtlas>("star.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> DOOR_ATLAS
			= new AssetDescriptor<TextureAtlas>("door.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> EXPLOSION_ATLAS
			= new AssetDescriptor<TextureAtlas>("explosion.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_GREEN_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballgreen.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_RED_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballred.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_ORANGE_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballorange.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_BLUE_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballblue.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> SWITCH_ATLAS
			= new AssetDescriptor<TextureAtlas>("switch.atlas", TextureAtlas.class);

    public static final AssetDescriptor<TextureAtlas> BUTTON_ATLAS
            = new AssetDescriptor<TextureAtlas>("button.atlas", TextureAtlas.class);

	public static final AssetDescriptor<Texture> BALL_SHADOW
			= new AssetDescriptor<Texture>("ballshadow.png", Texture.class);


	public static final AssetDescriptor<Texture> PUSH_BUTTON
			= new AssetDescriptor<Texture>("push_button.png", Texture.class);
	public static final AssetDescriptor<Texture> PUSH_BUTTON2
			= new AssetDescriptor<Texture>("push_button2.png", Texture.class);

	public static final AssetDescriptor<Texture> BALL_LIGHT
			= new AssetDescriptor<Texture>("balllight.png", Texture.class);


    public static final AssetDescriptor<Music> MAZESELECTING_MUSIC
            = new AssetDescriptor<Music>("sounds/Loungekore.mp3", Music.class);

    public static final AssetDescriptor<Music> NEXTLEVEL_MUSIC
            = new AssetDescriptor<Music>("sounds/RocknRoll.mp3", Music.class);

    public static final AssetDescriptor<Music> MENU_MUSIC
            = new AssetDescriptor<Music>("sounds/Ska1.mp3", Music.class);

//

    public static final AssetDescriptor<Sound> BLACKHOLE_SOUND
            = new AssetDescriptor<Sound>("sounds/black_hole.wav", Sound.class);

	public static final AssetDescriptor<Sound> DOOR_OPEN_SOUND
			= new AssetDescriptor<Sound>("sounds/door.wav", Sound.class);

	public static final AssetDescriptor<Sound> DOOR_CLOSE_SOUND
			= new AssetDescriptor<Sound>("sounds/door_back.wav", Sound.class);

    public static final AssetDescriptor<Sound> BALLCWBALL_SOUND
            = new AssetDescriptor<Sound>("sounds/ball_collision_with_ball.wav", Sound.class);

    public static final AssetDescriptor<Sound> BALLCWWALL_SOUND
            = new AssetDescriptor<Sound>("sounds/ball_collision_with_wall.wav", Sound.class);

    public static final AssetDescriptor<Sound> CLICK_SOUND
            = new AssetDescriptor<Sound>("sounds/click.wav", Sound.class);

    public static final AssetDescriptor<Music> PUDDE_MUSIC
            = new AssetDescriptor<Music>("sounds/puddle.wav", Music.class);

    public static final AssetDescriptor<Sound> STARCOLLECTION_SOUND
            = new AssetDescriptor<Sound>("sounds/star_collection.wav", Sound.class);

    public static final AssetDescriptor<Sound> SWITCHING_SOUND
            = new AssetDescriptor<Sound>("sounds/switching.wav", Sound.class);

    public static final AssetDescriptor<Sound> TELEPORTATION_SOUND
            = new AssetDescriptor<Sound>("sounds/teleportation.wav", Sound.class);

    public static final AssetDescriptor<Sound> WALLEXPLOSION_SOUND
            = new AssetDescriptor<Sound>("sounds/wall_explosion.wav", Sound.class);


    public static void prepare() {

		manager = new AssetManager();
		Texture.setAssetManager(manager);

	}

	public static void load() {
		//TEXTÚRÁK//
        manager.load(LOCK);
		manager.load(SWITCH_ATLAS);
		manager.load(PUSH_BUTTON);
		manager.load(PUSH_BUTTON2);
		manager.load(WALL);
		manager.load(PUDDLE);
		manager.load(EXPLOSIVE_WALL);
		manager.load(EXPLOSION_ATLAS);
		manager.load(HOLE);
		manager.load(BLACK_HOLE);
		manager.load(WORMHOLE);
		manager.load(STAR_ATLAS);
		manager.load(DOOR_ATLAS);
        manager.load(BUTTON_ATLAS);
		manager.load(BALL_GREEN_ATLAS);
		manager.load(BALL_RED_ATLAS);
		manager.load(BALL_ORANGE_ATLAS);
		manager.load(BALL_BLUE_ATLAS);
		manager.load(BALL_SHADOW);
		manager.load(BALL_LIGHT);
		manager.load(BACKGROUND);
		manager.load(WINDOW);
		manager.load(MENU_BACKGROUND);
		manager.load(MENU_LOGO);
		manager.load(MENU_MOLE);
		manager.load(CSANY_LOGO);
		manager.load(PenDroid_LOGO);
		//manager.load(PLAY);
		//TEXTÚRÁK//


		//ZENE//
        manager.load(MAZESELECTING_MUSIC);
        manager.load(MENU_MUSIC);
        manager.load(NEXTLEVEL_MUSIC);
		//ZENE//

		//HANGOK//
        manager.load(BLACKHOLE_SOUND);
        manager.load(BALLCWBALL_SOUND);
        manager.load(BALLCWWALL_SOUND);
        manager.load(CLICK_SOUND);
        manager.load(PUDDE_MUSIC);
        manager.load(STARCOLLECTION_SOUND);
        manager.load(SWITCHING_SOUND);
        manager.load(TELEPORTATION_SOUND);
        manager.load(WALLEXPLOSION_SOUND);
		manager.load(DOOR_OPEN_SOUND);
		manager.load(DOOR_CLOSE_SOUND);
		//HANGOK//

	}

    public static void afterLoaded() {
        manager.get(PUDDE_MUSIC).setLooping(true);
        manager.get(MAZESELECTING_MUSIC).setLooping(true);
        manager.get(MENU_MUSIC).setLooping(true);
        manager.get(NEXTLEVEL_MUSIC).setLooping(true);
    }

	public static void unload() {
		manager.dispose();
	}

}
