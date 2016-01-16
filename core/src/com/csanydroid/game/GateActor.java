package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class GateActor extends GameActor {

	protected final Array<TextureAtlas.AtlasRegion> textureAtlasRegions = Assets.manager.get(Assets.DOOR_ATLAS).getRegions();

	protected enum State {OPENING, OPENED, CLOSING, CLOSED, TRY_CLOSE;};

	State state;

	private int animationFrame = 0;

	public GateActor() {
		sprite = new Sprite(textureAtlasRegions.first());
		state = State.CLOSED;
		setSize(1, 1);
	}

	public void setOrientation(boolean horizontal) {
		sprite.setRotation(horizontal ? 0 : 90);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		switch (state) {
			case TRY_CLOSE:
				if(!isTouchBall()) close();
				else Gdx.app.log("gatte", "balll");
				break;
			case CLOSING:
				if (animationFrame > 0) {
					animationFrame--;
					sprite.setRegion(textureAtlasRegions.get(animationFrame));
				} else {
					state = State.CLOSED;
				}
				break;
			case OPENING:
				if (animationFrame < textureAtlasRegions.size - 1) {
					animationFrame++;
					sprite.setRegion(textureAtlasRegions.get(animationFrame));
				} else {
					state = State.OPENED;
				}
				break;
		}

	}

	@Override
	public void applyWorld(World world, BodyDef.BodyType bodyType) {
		super.applyWorld(world, bodyType);

		switch (state) {
			case OPENING:
				open();
				break;
			case CLOSING:
				close();
				break;
		}
	}

	public void open() {
		if(state == State.OPENED || state == State.OPENING) return;
		state = State.OPENING;
		if(body != null) setSensor(true);
	}

	public void tryClose() {
		if(state == State.CLOSED || state == State.CLOSING) return;
		state = State.TRY_CLOSE;
	}

	private void close() {
		//if(state == State.CLOSED) return;
		state = State.CLOSING;
		if(body != null) setSensor(false);
	}

}
