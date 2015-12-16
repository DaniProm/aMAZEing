package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class ActorExample extends MyActor {

	protected Sprite sprite;


	ActorExample() {
		createSprite();
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width,height);
		sprite.setSize(width, height);
		sprite.setOrigin(width / 2, height / 2);
	}

	@Override
	protected void createSprite()
	{
		sprite = new Sprite(new Texture("badlogic.jpg"));
	}

	protected Shape getShape()
	{
		//PolygonShape polygonShape = new PolygonShape();
		//polygonShape.setAsBox(getWidth() / 2, getHeight() / 2, new Vector2(getWidth() / 2, getHeight() / 2), 0);
		//polygonShape.set(new float[]{-10,20,-10,0,0,-30,10,0,10,10});


		CircleShape circleShape = new CircleShape();
		circleShape.setPosition(new Vector2(getWidth() / 2, getHeight() / 2));
		circleShape.setRadius(getWidth() / 2);

		return circleShape;
//		return polygonShape;
	}


	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		sprite.setPosition(x, y);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		sprite.draw(batch);
	}

	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees);
		sprite.setRotation(degrees);
	}


	@Override
	public void dispose() {
		sprite.getTexture().dispose();
	}
}
