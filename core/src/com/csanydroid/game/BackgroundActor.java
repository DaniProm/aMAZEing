package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class BackgroundActor extends GameActor {

    public BackgroundActor(Maze maze) {
        sprite = new Sprite(Assets.manager.get(Assets.BACKGROUND));

	    final int bgsize = Math.max(maze.getWidth(), maze.getHeight());
	    setSize(bgsize + 4, bgsize + 4);
	    setPosition(-2f + (bgsize - maze.getWidth()) / 2, -bgsize - 2f + (bgsize - maze.getHeight()) / 2);

	    //setZIndex(0);
    }

	@Override
	public void act(float delta) {
		// empty
	}
}
