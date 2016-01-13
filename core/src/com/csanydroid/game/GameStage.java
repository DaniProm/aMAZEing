package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameStage extends Stage implements GestureDetector.GestureListener {
	private final static String DEFAULT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890\"!`?'.,;:()[]{}<>|/@\\^$-%+=#_&~* ÖÜÓŐÚÉÁŰÍöüóőúéáűí";
	protected static BitmapFont scribbleFont;

	static {
		// http://www.fontsquirrel.com/fonts/list/language/hungarian
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AlegreyaSC-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 32;
		parameter.kerning = false;
		parameter.characters = DEFAULT_CHARS;
		parameter.color = Color.valueOf("#f0f0f0");
		scribbleFont = generator.generateFont(parameter);
		generator.dispose();

	}

	protected static final Label.LabelStyle LABEL_STYLE;

	static	{
		LABEL_STYLE = new Label.LabelStyle();
		LABEL_STYLE.font = scribbleFont;
	}

	public World world = new World(Vector2.Zero, false);

	private final ArrayList<BallActor> balls = new ArrayList<BallActor>();
	private final ArrayList<HoleActor> holes = new ArrayList<HoleActor>();

	public Maze getMaze() {
		return maze;
	}

	private final Maze maze;

	private byte totalStars, collectedStars;

	public void collectStar() {
		++collectedStars;
	}

    public byte getCollectedStars() {
        return collectedStars;
    }

    public void removeBall(BallActor ball) {
		Gdx.input.vibrate(250);
		balls.remove(ball);

		if (balls.size() == 0) {
			// TODO to do
			// vége a játéknak
			if (everyHoleHasBall()) {
				Gdx.app.log("játék", "Nyertem! :)");
			} else {
				Gdx.app.log("játék", "Vesztettem!");
			}
			Gdx.app.log("játék", "Sikerült " + collectedStars + " csillagot összegyűjtenem a " + totalStars + "-ra/-hoz/-ig/-ből/-ba/-tól.");
		} else {
			Gdx.app.log("játék", "Ajjaj! Kipukkadt egy lasztim...");
		}
	}

	private boolean everyHoleHasBall() {
		for (HoleActor hole : holes) {
			if (!hole.hasSwallowedBall()) return false;
		}
		return true;
	}

	{

		world.setContactFilter(new ContactFilter() {

			@Override
			public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
				return fixtureA.getBody().getUserData() instanceof GameActor || fixtureB.getBody().getUserData() instanceof GameActor;
			}

		});

		world.setContactListener(new ContactListener() {
			                         @Override
			                         public void beginContact(Contact contact) {
				                         GameActor other;
				                         BallActor ball;

				                         {
					                         final Body bodyObj = contact.getFixtureA().getBody(), bodyBall = contact.getFixtureB().getBody();
					                         if (!(bodyBall.getUserData() instanceof BallActor)) {
						                         ball = (BallActor) bodyObj.getUserData();
						                         other = (GameActor) bodyBall.getUserData();
					                         } else {
						                         ball = (BallActor) bodyBall.getUserData();
						                         other = (GameActor) bodyObj.getUserData();
					                         }
				                         }

				                         if (other instanceof PuddleActor) {
					                         ball.body.setLinearDamping(4f);
				                         } else if (other instanceof BlackHoleActor) {
					                         ((BlackHoleActor) other).swallowBall(ball);
				                         } else if (other instanceof WormholeActor) {
					                         ((WormholeActor) other).transportBall(ball);
				                         } else if (other instanceof ExplosiveWallActor) {
					                         if (ball.body.getLinearVelocity().len() >= ((ExplosiveWallActor) other).getStrength()) {
						                         ((ExplosiveWallActor) other).explode();
					                         }
				                         } else if (other instanceof HoleActor) {
					                         ((HoleActor) other).swallowBall(ball);
				                         } else if (other instanceof StarActor) {
					                         ((StarActor) other).collect();
				                         }

			                         }

			                         @Override
			                         public void endContact(Contact contact) {
				                         GameActor other;
				                         BallActor ball;

				                         {
					                         final Body bodyObj = contact.getFixtureA().getBody(), bodyBall = contact.getFixtureB().getBody();
					                         if (!(bodyBall.getUserData() instanceof BallActor)) {
						                         ball = (BallActor) bodyObj.getUserData();
						                         other = (GameActor) bodyBall.getUserData();
					                         } else {
						                         ball = (BallActor) bodyBall.getUserData();
						                         other = (GameActor) bodyObj.getUserData();
					                         }
				                         }


				                         if (other instanceof PuddleActor) {
					                         ball.body.setLinearDamping(0);
				                         }

			                         }

			                         @Override
			                         public void preSolve(Contact contact, Manifold oldManifold) { }

			                         @Override
			                         public void postSolve(Contact contact, ContactImpulse impulse) { }

		                         }

		);

	}

	public GameStage(Viewport viewport, Batch batch, Maze maze) {
		super(viewport, batch);
		this.maze = maze;
		loadMaze(maze);
		int bgsize = maze.getWidth() > maze.getHeight() ? maze.getWidth() : maze.getHeight();
		Gdx.app.log("bgsize", bgsize + "");
		BackgroundActor backgroundActor = new BackgroundActor();
		backgroundActor.setSize(bgsize + 4, bgsize + 4);
		backgroundActor.setPosition(-2f + (bgsize - maze.getWidth()) / 2, -bgsize - 2f + (bgsize - maze.getHeight()) / 2);
		addActor(backgroundActor);
	}

	public World getWorld() {
		return world;
	}

	static final float BALL_HORIZON = 2.5f; // a golyónál hányszor nagyobb teret lásson

	public void lookAtMaze(final OrthographicCamera camera) {
        camera.position.x = maze.getWidth() / 2f;
        camera.position.y = maze.getHeight() / -2f;
        camera.zoom = Math.max(maze.getHeight() / camera.viewportHeight, maze.getWidth() / camera.viewportWidth);
	}

	public void updateCamera(final OrthographicCamera camera) {

		if (balls.size() > 0) {
			float left, right, bottom, top, height, width;

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

			top += .5f + BALL_HORIZON;
			bottom += .5f - BALL_HORIZON;

			left += .5f - BALL_HORIZON;
			right += .5f + BALL_HORIZON;


			height = Math.max(top - bottom, BALL_HORIZON);
			width = Math.max(right - left, BALL_HORIZON);

			float newValue;

			newValue = (right + left) / 2;
			camera.position.x += (newValue - camera.position.x) / 10;
			newValue = (top + bottom) / 2;
			camera.position.y += (newValue - camera.position.y) / 10;

			newValue = additionalZoom * Math.max(height / camera.viewportHeight, width / camera.viewportWidth);
			camera.zoom += (newValue - camera.zoom) / 30f;

		} else {
            lookAtMaze(camera);
        }

	}

	private float additionalZoom = 1;

    boolean controlWithMouse = false;

	@Override
	public void act(float delta) {
		if (world == null) return;

        switch (Gdx.app.getType()) {
            case Android:
                world.setGravity(new Vector2(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX()));
                break;
            case Desktop:

                if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
                    controlWithMouse = !controlWithMouse;
                    Gdx.app.log("control", "vezérlés " + (controlWithMouse ? "egérrel" : "billentyűvel"));
                }

                if(!controlWithMouse) {
                    Vector2 gravity = world.getGravity();

                    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                        gravity.x = 5;
                    } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                        gravity.x = -5;
                    } else {
                        gravity.x = 0;
                    }

                    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                        gravity.y = -5;
                    } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                        gravity.y = 5;
                    } else {
                        gravity.y = 0;
                    }

                    world.setGravity(gravity);
                } else {

                    world.setGravity(new Vector2(
                            ((float) Gdx.input.getX() / Gdx.graphics.getWidth() - .5f) * 20,
                            -((float) Gdx.input.getY() / Gdx.graphics.getHeight() - .5f) * 20
                    ));

                }

                break;
        }


		world.step(delta, 1, 1);

		super.act(delta);


	}

@Override
	public void addActor(Actor actor) {
		super.addActor(actor);

		if (!(actor instanceof BallActor)) actor.toBack();
		else actor.toFront();

		if (actor instanceof BallActor) {
			balls.add((BallActor) actor);
		} else if (actor instanceof HoleActor) {
			holes.add((HoleActor) actor);
		} else if (actor instanceof StarActor) {
			++totalStars;
		} else if (actor instanceof Scribble) {
			actor.setZIndex(0);
		}

	}

	private void loadMaze(Maze maze) {

        WormholeActor[] wormholes = new WormholeActor['F' - 'A' + 1];
        for (Maze.MazeObject o : maze.getObjects()) {

				if(o.getType() == Maze.ObjectType.SCRIBBLE) {
					new Scribble((String)o.getParams()[0], o.getX(), o.getY(), (Integer)o.getParams()[1]);
					return;
				}

				GameActor actor;
				BodyDef.BodyType bodyType;

				switch (o.getType()) {
					case WALL:
						actor = new WallActor();
						bodyType = BodyDef.BodyType.StaticBody;
						break;
					case EXPLOSIVE_WALL:
						actor = new ExplosiveWallActor((Float)o.getParams()[0]);
						bodyType = BodyDef.BodyType.StaticBody;
						break;
					case BALL:
						actor = new BallActor();
						bodyType = BodyDef.BodyType.DynamicBody;
						break;
					case HOLE:
						actor = new HoleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case BLACK_HOLE:
						actor = new BlackHoleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case WORMHOLE:
						int j = (Integer)o.getParams()[0];
						actor = new WormholeActor();
						bodyType = BodyDef.BodyType.KinematicBody;

						if (wormholes[j] == null) {
							wormholes[j] = (WormholeActor) actor;
						} else {
							((WormholeActor) actor).setEndpoint(wormholes[j]);
							wormholes[j].setEndpoint((WormholeActor) actor);
						}

						break;
					case PUDDLE:
						actor = new PuddleActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case STAR:
						actor = new StarActor();
						bodyType = BodyDef.BodyType.KinematicBody;
						break;
					case DOOR:
						actor = new DoorActor();
                        ((DoorActor)actor).setOrientation((Boolean)o.getParams()[0]);
						bodyType = BodyDef.BodyType.StaticBody;
						break;
					default:
						return;
				}

				actor.setPosition(o.getX(), -o.getY());

				actor.applyWorld(world, bodyType);

				addActor(actor);

			}

	}

	private class Scribble extends Label {

		private float elapsedTime = 0f;
		private static final float MAGNIFY = 0.0005f;
		private static final float FONT_SCALE = 0.007f; //Ennyin volt jó...

		@Override
		public void act(float delta) {
			elapsedTime += delta;
			super.act(delta);
			setFontScale(FONT_SCALE + (float)Math.sin(elapsedTime * 5.0f) * MAGNIFY);
		}

		Scribble(String text, int x, int y, int width) {
			super(text, LABEL_STYLE);

			setFontScale(FONT_SCALE);
			setPosition(x, -y);
			setSize(width, 1);
			setAlignment(Align.center);
			setWrap(true);
			setVisible(true);
			setColor(.9f, .9f, .9f, 1);
			addActor(this);

		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		//Gdx.app.log("stage", "fling");
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//Gdx.app.log("stage", "pan");
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		//Gdx.app.log("stage", "panStop");
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		additionalZoom = Math.min(initialDistance / distance, 2.5f);
		// TODO
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		//Gdx.app.log("stage", "pinch");
		return false;
	}
}
