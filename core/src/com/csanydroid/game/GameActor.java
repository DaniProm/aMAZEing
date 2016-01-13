package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

// Disposable: Textúrák takarítása, de csak azok, amelyek nem statikusak!
public abstract class GameActor extends Actor implements Disposable {

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

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		body.setActive(visible);
	}

    public void deactivate() {
        body.setActive(false);
    }

	public void activate() {
		body.setActive(true);
	}

	protected Shape getShape() {
		return getTileShape();
	}

	protected static Shape getTileShape() {
		return getTileShape(1);
	}

	protected static Shape getTileShape(float scale) {
		final PolygonShape shape = new PolygonShape();
		shape.setAsBox(
	          .5f * scale,
	          .5f * scale,
	          new Vector2(.5f, .5f),
	          0
		);
		return shape;

	}

	protected static Shape getCircleShape(float scale) {
		scale /= 2; // to radius

		final CircleShape shape = new CircleShape();
		shape.setRadius(scale);
		shape.setPosition(new Vector2(.5f, .5f));
        return shape;
	}

	protected static Shape getCircleShape() {
		return getCircleShape(1);
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

	public void applyWorld(World world, BodyDef.BodyType bodyType) {

		if (bodyType == BodyDef.BodyType.KinematicBody) {
			bodyType = BodyDef.BodyType.StaticBody;
		}

		this.world = world;
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.set(getX(), getY());

		body = world.createBody(bodyDef);
		body.setFixedRotation(true);//bodyType == BodyDef.BodyType.StaticBody);
		body.setUserData(this);
		body.setLinearDamping(0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 0;
        fixtureDef.density = bodyType == BodyDef.BodyType.StaticBody ? 10 : 1;
        fixtureDef.friction = bodyType == BodyDef.BodyType.StaticBody ? 0 : 1;
        fixtureDef.shape = getShape();

		body.createFixture(fixtureDef);

		fixtureDef.shape.dispose();

	}

	@Override
	public void act(float delta) {
		//super.act(delta);

		final Vector2 pos = body.getPosition();
		setPosition(pos.x, pos.y);

	}

	@Override
	public void dispose() { }

	{
		setBounds(0,0,1,1);
	}
}
