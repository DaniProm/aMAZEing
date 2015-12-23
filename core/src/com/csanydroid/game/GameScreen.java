package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
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

	public static float BASE_SIZE = 128; // TODO máshova kell rakni valószínűleg
	BallActor ball = new BallActor();
	private Stage stage = new Stage(viewport) {

		@Override
		public void act(float delta) {
			super.act(delta);
			world.setGravity(new Vector2(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX()));
		}

	};

	Box2DDebugRenderer debugger = new Box2DDebugRenderer();
	World world;

	GameScreen(String maze) throws IOException {
		super();

		world = new World(Vector2.Zero, false);

		initMaze(maze);

		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				final Body
						bodyA = contact.getFixtureA().getBody(),
						bodyB = contact.getFixtureB().getBody();
				final GameActor
						a = (GameActor)(bodyA.getUserData()),
						b = (GameActor)(bodyB.getUserData());

				Gdx.app.log("contact", a + " - " + b);
				bodyB.setTransform(10, 10, 0);
				contact.setEnabled(false);
				if(Math.random() < 0) return;

				if(b instanceof BallActor) {
					if (a instanceof PuddleActor) {
						contact.setFriction(1000);
						contact.setEnabled(true);
						Gdx.app.log("contact", "pocsolyába léptem...");
					} else if (a instanceof WormholeActor) {
						contact.setEnabled(false);

						Gdx.app.log("contact", "blablabla...: " + ((WormholeActor)a).active);
						if(((WormholeActor)a).active < 0) {
							Vector2 newPos = ((WormholeActor) a).getEndpoint().body.getPosition();
							bodyB.setTransform(newPos.x - 100, newPos.y, 0);

							((WormholeActor) a).active = 3;
							((WormholeActor) a).getEndpoint().active = 3;
						}

					}
				}

				if(a instanceof WallActor){
					if(bodyA.getLinearVelocity().len() > WallActor.pwOfExpWall) {
						WallActor.itWillExp = true;
						ball.destroy();
					}
				}




				//.applyForceToCenter(contact.getFixtureB().getBody().getLinearVelocity(),true);
				//contact.getFixtureB().getBody().applyForceToCenter(contact.getFixtureA().getBody().getLinearVelocity(),true);

			}


			@Override
			public void endContact(Contact contact) {
				// http://stackoverflow.com/questions/18051102/libgdx-box2d-how-to-destroy-body-after-collision
				// :) http://stackoverflow.com/questions/18381845/remove-body-from-world-when-a-collision-occurs :)

				/*final GameActor
						a = (GameActor)(contact.getFixtureA().getBody().getUserData()),
						b = (GameActor)(contact.getFixtureB().getBody().getUserData());

				if (b instanceof BallActor && a instanceof BlackHoleActor) {
					b.removeWorld();
				}*/
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) { }

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) { }

		});

	}

	private void initMaze(String maze) throws IOException {
		BufferedReader reader = new BufferedReader(Gdx.files.internal("mazes/" + maze + ".txt").reader());
		final float height = Gdx.graphics.getHeight();
		WormholeActor[] wormholes = new WormholeActor['F' - 'A' + 1];

		int y = 0;
		for (String line; null != (line = reader.readLine()); ) {
			y++;

			for (int x = 0; x < line.length(); ++x) {
				GameActor actor = null;
				BodyDef.BodyType bodyType = null;

				final char ch = line.charAt(x);
				switch (ch) {
					case ' ':
						break; // do nothing
					case '.': // wall
						actor = new WallActor(true);//Ha true akkor nem robban
						bodyType = BodyDef.BodyType.StaticBody;
						break;
					case 'O': // ball
						actor = new BallActor();
						bodyType = BodyDef.BodyType.DynamicBody;
						theActor = actor;
						break;
					case 'X': // hole
						actor = new HoleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case '*': // door
						//actor = new DoorActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case '@': // black hole
						actor = new BlackHoleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case '~': // puddle
						actor = new PuddleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case ':': //explosion wall
						actor = new WallActor(false);//Ha false, akkor felrobbanós fal lesz
						bodyType = BodyDef.BodyType.DynamicBody;
					default:

						// wormhole
						int i;
						if (ch >= 'a' && ch <= 'f') i = ch - 'a';
						else if (ch >= 'A' && ch <= 'F') i = ch - 'A';
						else break;

						actor = new WormholeActor();
						bodyType = BodyDef.BodyType.KinematicBody;

						if (wormholes[i] == null) {
							wormholes[i] = (WormholeActor) actor;
						} else {
							((WormholeActor) actor).setEndpoint(wormholes[i]);
							wormholes[i].setEndpoint((WormholeActor) actor);
						}

						break;
				}

				if (actor != null) {
					actor.setPosition(x * BASE_SIZE, height - y * BASE_SIZE);

					actor.applyWorld(world, bodyType);

					stage.addActor(actor);

					if(actor instanceof BallActor) {
						Gdx.app.log("aaaaaa", "bbbbbbbbbbbbb");
						actor.setZIndex(1000);
					}

				}

			}

		}

		reader.close();

	}

	GameActor theActor = null;

	//float minZoom = 1, maxZoom = 2;

	@Override
	public void render(float delta) {


		super.render(delta);
		batch.begin();

		camera.position.x = theActor.getX();
		camera.position.y = theActor.getY();

		camera.zoom = .7f;//1 / (theActor.body.getLinearVelocity().len() - 2f);
		//if (camera.zoom < minZoom) camera.zoom = minZoom;
		//if (camera.zoom > maxZoom) camera.zoom = maxZoom;


		final float deltaTime = Gdx.graphics.getDeltaTime();
		world.step(deltaTime, 1, 1);
		stage.act(deltaTime);
		stage.draw();
		//actorExample1.body.applyForceToCenter(-200f, -200f, false);

		debugger.render(world, camera.combined);
		batch.end();

	}


}
