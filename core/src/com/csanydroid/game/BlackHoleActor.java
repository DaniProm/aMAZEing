package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

import java.util.ArrayDeque;

/* Ez fog elnyelni*/
public class BlackHoleActor extends GameActor {

	protected static Texture texture = new Texture("black_hole.png");
	private ArrayDeque<BallActor> balls = new ArrayDeque<BallActor>();

	public BlackHoleActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
	}

	private final static float SIZE_SCALE = .2f;
	@Override
	protected Shape getShape() {
		final CircleShape shape = new CircleShape();
		shape.setRadius(GameScreen.TILE_SIZE * SIZE_SCALE / PIX2M);
		shape.setPosition(new Vector2(GameScreen.TILE_SIZE * SIZE_SCALE / PIX2M, GameScreen.TILE_SIZE * SIZE_SCALE / PIX2M));
		return shape;
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

	@Override
	public void dispose() { }

}
