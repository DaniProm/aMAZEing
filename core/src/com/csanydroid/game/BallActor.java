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

import java.util.ArrayList;

public class BallActor extends GameActor {

	protected static Array<TextureAtlas.AtlasRegion> textureAtlasRegions = new TextureAtlas("ballcsd.atlas").getRegions();

	public BallActor() {
		sprite = new Sprite(textureAtlasRegions.first());
		sprite.setRegion(textureAtlasRegions.first());
		setSize(1, 1);
	}

	private static final float DIAMETER = .9f, CIRCUMFERENCE = DIAMETER * (float)Math.PI;

	@Override
	protected Shape getShape() {
		return getCircleShape(DIAMETER);
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
		sprite.setRegion(textureAtlasRegions.get(((int) ballRotateY) * 9 + (int) ballRotateX));

		// szerintem hibásak a forgási irányok a képben (pl. a 6. sor nekem úgy tűnik, hogy a saját tengelye körül is forog)
		// sprite.setRegion(textureAtlasRegions.get((/*8 - */(int)(((-y / CIRCUMFERENCE) * 9 % 9))) * 9 + (/*8 - */(int)((x / CIRCUMFERENCE) * 9 % 9))));

		prevBallPositionX = BallPositionX;
		prevBallPositionY = BallPositionY;

	}
}

