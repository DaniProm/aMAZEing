package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

// Disposable: Textúrák takarítása, de csak azok, amelyek nem statikusak!
public abstract class GameActor extends Actor implements Disposable {
	protected static float PIX2M = 64f;
	protected float elapsedTime = 0;
	protected Body body;
	protected World world;
	protected Sprite sprite;

	//protected FixtureDef fd = new FixtureDef();

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		sprite.setPosition(x, y);
	}

	@Override
	public void setSize(float width, float height) {
		super.setSize(width, height);
		sprite.setSize(width, height);
		sprite.setOrigin(width / 2, height / 2);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		elapsedTime += Gdx.graphics.getDeltaTime();
		sprite.draw(batch);
	}

	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees);
		sprite.setRotation(degrees);
	}

	protected Shape getShape() {
		// default shape is a tile
		final PolygonShape shape = new PolygonShape();
		final float hs =  GameScreen.TILE_SIZE / 2 / PIX2M;
		shape.setAsBox(hs, hs, new Vector2(hs, hs), 0);
		return shape;
	}

	final public void detachWorld() {
		if (world == null) return;
		world.destroyBody(body);
		world = null;
	}

	public void delete() {
		detachWorld();
		super.remove();
		dispose();
	}

	final public void applyWorld(World world, BodyDef.BodyType bodyType) {

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.restitution = 0.01f;
		fixtureDef.density = bodyType == BodyDef.BodyType.StaticBody ? 10 : 1;
		fixtureDef.friction = bodyType == BodyDef.BodyType.StaticBody ? 0 : 1;
		fixtureDef.shape = getShape();
		fixtureDef.isSensor = bodyType == BodyDef.BodyType.KinematicBody;

		if (bodyType == BodyDef.BodyType.KinematicBody) {
			bodyType = BodyDef.BodyType.StaticBody;
		}

		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.set(getX() / PIX2M, getY() / PIX2M);

		body = world.createBody(bodyDef);
		body.setFixedRotation(true);//bodyType == BodyDef.BodyType.StaticBody);
		body.setUserData(this);


		//final Shape shape = getShape();
		body.createFixture(fixtureDef);

		// shape.dispose();
		fixtureDef.shape.dispose();

	}

	@Override
	public void act(float delta) {
		//super.act(delta);

		final Vector2 pos = body.getPosition();
		setPosition(pos.x * PIX2M, pos.y * PIX2M);

	}
}
