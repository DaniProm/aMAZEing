package com.csanydroid.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AmazingGame extends Game implements ApplicationListener {

    public Preferences prefs;

    @Override
	public void create() {
        prefs = Gdx.app.getPreferences("AmazingGame");
        Gdx.app.log("dsda", prefs + "");
		try {
			setScreen(new MenuScreen());
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