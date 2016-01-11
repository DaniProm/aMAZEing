package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
	protected static Texture textureLight = new Texture("balllight.png");
	protected static Texture textureShadow = new Texture("ballshadow.png");
	protected Sprite spriteLight;
	protected Sprite spriteShadow;

	private float prevBallPositionX=0f;
	private float prevBallPositionY=0f;
	private float ballPictureRotation=0.8f;
	private float prevBallAngle = 0f;
	private float ballAngle = 0f;
	private boolean ballInverzeRotation = false;
	private float targetRotation = 0f;


	private static final float ballZoom = 0.93f;


	public BallActor() {
		sprite = new Sprite(textureAtlasRegions.first());
		sprite.setRegion(textureAtlasRegions.first());
		spriteLight = new Sprite(textureLight);
		spriteShadow = new Sprite(textureShadow);
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

private float prevBallAngle2=0f;
	private float ballAngle2=0f;
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
			ballAngle2 = (float) Math.acos(distanceY / distance);
			if (Math.abs(ballAngle - prevBallAngle)>=MathUtils.PI/2 || Math.abs(ballAngle2 - prevBallAngle2)>=MathUtils.PI/2)
			{
				ballInverzeRotation = !ballInverzeRotation;
			}
			if (ballInverzeRotation) {
				targetRotation = 180 - MathUtils.radiansToDegrees * ballAngle;

				ballPictureRotation -= distance / (MathUtils.PI / (float)textureAtlasRegions.size);
			}
			else
			{
				targetRotation = MathUtils.radiansToDegrees * ballAngle;
				ballPictureRotation += distance / (MathUtils.PI / (float)textureAtlasRegions.size);
			}
            /*if (sprite.getRotation())
            {

            }*/
            Gdx.app.log("asd", String.valueOf(sprite.getRotation()));
			float actualRotation = sprite.getRotation();
			float rotation = 0;
			if (Math.abs(actualRotation-targetRotation)>20)
			{
				if (actualRotation<targetRotation) rotation = 5; else rotation= -5;
			}
			else
			{
				if (actualRotation!=targetRotation)
				{
					if (actualRotation<targetRotation)	{
						rotation = Math.abs(actualRotation - targetRotation) /5;
					} else	{
						rotation = -Math.abs(actualRotation - targetRotation) /5;
					}
				}
			}
			//sprite.setRotation(targetRotation);
			sprite.rotate(rotation);
			if (ballPictureRotation < 0) ballPictureRotation= (float)textureAtlasRegions.size-0.00001f;
			if (ballPictureRotation >= textureAtlasRegions.size) ballPictureRotation = 0f;
			sprite.setRegion(textureAtlasRegions.get(((int)(ballPictureRotation))));
		}



		prevBallPositionX = BallPositionX;
		prevBallPositionY = BallPositionY;
		prevBallAngle = ballAngle;
		prevBallAngle2 = ballAngle2;

	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		sprite.setPosition(x + (getWidth()-sprite.getWidth()) / 2,y + (getHeight()-sprite.getHeight()) / 2);
		spriteShadow.setPosition(x, y);
		spriteLight.setPosition(x + (getWidth()-sprite.getWidth()) / 2,y + (getHeight()-sprite.getHeight()) / 2);
	}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		spriteShadow.draw(batch);
		super.draw(batch, parentAlpha);
		spriteLight.draw(batch);
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		sprite.setSize(width*ballZoom, height*ballZoom);
		sprite.setOrigin(width*ballZoom / 2, height*ballZoom / 2);
		spriteShadow.setSize(width, height);
		spriteShadow.setOrigin(width / 2, height / 2);
		spriteLight.setSize(width*ballZoom, height*ballZoom);
		spriteLight.setOrigin(width*ballZoom / 2, height*ballZoom / 2);
	}
}

