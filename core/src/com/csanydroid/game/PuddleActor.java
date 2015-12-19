package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class PuddleActor extends GameActor {

	protected static Texture texture = new Texture("mud_puddle.png");

	public PuddleActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
	}

    @Override
    public void dispose() {

    }

}
