package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class PuddleActor extends GameActor {

	public PuddleActor() {
		sprite = new Sprite(Assets.manager.get(Assets.PUDDLE));
		setSize(1, 1);
	}

	@Override
	public void applyWorld(World world, BodyDef.BodyType bodyType) {
		super.applyWorld(world, bodyType);

		setSensor(true);
	}
}
