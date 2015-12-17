package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.BufferedReader;
import java.io.IOException;

public class GameScreen extends MyScreen {

    private float BASE_SIZE = 40; // TODO máshova kell rakni valószínűleg
    private Stage stage;
    MyActor actorExample1, actorExample2, actorExample3;
    Box2DDebugRenderer debugger = new Box2DDebugRenderer();
    World world;

    GameScreen(String maze) throws IOException {
        super();

        initMaze(maze);

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


        actorExample1 = new BallActor();
        actorExample1.setPosition(50, WORLD_HEIGHT);
        actorExample1.setSize(20, 20);
        actorExample1.setWorld(world, BodyDef.BodyType.DynamicBody);
        //actorExample1.body.applyForceToCenter(0,-100000f,true);
        stage.addActor(actorExample1);

        actorExample2 = new BallActor();
        actorExample2.setPosition(70, 40);
        actorExample2.setSize(20, 20);
        actorExample2.setWorld(world, BodyDef.BodyType.DynamicBody);
        stage.addActor(actorExample2);

        actorExample3 = new WallActor();
        actorExample3.setPosition(45, WORLD_HEIGHT/2);
        actorExample3.setSize(20, 20);
        actorExample3.setWorld(world, BodyDef.BodyType.DynamicBody);
        stage.addActor(actorExample3);

    }

    private void initMaze(String maze) throws IOException {
        BufferedReader reader = new BufferedReader(Gdx.files.internal("mazes/" + maze + ".txt").reader());

        int y = 0;
        for (String line; null != (line = reader.readLine()); y++) {

            for(int x = 0;x<line.length();) {
                MyActor actor = null;
                final char ch = line.charAt(x++);
                switch (ch) {
                    case ' ': break; // do nothing
                    case 'O': // ball
                        actor = new BallActor();
                        break;
                    case 'X': // hole
                        actor = new HoleActor();
                        break;
                    case '.': // wall
                        actor = new WallActor();
                        break;
                    case '*': // door
                        actor = new DoorActor();
                        break;
                    case '@': // black hole
                        actor = new HoleActor();
                        ((HoleActor) actor).setEndpoint(null);
                        break;
                    case '~': // puddle
                        actor = new PuddleActor();
                        break;
                    case '[': //warmhole
                        actor = new WormholeActor();
                        break;
                    case '¤': //blackhole :3
                        actor = new BlackHoleActor();
                    default:
                        if((ch >= 'A' && ch < 'X') || (ch >= 'a' && ch < 'x')) {

                        }
                        break;
                }

                if(actor != null) {
                    stage.addActor(actor);
                    actor.setPosition(x * BASE_SIZE, y * BASE_SIZE);
                }

            }

        }

        reader.close();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();

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
