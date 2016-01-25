package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class BallActor extends GameActor {

	private float Timer = -1;
	private float scale = 1;
	private final Array<TextureAtlas.AtlasRegion> textureAtlasRegions;

	protected Sprite spriteLight;
	protected Sprite spriteShadow;

	private float prevBallPositionX=0, prevBallPositionY=0;
	private float ballPictureRotation=0.8f;
	private float ballAngle = 0, prevBallAngle = 0;
	private boolean ballInverzeRotation = false;

	private boolean onBlackhole = false;


	//private static int createdBallsNumber = 0;

	private static final float ballZoom = 0.93f;

	public BallActor() {
		final TextureAtlas[] textureAtlases = new TextureAtlas[] {
			 Assets.manager.get(Assets.BALL_BLUE_ATLAS),
             Assets.manager.get(Assets.BALL_GREEN_ATLAS),
             Assets.manager.get(Assets.BALL_ORANGE_ATLAS),
             Assets.manager.get(Assets.BALL_RED_ATLAS),
		};
		//ballColorIndex = createdBallsNumber % textureAtlasRegions.size;

		textureAtlasRegions = textureAtlases[(int)(Math.random() * textureAtlases.length)].getRegions();

		//createdBallsNumber++;
		sprite = new Sprite(textureAtlasRegions.first());
		sprite.setRegion(textureAtlasRegions.first());
		spriteLight = new Sprite(Assets.manager.get(Assets.BALL_LIGHT));
		spriteShadow = new Sprite(Assets.manager.get(Assets.BALL_SHADOW));
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

	public static float minAngleRad(float a, float b)
	{
		return Math.min(Math.min(Math.abs(a  - b), Math.abs((a - MathUtils.PI2) - b)), Math.abs(a - (b - MathUtils.PI2)));
	}
	public static float minAngleDeg(float a, float b)
	{
		return Math.min(Math.min(Math.abs(a - b), Math.abs((a - 180) - b)), Math.abs(a - (b - 180)));
	}

	public static float absAngleDeg(float a)
	{
		return (a+36000) % 360;
	}



	public void setBlackhole()
	{
		onBlackhole = true;
		deactivate();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		float BallPositionX = body.getPosition().x;
		float BallPositionY = body.getPosition().y;
		float distanceX = prevBallPositionX - BallPositionX;
		float distanceY = prevBallPositionY - BallPositionY;

		float distance = (float) Math.sqrt((double) (distanceX * distanceX + distanceY * distanceY));
		if (distance>0) {
			//Gdx.app.log("Time", String.valueOf(elapsedTime));
			if (distanceX <= 0 && distanceY <= 0) {
				//Gdx.app.log("Irany", "Jobbra, fel");
				ballAngle = MathUtils.PI - (float) Math.acos(distanceX / distance);
			}
			else {
				if (distanceX <= 0 && distanceY >= 0) {
					ballAngle = MathUtils.PI  + (float) Math.acos(distanceX / distance);

					//Gdx.app.log("Irany", "Jobbra, le");
				}
				else {
					if (distanceX >= 0 && distanceY <= 0) {
						//Gdx.app.log("Irany", "Balra, fel");
						ballAngle = MathUtils.PI - (float) Math.acos(distanceX / distance);

					}
					else {
						if (distanceX >= 0 && distanceY >= 0) {
							ballAngle = MathUtils.PI  + (float) Math.acos(distanceX / distance);
							//Gdx.app.log("Irany", "Balra, le");
						}
						else
						{
							//Gdx.app.log("Irany", "----");
						}
					}
				}
			}
			float actualRotation = sprite.getRotation();
			float rotationAngle = minAngleRad(ballAngle, prevBallAngle)* MathUtils.radiansToDegrees;// Math.min(Math.min(Math.abs(ballAngle  - prevBallAngle), Math.abs((ballAngle - MathUtils.PI2) - prevBallAngle)), Math.abs(ballAngle - (prevBallAngle - MathUtils.PI2)))* MathUtils.radiansToDegrees;
			if (rotationAngle>90)
			{
				ballInverzeRotation = !ballInverzeRotation;
				//Gdx.app.log("Fordult", String.valueOf(ballInverzeRotation));
			}

			float targetRotation;
			if (ballInverzeRotation) {
				targetRotation = MathUtils.radiansToDegrees * ballAngle;
				ballPictureRotation -= distance / (MathUtils.PI / (float)textureAtlasRegions.size);
			}
			else
			{
				targetRotation = MathUtils.radiansToDegrees * ballAngle  - 180;
				ballPictureRotation += distance / (MathUtils.PI / (float)textureAtlasRegions.size);
			}

			float rotation = targetRotation;
			if (minAngleDeg(absAngleDeg(actualRotation), absAngleDeg(targetRotation))<130)
			{
				if (minAngleDeg(absAngleDeg(actualRotation), absAngleDeg(targetRotation))>20)
				{
					if (absAngleDeg(targetRotation)>absAngleDeg(actualRotation))
					{
						rotation = actualRotation + 10;
					}
					else
					{
						rotation = actualRotation - 10;
					}
				}
				else
				{
					rotation = (absAngleDeg(actualRotation) + absAngleDeg(targetRotation)) / 2;
				}
			}
			//Gdx.app.log("Actual: ",String.valueOf(absAngleDeg(actualRotation)) + " Target:" + String.valueOf(absAngleDeg(targetRotation)));

			sprite.setRotation(rotation);
			//sprite.setRotation(targetRotation);
			if (ballPictureRotation < 0) ballPictureRotation= (float)textureAtlasRegions.size-0.00001f;
			if (ballPictureRotation >= textureAtlasRegions.size) ballPictureRotation = 0f;
			sprite.setRegion(textureAtlasRegions.get(((int) (ballPictureRotation))));
		}



		prevBallPositionX = BallPositionX;
		prevBallPositionY = BallPositionY;
		prevBallAngle = ballAngle;
		//prevBallAngle2 = ballAngle2;



		// Teleport //

		if (Timer>=0) {
			Timer+=delta;
		}
		if (Timer>=0 && Timer<=1.5)
		{
			setPosition((getX()+(float)Math.ceil(wa.getX()))/2, (getY()+(float)Math.ceil(wa.getY()))/2);
			if (scale>0) {
				scale -= 0.1f;
				setSize(scale, scale);
				setPosition(getX()+getWidth()/2-scale+0.5f, getY()+getHeight()/2-scale+0.5f);
			}
		}
		/*if (Timer>0.5 && Timer<=1.5)
		{
			scale-=0.2f;
            sprite.setSize(scale, scale);
		}*/
		if (Timer > 1.5f && tx!=-10000) {
			body.setTransform(tx, ty, 0);
			tx = -10000;
			ty = -10000;
		}
		if (Timer > 2) {
			if (scale<=1) {
				scale += 0.1f;
				setSize(scale, scale);
				setPosition(getX()+getWidth()/2-scale+0.5f, getY()+getHeight()/2-scale+0.5f);
			}
		}
		if (Timer>0 && scale >= 1)
		{
			Timer = -1;
			activate();
			wa.setActive(1);
			wb.setActive(1);
			setSize(1, 1);
		}

		// Teleport //

		// Daráló //
		if (onBlackhole)
		{
			final float alpha = sprite.getColor().a;
			sprite.setAlpha(alpha * 0.97f);
			spriteLight.setAlpha(alpha * 0.97f);
			spriteShadow.setAlpha(alpha * 0.97f);
			if(alpha < 0.1f) {
				delete();
			}

		}
		// Daráló //

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
		spriteLight.setSize(width * ballZoom, height * ballZoom);
		spriteLight.setOrigin(width * ballZoom / 2, height * ballZoom / 2);
	}


	private float tx= -10000;
	private float ty= -10000;
	private WormholeActor wa = null;
	private WormholeActor wb = null;
	public void Teleport(float x, float y, WormholeActor a, WormholeActor b)
	{
		deactivate();
        Assets.manager.get(Assets.TELEPORTATION_SOUND).play();

		wa = a;
		wb = b;
		tx = x;
		ty = y;
		Timer=0;
		scale=1;
	}

}

