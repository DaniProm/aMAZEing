package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class BallActor extends GameActor {

	// http://stackoverflow.com/questions/30812250/how-can-i-use-the-accelerometer-for-detecting-jump-in-libgdx
	protected static Texture texture = new Texture("ball8.png");

	public BallActor() {
		sprite = new Sprite(texture);
		setSize(1, 1);
	}

	@Override
    protected Shape getShape() {
		return getCircleShape(.9f);
    }

	@Override
	public void delete() {
		((GameStage)getStage()).removeBall(this);
		super.delete();

	}

}
