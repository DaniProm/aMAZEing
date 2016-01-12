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

	protected static Array<Array<TextureAtlas.AtlasRegion>> textureAtlasRegions = new Array<Array<TextureAtlas.AtlasRegion>>();
	static
	{
		textureAtlasRegions.add(new TextureAtlas("ballgreen.atlas").getRegions());
		textureAtlasRegions.add(new TextureAtlas("ballred.atlas").getRegions());
		textureAtlasRegions.add(new TextureAtlas("ballorange.atlas").getRegions());
		textureAtlasRegions.add(new TextureAtlas("ballblue.atlas").getRegions());
	}
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

	private static int createdBallsNumber = 0;
	private int ballColorIndex = 0;


	private static final float ballZoom = 0.93f;


	public BallActor() {
		ballColorIndex = createdBallsNumber % textureAtlasRegions.size;
		createdBallsNumber++;
		sprite = new Sprite(textureAtlasRegions.get(ballColorIndex).first());
		sprite.setRegion(textureAtlasRegions.get(ballColorIndex).first());
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

	public static float minAngleRad(float a, float b)
	{
		return Math.min(Math.min(Math.abs(a  - b), Math.abs((a - MathUtils.PI2) - b)), Math.abs(a - (b - MathUtils.PI2)));
	}
	public static float minAngleDeg(float a, float b)
	{
		return Math.min(Math.min(Math.abs(a  - b), Math.abs((a - 180) - b)), Math.abs(a - (b - 180)));
	}

	public static float absAngleDeg(float a)
	{
		return (a+36000) % 360;
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
		if (distance>0.01) {
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
				Gdx.app.log("Fordult", String.valueOf(ballInverzeRotation));
			}
			if (ballInverzeRotation) {
				targetRotation = MathUtils.radiansToDegrees * ballAngle;
				ballPictureRotation -= distance / (MathUtils.PI / (float)textureAtlasRegions.get(ballColorIndex).size);
			}
			else
			{
				targetRotation = MathUtils.radiansToDegrees * ballAngle  - 180;
				ballPictureRotation += distance / (MathUtils.PI / (float)textureAtlasRegions.get(ballColorIndex).size);
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
			Gdx.app.log("Actual: ",String.valueOf(absAngleDeg(actualRotation)) + " Target:" + String.valueOf(absAngleDeg(targetRotation)));

			sprite.setRotation(rotation);
			//sprite.setRotation(targetRotation);
			if (ballPictureRotation < 0) ballPictureRotation= (float)textureAtlasRegions.get(ballColorIndex).size-0.00001f;
			if (ballPictureRotation >= textureAtlasRegions.get(ballColorIndex).size) ballPictureRotation = 0f;
			sprite.setRegion(textureAtlasRegions.get(ballColorIndex).get(((int) (ballPictureRotation))));
		}



		prevBallPositionX = BallPositionX;
		prevBallPositionY = BallPositionY;
		prevBallAngle = ballAngle;
		//prevBallAngle2 = ballAngle2;

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

