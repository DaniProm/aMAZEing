package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ScreenExample extends MyScreen {
    private Stage stage;
    ActorExample actorExample1, actorExample2, actorExample3;
    Box2DDebugRenderer debugger;
    World world;


    ScreenExample() {
        super();
        debugger = new Box2DDebugRenderer();
        world = new World(new Vector2(0, 0), true);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //contact.getFixtureA().getBody().applyForceToCenter(contact.getFixtureB().getBody().getLinearVelocity(),true);
                //contact.getFixtureB().getBody().applyForceToCenter(contact.getFixtureA().getBody().getLinearVelocity(),true);

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });

        stage = new Stage(viewport);


        actorExample1 = new ActorExample();
        actorExample1.setPosition(50, WORLD_HEIGHT);
        actorExample1.setSize(20, 20);
        actorExample1.setWorld(world, BodyDef.BodyType.DynamicBody);
        //actorExample1.body.applyForceToCenter(0,-100000f,true);
        stage.addActor(actorExample1);

        actorExample2 = new ActorExample();
        actorExample2.setPosition(70, 40);
        actorExample2.setSize(20, 20);
        actorExample2.setWorld(world, BodyDef.BodyType.DynamicBody);
        stage.addActor(actorExample2);

        actorExample3 = new ActorExample();
        actorExample3.setPosition(45, WORLD_HEIGHT/2);
        actorExample3.setSize(20, 20);
        actorExample3.setWorld(world, BodyDef.BodyType.DynamicBody);
        stage.addActor(actorExample3);

    }

    @Override
    public void show() {
        super.show();
    }

    float x;
    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        //x+=0.001f;
        camera.translate(0.1f,0.1f);

        camera.update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        world.step(Gdx.graphics.getDeltaTime(), 1, 1);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        //actorExample1.body.applyForceToCenter(-200f, -200f, false);


        debugger.render(world, camera.combined);
        batch.end();

    }
}
