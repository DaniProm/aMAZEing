package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class HoleActor extends GameActor {

	private boolean hasSwallowedBall = false;

	public void swallowBall(BallActor ball) {
		if(hasSwallowedBall) return;
		ballToSwallow = ball;
	}

	private BallActor ballToSwallow;

	public boolean hasSwallowedBall() {
		return hasSwallowedBall;
	}

	public HoleActor() {
		sprite = new Sprite(Assets.manager.get(Assets.HOLE));
		setSize(1, 1);
	}

	@Override
	protected Shape getShape() {
		return getCircleShape(.8f);
	}

	@Override
	public void applyWorld(World world, BodyDef.BodyType bodyType) {
		super.applyWorld(world, bodyType);

		setSensor(true);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if(ballToSwallow != null) {
			hasSwallowedBall = true;
			ballToSwallow.delete();
			ballToSwallow.Swallow(this);
			ballToSwallow = null;

			// itt lehetne megváltoztatni a sprite-ot is...

		}

	}

}
