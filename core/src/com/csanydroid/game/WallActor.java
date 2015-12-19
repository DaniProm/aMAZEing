package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class WallActor extends GameActor {

    private int strength = Integer.MAX_VALUE; // a fal erőssége

	protected static Texture texture = new Texture("wall0.png");

	public WallActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
	}

    @Override
    public void dispose() {

    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

}
