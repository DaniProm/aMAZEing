package com.csanydroid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;

public class AmazingGame extends Game implements ApplicationListener {

	@Override
	public void create () {

		try {
			setScreen(new GameScreen("test"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}