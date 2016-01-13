package com.csanydroid.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import java.io.IOException;

public class AmazingGame extends Game implements ApplicationListener {

	public static int screenValue = 0;
	public static AmazingGame amazingGame = new AmazingGame();
	Screen LVL1, LVL2, LVL3, LVL4, LVL5, LVL6, LVL7, LVLTest;
	@Override
	public void create() {
		try {
			setScreen(new GameMenu());
			/*LVL1 = new GameScreen("0");
			LVL2 = new GameScreen("1");
			LVL3 = new GameScreen("2");
			LVL4 = new GameScreen("3");
			LVL5 = new GameScreen("4");
			LVL6 = new GameScreen("5");
			LVL7 = new GameScreen("6");
			LVLTest = new GameScreen("test");*/

		}
		catch (Exception e) {e.printStackTrace();}
	}

	public void showScreen(Screen screen) {}

	@Override
	public void render() {
		super.render();
		try {
			if (screenValue == 1) this.setScreen(new GameScreen("1"));
			if (screenValue == 2) this.setScreen(new GameScreen("2"));
			if (screenValue == 3) this.setScreen(new GameScreen("3"));
			if (screenValue == 4) this.setScreen(new GameScreen("4"));
			if (screenValue == 5) this.setScreen(new GameScreen("5"));
		} catch (Exception e) {}
	}
}