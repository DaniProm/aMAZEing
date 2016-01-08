package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class BallActor extends GameActor {

	// http://stackoverflow.com/questions/30812250/how-can-i-use-the-accelerometer-for-detecting-jump-in-libgdx
	protected static Texture texture = new Texture("ball8.png");

	protected static Animation animationStar;
	private float stFrame = 0;
	protected static Array<TextureAtlas.AtlasRegion> textureAtlas = new TextureAtlas("ballx.atlas").getRegions();


	public BallActor() {
		sprite = new Sprite(textureAtlas.get(0));
		sprite.setRegion(textureAtlas.get(0));
		//animationStar = new Animation(1 / 30f, textureAtlas, Animation.PlayMode.LOOP);
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

	private float prevBallPositionX=0f;
	private float prevBallPositionY=0f;

	@Override
	public void act(float delta) {
		super.act(delta);
		float BallPositionX = body.getPosition().x;
		float BallPositionY = body.getPosition().y;
		Gdx.app.log("asd", String.valueOf(prevBallPositionX - BallPositionX));
		sprite.rotate((prevBallPositionX - BallPositionX)*100.0f);
		prevBallPositionX = BallPositionX;
		prevBallPositionY = BallPositionY;

	}
}
