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
	//protected static Texture texture = new Texture("ball8.png");

	protected static Animation animationStar;
	private float stFrame = 0;
	//protected static Array<TextureAtlas.AtlasRegion> textureAtlas = new TextureAtlas("ballcsd.atlas").getRegions();
	protected static TextureAtlas textureAtlas = new TextureAtlas("ballcsd.atlas");


	public BallActor() {
		sprite = new Sprite(textureAtlas.getRegions().get(0));
		sprite.setRegion(textureAtlas.getRegions().get(0));
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
	private float ballRotateX=0;
	private float ballRotateY=0;

	@Override
	public void act(float delta) {
		super.act(delta);
		float BallPositionX = body.getPosition().x;
		float BallPositionY = body.getPosition().y;
		//Gdx.app.log("asd", String.valueOf(prevBallPositionX - BallPositionX));
		//sprite.rotate((prevBallPositionX - BallPositionX)*100.0f);
		ballRotateX += (prevBallPositionX - BallPositionX)*5f;
		ballRotateY -= (prevBallPositionY - BallPositionY)*5f;
		if (ballRotateX>=9)
		{
			ballRotateX = 0;
		}
		if (ballRotateY>=9)
		{
			ballRotateY = 0;
		}
		if (ballRotateX<0)
		{
			ballRotateX=8.99999f;
		}
		if (ballRotateY<0)
		{
			ballRotateY=8.99999f;
		}
		sprite.setRegion(textureAtlas.getRegions().get(((int)ballRotateY) * 9 + (int)ballRotateX));
		prevBallPositionX = BallPositionX;
		prevBallPositionY = BallPositionY;

	}
}

