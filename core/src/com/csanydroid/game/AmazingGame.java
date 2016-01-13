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

	public static AmazingGame amazingGame = new AmazingGame();


	@Override
	public void create() {
		/*try {
			mScreens = new Screen[]{
					new GameMenu(),
					new GameScreen("1"),
					new GameScreen("2"),
					new GameScreen("3"),
					new GameScreen("4"),
					new GameScreen("5"),
					new GameScreen("6"),
					new GameScreen("7"),
			};
		}
		catch (Exception e){
			System.out.println(e.getMessage()+" Dani buta volt... MEGINT!! >.<''");
		}*/


	//	Maze.findMaze("1").beginPlay();
		Maze.findMaze("star").beginPlay();
		//showScreen(Screens.MENU);
	}




		/*
		public static SpaceGame sGame = new SpaceGame();

	private static Screen[] mScreens;

	public enum Screens {
		MENU(0), HELP(1), GAME(2), STAT(3), EGGS(4);

		private int value;

		Screens(int value) {
			this.value = value;
		}
	}

	public void showScreen(Screens screen) {
		setScreen(mScreens[screen.value]);
	}

	@Override
	public void create() {
		mScreens = new Screen[]{
           		new ScreenMenu(),
           		new ScreenHelp(),
           		new ScreenGame(),
				new ScreenStatictics(),
				new ScreenEasterEggs()
		};

		showScreen(Screens.MENU);
	}
		 */



	}


