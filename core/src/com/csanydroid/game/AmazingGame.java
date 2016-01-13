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

	@Override
	public void create() {
		try {
			setScreen(new GameMenu());
		}
		catch (Exception e) {e.printStackTrace();}
	}

	@Override
	public void render() {
		super.render();
		try {
			/*if (screenValue == 1) setScreen(new GameScreen(Maze.findMaze("1")));
			if (screenValue == 2) setScreen(new GameScreen(Maze.findMaze("2")));
			if (screenValue == 3) setScreen(new GameScreen(Maze.findMaze("3")));
			if (screenValue == 4) setScreen(new GameScreen(Maze.findMaze("4")));
			if (screenValue == 5) setScreen(new GameScreen(Maze.findMaze("5")));
			if (screenValue == 6) setScreen(new GameScreen(Maze.findMaze("6")));*/
		} catch (Exception e) {}
	}
}