package com.csanydroid.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class BallActor extends GameActor {

	// http://stackoverflow.com/questions/30812250/how-can-i-use-the-accelerometer-for-detecting-jump-in-libgdx
	protected static Texture texture = new Texture("ball.png");

	public BallActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
	}

	@Override
    protected Shape getShape() {
	    CircleShape shape = new CircleShape();
	    shape.setRadius(GameScreen.BASE_SIZE * .4f / PIX2M);
		shape.setPosition(new Vector2(GameScreen.BASE_SIZE / 2f / PIX2M, GameScreen.BASE_SIZE / 2f / PIX2M));
	    return shape;
    }

    @Override
    public void dispose() {

    }

}
