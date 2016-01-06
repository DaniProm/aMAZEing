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
		parameter.color = Color.valueOf("#8a8a8a");
		scribbleFont = generator.generateFont(parameter);
		generator.dispose();

	}


	protected static final Label.LabelStyle LABEL_STYLE;

	static	{
		LABEL_STYLE = new Label.LabelStyle();
		LABEL_STYLE.font = scribbleFont;
		//LABEL_STYLE.fontColor = Color.WHITE; //Nem működik
	}

	public World world = new World(Vector2.Zero, false);

	private final ArrayList<BallActor> balls = new ArrayList<BallActor>();
	private final ArrayList<HoleActor> holes = new ArrayList<HoleActor>();

	public byte totalStars, collectedStars;

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

	public GameStage(Viewport viewport, Batch batch, String maze) {
		super(viewport, batch);
		loadMaze(maze);
	}

	public GameStage(Viewport viewport, String maze) {
		super(viewport);
		loadMaze(maze);
	}

	public World getWorld() {
		return world;
	}

	static final float BALL_HORIZON = 320;

	public void updateCamera(final OrthographicCamera camera) {

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

			top += GameScreen.TILE_SIZE / 2 + BALL_HORIZON;
			bottom += GameScreen.TILE_SIZE / 2 - BALL_HORIZON;

			left += GameScreen.TILE_SIZE / 2 - BALL_HORIZON;
			right += GameScreen.TILE_SIZE / 2 + BALL_HORIZON;

		}

		height = Math.max(top - bottom, BALL_HORIZON);
		width = Math.max(right - left, BALL_HORIZON);

		camera.position.x = (right + left) / 2;
		camera.position.y = (top + bottom) / 2;

		camera.zoom = additionalZoom * Math.max(height / camera.viewportHeight, width / camera.viewportWidth);
	}

	private float additionalZoom = 0.01f;


	private float keyGravityX = 0, keyGravityY = 0;

	@Override
	public void act(float delta) {
		if (world == null) return;

		world.setGravity(new Vector2(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX()));

//Teszteléshez
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (keyGravityX < 10) keyGravityX += 1f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (keyGravityX > -10) keyGravityX -= 1f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (keyGravityY > -10) keyGravityY -= 1f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (keyGravityY < 10) keyGravityY += 1f;
		}
		if (keyGravityX != 0 || keyGravityY != 0) {
			//keyGravityX*=0.1f;
			//keyGravityY*=0.1f;
			world.setGravity(new Vector2(keyGravityX * 2, -keyGravityY * 2));
		}
//Teszteléshez
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
		}

	}

	boolean showScribbles = true;

	private void loadMaze(String maze) {

		try {
			BufferedReader reader = new BufferedReader(Gdx.files.internal("mazes/" + maze + ".txt").reader());
			//final float height = Gdx.graphics.getHeight(); - Nem szükséges, mert a pálya nem függ a képernyőtől. Inkább játszunk a kamerával...
			WormholeActor[] wormholes = new WormholeActor['F' - 'A' + 1];
			String line;

			line = reader.readLine(); // maze description

			final ArrayList<Label> scribbles = showScribbles ? new ArrayList<Label>() : null;

			for (int y = 0; null != (line = reader.readLine()); ) {
				if (line.compareTo("") == 0) break;

				y++;
				int x = 0;
				for (int i = 0; i < line.length(); i++) {
					GameActor actor = null;
					BodyDef.BodyType bodyType = null;
					final char ch = line.charAt(i);
					switch (ch) {
						case ' ':
							break; // space: do nothing
						case '<':
							x -= 2;
							break;
						case '.': // wall
							actor = new WallActor();
							bodyType = BodyDef.BodyType.StaticBody;
							break;
						case ':': // explosive wall
							actor = new ExplosiveWallActor(10.0f);
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
						case '%': // door
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
						case '*': // bonus star
							actor = new StarActor();
							bodyType = BodyDef.BodyType.KinematicBody;
							break;
						case '#': // scribble
							if (scribbles != null) {
								int nextCh = line.charAt(++i);


								Label label = new Label("Valami", LABEL_STYLE);
								label.setFontScale(0.007f); //Ennyin volt jó...
								label.setPosition(x * GameScreen.TILE_SIZE, -y * GameScreen.TILE_SIZE);
								label.setSize(GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
								label.setAlignment(Align.center);
								label.setWrap(true);
								label.setVisible(true);
								scribbles.add(label);
								addActor(label);

/*
								ScribbleActor scribble = new ScribbleActor(x * GameScreen.TILE_SIZE, height - (y - 1) * GameScreen.TILE_SIZE, nextCh - '0');
								scribbles.add(scribble);
								addActor(scribble);*/
								--x;
							}
							break;
						default:

							// wormhole
							int j;
							if (ch >= 'a' && ch <= 'f') j = ch - 'a';
							else if (ch >= 'A' && ch <= 'F') j = ch - 'A';
							else break;

							actor = new WormholeActor();
							bodyType = BodyDef.BodyType.KinematicBody;

							if (wormholes[j] == null) {
								wormholes[j] = (WormholeActor) actor;
							} else {
								((WormholeActor) actor).setEndpoint(wormholes[j]);
								wormholes[j].setEndpoint((WormholeActor) actor);
							}

							break;
					}

					if (actor != null) {

						//actor.setPosition(x * GameScreen.TILE_SIZE, height - y * GameScreen.TILE_SIZE);
						actor.setPosition(x * GameScreen.TILE_SIZE, -y * GameScreen.TILE_SIZE);

						actor.applyWorld(world, bodyType);

						addActor(actor);

					}

					++x;
				}

			}

			if (scribbles != null) {
				// read scribbles
				for (int i = 0; null != (line = reader.readLine()); i++) {
					scribbles.get(i).setText(line);
				}

			}

			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
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
		additionalZoom = initialDistance / distance;
		// TODO
		return false;
	}
/*

	public class ScribbleActor extends Actor {
		private final float x, y;
		private final int span;
		private String text;
		public ScribbleActor(float x, float y, int span) {
			this.x = x;
			this.y = y;
			this.span = span;
		}

		public void setText(String text) {
			this.text = text;
		}

		@Override
		public void draw(Batch batch, float parentAlpha) {

			//scribbleFont.draw(batch, text, x, y, span * GameScreen.TILE_SIZE, Align.center, true); // TODO align vertically center
		}
	}
*/
	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		//Gdx.app.log("stage", "pinch");
		return false;
	}
}
