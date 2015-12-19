package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class WormholeActor extends GameActor {

	private WormholeActor endpoint = null;
	public void setEndpoint(WormholeActor endpoint) {
		this.endpoint = endpoint;
	}

	public float active = 0;

	protected static Texture texture = new Texture("wormhole.png");

	public WormholeActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
	}

	public WormholeActor getEndpoint() {
		return endpoint;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		active -= delta;
	}

	@Override
	public void dispose() {

	}
}
