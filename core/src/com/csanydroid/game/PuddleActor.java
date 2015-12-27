package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PuddleActor extends GameActor {

	protected static Texture texture = new Texture("mud_puddle.png");

	public PuddleActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
	}

}
