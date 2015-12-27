package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class BallActor extends GameActor {

	// http://stackoverflow.com/questions/30812250/how-can-i-use-the-accelerometer-for-detecting-jump-in-libgdx
	protected static Texture texture = new Texture("ball.png");

	public BallActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
	}

	private final static float BALL_SIZE_SCALE = (.8f) / 2;

	@Override
    protected Shape getShape() {
	    final CircleShape shape = new CircleShape();
	    shape.setRadius(GameScreen.TILE_SIZE * BALL_SIZE_SCALE / PIX2M);
		shape.setPosition(new Vector2(GameScreen.TILE_SIZE * BALL_SIZE_SCALE / PIX2M, GameScreen.TILE_SIZE * BALL_SIZE_SCALE / PIX2M));
	    return shape;
    }

	@Override
	public void delete() {
		Gdx.input.vibrate(300);
// TODO dfasdfaeskjdfasdfas
		//((GameStage)getParent().getStage()).balls.remove(this);
		super.delete();

	}

	@Override public void dispose() {}

}
