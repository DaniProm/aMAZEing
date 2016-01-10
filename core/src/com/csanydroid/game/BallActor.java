package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class BallActor extends GameActor {

	protected static Array<TextureAtlas.AtlasRegion> textureAtlasRegions = new TextureAtlas("ballcsd4.atlas").getRegions();

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
	private float ballPictureRotation=0f;
	private float prevBallAngle = 0f;
	private float ballAngle = 0f;
	private boolean ballInverzeRotation = false;
	private float targetRotation = 0f;

	@Override
	public void act(float delta) {
		super.act(delta);
		float BallPositionX = body.getPosition().x;
		float BallPositionY = body.getPosition().y;
		float distanceX = prevBallPositionX - BallPositionX;
		float distanceY = prevBallPositionY - BallPositionY;

		float distance = (float) Math.sqrt((double) (distanceX * distanceX + distanceY * distanceY));
		if (distance!=0.0) {
			ballAngle = (float) Math.acos(distanceX / distance);
			if (Math.abs(ballAngle - prevBallAngle)>MathUtils.PI/2)
			{
				ballInverzeRotation = !ballInverzeRotation;
			}
			if (ballInverzeRotation) {
				targetRotation = 180 - MathUtils.radiansToDegrees * ballAngle;

				ballPictureRotation -= distance / (MathUtils.PI / textureAtlasRegions.size);
			}
			else
			{
				targetRotation = MathUtils.radiansToDegrees * ballAngle;
				ballPictureRotation += distance / (MathUtils.PI / textureAtlasRegions.size);
			}
			float actualRotation = sprite.getRotation();
			float rotation = 0;
			if (Math.abs(actualRotation-targetRotation)>10)
			{
				if (actualRotation<targetRotation) rotation = 10; else rotation= -10;
			}
			else
			{
				if (actualRotation!=targetRotation)
				{
					if (actualRotation<targetRotation)	{
						rotation = Math.abs(actualRotation - targetRotation) /2;
					} else	{
						rotation = -Math.abs(actualRotation - targetRotation) /2;
					}
				}
			}
			//sprite.setRotation(targetRotation);
			sprite.rotate(rotation);
			if (ballPictureRotation < 0) ballPictureRotation= (float)textureAtlasRegions.size-0.00001f;
			if (ballPictureRotation >= textureAtlasRegions.size) ballPictureRotation = 0f;
			sprite.setRegion(textureAtlasRegions.get(((int)(ballPictureRotation))));
		}

//				Gdx.app.log("asd", String.valueOf(sprite.getRotation()));

		prevBallPositionX = BallPositionX;
		prevBallPositionY = BallPositionY;
		prevBallAngle = ballAngle;

	}
}

