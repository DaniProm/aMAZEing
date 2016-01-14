package com.csanydroid.game;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Assets {
	// https://github.com/libgdx/libgdx/wiki/Managing-your-assets
	// http://www.jacobplaster.net/using-libgdx-asset-manager
	// https://www.youtube.com/watch?v=JXThbQir2gU
	// https://github.com/Matsemann/libgdx-loading-screen/blob/master/Main/src/com/matsemann/libgdxloadingscreen/screen/LoadingScreen.java

	public static AssetManager manager;


	public static final AssetDescriptor<Texture> EXPLOSIVE_WALL
			= new AssetDescriptor<Texture>("expwall1.png", Texture.class);

	public static final AssetDescriptor<Texture> WALL
			= new AssetDescriptor<Texture>("wall1.png", Texture.class);

	public static final AssetDescriptor<Texture> HOLE
			= new AssetDescriptor<Texture>("hole.png", Texture.class);

	public static final AssetDescriptor<Texture> BACKGROUND
			= new AssetDescriptor<Texture>("background.png", Texture.class);

	public static final AssetDescriptor<Texture> BLACK_HOLE
			= new AssetDescriptor<Texture>("black_hole.png", Texture.class);

	public static final AssetDescriptor<TextureAtlas> WORMHOLE_ATLAS
			= new AssetDescriptor<TextureAtlas>("teleport.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> STAR_ATLAS
			= new AssetDescriptor<TextureAtlas>("star.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> DOOR_ATLAS
			= new AssetDescriptor<TextureAtlas>("door.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> EXPLOSION_ATLAS
			= new AssetDescriptor<TextureAtlas>("boom1.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_GREEN_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballgreen.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_RED_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballred.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_ORANGE_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballorange.atlas", TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> BALL_BLUE_ATLAS
			= new AssetDescriptor<TextureAtlas>("ballblue.atlas", TextureAtlas.class);

	public static final AssetDescriptor<Texture> BALL_SHADOW
			= new AssetDescriptor<Texture>("ballshadow.png", Texture.class);

	public static final AssetDescriptor<Texture> BALL_LIGHT
			= new AssetDescriptor<Texture>("balllight.png", Texture.class);

	public static final AssetDescriptor<Music> STAR_MUSIC
			= new AssetDescriptor<Music>("teleport.mp3", Music.class);

	public static void load() {
		manager = new AssetManager();
		Texture.setAssetManager(manager);

		manager.load(WALL);
		manager.load(HOLE);
		manager.load(BLACK_HOLE);
		manager.load(WORMHOLE_ATLAS);
		manager.load(STAR_ATLAS);
		manager.load(STAR_MUSIC);
		manager.load(DOOR_ATLAS);
		manager.load(BALL_GREEN_ATLAS);
		manager.load(BALL_RED_ATLAS);
		manager.load(BALL_ORANGE_ATLAS);
		manager.load(BALL_BLUE_ATLAS);
		manager.load(BALL_SHADOW);
		manager.load(BALL_LIGHT);
		manager.load(BACKGROUND);
	}

	public static void unload() {
		manager.dispose();
	}

}
