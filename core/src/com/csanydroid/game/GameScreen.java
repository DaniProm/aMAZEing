package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends MyScreen {

	public static float TILE_SIZE = 128;
	public ArrayList<BallActor> balls = new ArrayList<BallActor>();
	public World world;
	private Box2DDebugRenderer debugger = new Box2DDebugRenderer();
	private Stage gameStage = new Stage(viewport) {

		@Override
		public void act(float delta) {
			super.act(delta);
			world.setGravity(new Vector2(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX()));
		}

		@Override
		public void addActor(Actor actor) {
			super.addActor(actor);

			if(actor instanceof BallActor) {
				balls.add((BallActor) actor);
				actor.setZIndex(0x1000);
			}

		}
	};
	private Stage asfd = new Stage() {

	};

	GameScreen(String maze) throws IOException {
		super();

		world = new World(Vector2.Zero, false);

		initMaze(maze);

		world.setContactFilter(new ContactFilter() {

			@Override
			public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
				return fixtureA.getBody().getUserData() instanceof GameActor || fixtureB.getBody().getUserData() instanceof GameActor;
			}

		});

		world.setContactListener(new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				final Body
						bodyA = contact.getFixtureA().getBody(),
						bodyB = contact.getFixtureB().getBody();
				final GameActor
						a = (GameActor) (bodyA.getUserData()),
						b = (GameActor) (bodyB.getUserData());

				if (b instanceof BallActor) {
					contact.setEnabled(true);

					if (a instanceof PuddleActor) {
						// TODO friction
					} else if (a instanceof BlackHoleActor) {
						((BlackHoleActor) a).swallowBall((BallActor) b);
					} else if (a instanceof WormholeActor) {
						((WormholeActor) a).transportBall((BallActor) b);
					} else if (a instanceof ExplosiveWallActor) {
						if (bodyB.getLinearVelocity().len() >= ((ExplosiveWallActor) a).getStrength()) {
							((ExplosiveWallActor) a).explode();
						}
					} else if (a instanceof HoleActor) {
						((HoleActor) a).swallowBall((BallActor)b);
					}

				}

				//.applyForceToCenter(contact.getFixtureB().getBody().getLinearVelocity(),true);
				//contact.getFixtureB().getBody().applyForceToCenter(contact.getFixtureA().getBody().getLinearVelocity(),true);

			}

			@Override
			public void endContact(Contact contact) { }

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
		String line;

		line = reader.readLine(); // maze description

		int y = 0;
		while (null != (line = reader.readLine())) {
			y++;

			for (int x = 0; x < line.length(); ++x) {
				GameActor actor = null;
				BodyDef.BodyType bodyType = null;

				final char ch = line.charAt(x);
				switch (ch) {
					case ' ':
						break; // space: do nothing
					case '.': // wall
						actor = new WallActor();
						bodyType = BodyDef.BodyType.StaticBody;
						break;
					case 'O': // ball
						actor = new BallActor();
						bodyType = BodyDef.BodyType.DynamicBody;
						break;
					case 'X': // hole
						actor = new HoleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case '*': // door
						actor = new DoorActor();
						bodyType = BodyDef.BodyType.StaticBody;
						break;
					case '@': // black hole
						actor = new BlackHoleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case '~': // puddle
						actor = new PuddleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case ':': // explosive wall
						actor = new ExplosiveWallActor(10.0f);
						bodyType = BodyDef.BodyType.StaticBody;
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
					actor.setPosition(x * TILE_SIZE, height - y * TILE_SIZE);

					actor.applyWorld(world, bodyType);

					gameStage.addActor(actor);
				}

			}

		}

		reader.close();

	}

	@Override
	public void render(float delta) {

		super.render(delta);
		batch.begin();

		world.step(delta, 1, 1);

		gameStage.act(delta);
		{

			final float BALL_MARGIN = 100;
			float left = 0, right = 0, bottom = 0, top = 0, height, width;

			if (balls.size() > 0) {
				BallActor ball;
				ball = balls.get(0);

				right = left = ball.getX();
				top = bottom = ball.getY();

				for (int i = balls.size(); --i > 0; ) {
					ball = balls.get(i);
					final float x = ball.getX(), y = ball.getY();
					if (x > right) right = x;
					else if (x < left) left = x;

					if (y > top) top = y;
					else if (y < bottom) bottom = y;
				}

				top += TILE_SIZE / 2 + BALL_MARGIN;
				bottom += TILE_SIZE / 2 - BALL_MARGIN;

				left += TILE_SIZE / 2 - BALL_MARGIN;
				right += TILE_SIZE / 2 + BALL_MARGIN;

			}

			height = Math.max(top - bottom, 100);
			width = Math.max(right - left, 100);

			camera.position.x = (right + left) / 2;
			camera.position.y = (top + bottom) / 2;

			camera.zoom = Math.max(height / camera.viewportHeight, width / camera.viewportWidth);

		}

		gameStage.draw();

		debugger.render(world, camera.combined);
		batch.end();

	}


}
