package com.csanydroid.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class AmazingGame extends Game implements ApplicationListener {

    public Preferences prefs;

    @Override
	public void create() {
	    prefs = Gdx.app.getPreferences("AmazingGame");

	   setScreen(new LoadingScreen());
	}

	@Override
	public void resume() {
		super.resume();
		Assets.manager.update();
	}

	@Override
	public void dispose() {
		super.dispose();
		Assets.unload();
	}
}