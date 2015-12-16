package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

import sun.security.provider.SHA;


public abstract class MyActor extends Actor implements Disposable {
    protected float elapsedTime = 0;
    protected Body body;
    protected World world;
    protected BodyDef bodyDef = new BodyDef();

    abstract protected void createSprite();

    abstract protected Shape getShape();

    @Override
    abstract public void dispose(); //Text�r�k takar�t�sa, de csak azok, amelyek nem statikusak!

    @Override
    public void draw(Batch batch, float parentAlpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    public void setWorld(World world, BodyDef.BodyType bodyType)
    {
        this.world = world;
        bodyDef.type = bodyType;
        bodyDef.position.x = getX();
        bodyDef.position.y = getY();

        this.body = this.world.createBody(bodyDef);
        this.body.setFixedRotation(false);
        this.body.setUserData(this);
        Shape shape=getShape();
        body.createFixture(shape, 0);
        shape.dispose();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (world!=null) {
            final Vector2 pos = body.getPosition();
            setPosition(pos.x, pos.y);
        }
    }
}
