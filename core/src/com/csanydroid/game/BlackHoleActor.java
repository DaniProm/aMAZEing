package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

import java.util.ArrayDeque;

/* Ez fog elnyelni*/
public class BlackHoleActor extends GameActor {

	private ArrayDeque<BallActor> balls = new ArrayDeque<BallActor>();

	public BlackHoleActor() {
		sprite = new Sprite(Assets.manager.get(Assets.BLACK_HOLE));
		setSize(1, 1);
	}

	@Override
	protected Shape getShape() {
		return getCircleShape(.55f);
	}

	public void swallowBall(BallActor ball) {
		balls.add(ball);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		for (BallActor ball; (ball = balls.poll()) != null; ) {
			ball.delete();
		}

	}

}
