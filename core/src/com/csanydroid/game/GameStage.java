package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
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

import java.util.ArrayList;

public class GameStage extends Stage implements GestureDetector.GestureListener {
	protected static final Label.LabelStyle LABEL_STYLE;
	static final float BALL_HORIZON = 2.5f; // a golyónál hányszor nagyobb teret lásson
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

	static {
		LABEL_STYLE = new Label.LabelStyle();
		LABEL_STYLE.font = scribbleFont;
	}

	private final ArrayList<BallActor> balls = new ArrayList<BallActor>();
	private final ArrayList<HoleActor> holes = new ArrayList<HoleActor>();
	private final Maze maze;
	public World world = new World(Vector2.Zero, false);
	boolean controlWithMouse = false;
	private byte collectedStars;

	private float additionalZoom = 1;
	private float additionalPositionX = 0;
	private float additionalPositionY = 0;
	private float additionalPosLockTime = -100;
	private float elapsedTime = 0;

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
                                             Assets.manager.get(Assets.PUDDE_MUSIC).play();
					                         ball.body.setLinearDamping(4f);
				                         } else if (other instanceof BlackHoleActor) {
					                         ((BlackHoleActor) other).swallowBall(ball);
				                         } else if (other instanceof WormholeActor) {
					                         ((WormholeActor) other).transportBall(ball);
				                         } else if (other instanceof ExplosiveWallActor) {
					                         ((ExplosiveWallActor) other).ballCollide(ball.body.getLinearVelocity().len());
				                         } else if (other instanceof HoleActor) {
					                         ((HoleActor) other).swallowBall(ball);
				                         } else if (other instanceof StarActor) {
					                         ((StarActor) other).collect();
				                         } else if (other instanceof SwitchActor) {
                                             ball.body.setLinearDamping(1f);
					                         int side = angleToSide(other.body.getPosition(), ball.body.getPosition());
					                         if(((SwitchActor) other).isHorizontal()) {
						                         if(side == 0 || side == 2)
							                         ((SwitchActor) other).setState((side == 0) == ((SwitchActor) other).isReversed());
					                         } else {
						                         if(side == 1 || side == 3)
							                         ((SwitchActor) other).setState((side == 1) == ((SwitchActor) other).isReversed());
					                         }
				                         } else if (other instanceof PushButtonActor) {
					                         ((PushButtonActor) other).setState(true);
				                         } else if(other instanceof WallActor) {
                                             Assets.manager.get(Assets.BALLCWWALL_SOUND).play();
                                         } else if (other instanceof BallActor) {
                                             Assets.manager.get(Assets.BALLCWBALL_SOUND).play();
                                         }

			                         }



			                         public int angleToSide(Vector2 posA, Vector2 posB) {
				                         float angle = (float)Math.atan2(posB.y - posA.y, posB.x - posA.x);
				                         if (angle < 0) angle += MathUtils.PI2;
				                         return  (((int)(angle / (MathUtils.PI / 4)) + 1) / 2) % 4;
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
                                             Assets.manager.get(Assets.PUDDE_MUSIC).pause();
				                         } else if (other instanceof SwitchActor) {
                                             ball.body.setLinearDamping(0);
					                         int side = angleToSide(other.body.getPosition(), ball.body.getPosition());
					                         if(((SwitchActor) other).isHorizontal()) {
						                         if(side == 0 || side == 2)
							                         ((SwitchActor) other).setState((side == 0) == ((SwitchActor) other).isReversed());
					                         } else {
						                         if(side == 1 || side == 3)
							                         ((SwitchActor) other).setState((side == 1) == ((SwitchActor) other).isReversed());
					                         }
				                         } else if (other instanceof PushButtonActor) {
					                         ((PushButtonActor) other).setState(false);
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

		addActor(new BackgroundActor(maze));

        changeState(GameState.PLAYING);
	}


	@Override
	public boolean keyDown(int keyCode) {
		switch (keyCode) {
			case Input.Keys.BACK:
			case Input.Keys.ESCAPE:
                pause();
				break;
		}

		return true;
	}

	public Maze getMaze() {
		return maze;
	}

	public void collectStar() {
		++collectedStars;
	}

	public byte getCollectedStars() {
		return collectedStars;
	}


	public void pause() {
        if(state == GameState.PLAYING) {
            changeState(GameState.PAUSED);
        }
	}

	public void resume() {

        if (state == GameState.PAUSED) {
            changeState(GameState.PLAYING);
        }
    }

    enum GameState {
        PLAYING, PAUSED, WON, LOST, WINNER
    }

    public GameState getState() {
        return state;
    }

    private GameState state;

    private void changeState(GameState state) {
        if(this.state == state) return;
        this.state = state;

        if(this.state == GameState.PLAYING) {

            GestureDetector gd = new GestureDetector(20, 0.5f, 2, 0.15f, this);
            InputMultiplexer im = new InputMultiplexer(gd, this);
            Gdx.input.setInputProcessor(im);

        }

        if(eventListener != null) eventListener.onStateChange();

    }

    private void gameFinished(boolean hasWon) {

		try {
            changeState(hasWon ? GameState.WON : GameState.LOST);

			if (hasWon) {
				maze.unlockNext();
			}


		} catch (Exception e) {
            try {
                changeState(GameState.WINNER);
            } catch (Exception ignored) { }

		}

	}

    public void setEventListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    EventListener eventListener;

    interface EventListener {
        void onStateChange();
        void onBallRemove();
    }

	public void removeBall(BallActor ball) {
		//Gdx.app.log("Vibra","1");
        eventListener.onBallRemove();

		balls.remove(ball);

		if (balls.size() == 0) {

			if (countEmptyHoles() == 0) {
				Gdx.app.log("játék", "Nyertem! :)");
				gameFinished(true);
			} else {
				Gdx.app.log("játék", "Vesztettem!");
				gameFinished(false);
			}

			//Gdx.app.log("játék", "Sikerült " + collectedStars + " csillagot összegyűjtenem a " + totalStars + "-ra/-hoz/-ig/-ből/-ba/-tól.");
		} else if (balls.size() < maze.getRemainingBalls() || balls.size() < countEmptyHoles()) {
			gameFinished(false);
		} else {

			Gdx.app.log("játék", "Ajjaj! Kipukkadt egy lasztim...");
		}
	}

	private int countEmptyHoles() {
		int count = 0;
		for (HoleActor hole : holes) {
			if (!hole.hasSwallowedBall()) ++count;
		}
		return count;
	}

	public World getWorld() {
		return world;
	}

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
//Gdx.app.log("stage", "Elapsed: " + elapsedTime + "  add" + additionalPosLockTime);
			if (elapsedTime>additionalPosLockTime+2f) {
				newValue = (right + left) / 2;
				camera.position.x += (newValue - camera.position.x) / 10;
				newValue = (top + bottom) / 2;
				camera.position.y += (newValue - camera.position.y) / 10;
				if (additionalZoom>1.1f)
				{
					additionalZoom*=0.95;
				}
				else if (additionalZoom<0.9f)
				{
					additionalZoom*=1.05;
				}
			}
			else
			{
				if (elapsedTime<additionalPosLockTime+0.1f) {
					camera.position.x += additionalPositionX;
					camera.position.y += additionalPositionY;
				}
			}
			newValue = additionalZoom * Math.max(height / camera.viewportHeight, width / camera.viewportWidth);
			camera.zoom += (newValue - camera.zoom) / 30f;

		} else {
			lookAtMaze(camera);
		}

	}

	@Override
	public void act(float delta) {

		elapsedTime += delta;
		if (state == GameState.PLAYING) {

			switch (Gdx.app.getType()) {
				case Android:
					world.setGravity(new Vector2(Gdx.input.getAccelerometerY(), -Gdx.input.getAccelerometerX()));
					break;
				case Desktop:

					if (Gdx.input.isKeyJustPressed(Input.Keys.M)) {
						controlWithMouse = !controlWithMouse;
						Gdx.app.log("control", "vezérlés " + (controlWithMouse ? "egérrel" : "billentyűvel"));
					}

					if (Gdx.input.isKeyJustPressed(Input.Keys.F10)) {
						maze.lockAll();
						Gdx.app.log("control", "Az összes pálya lezárva ");
					}

					if (Gdx.input.isKeyJustPressed(Input.Keys.F12)) {
						maze.unlockAll();
						Gdx.app.log("control", "Az összes pálya feloldva");
					}

					if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
						changeState(GameState.WON);
						Gdx.app.log("control", "CHEAT...A pálya kész");
					}

					if (!controlWithMouse) {
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


		}
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
		}

	}

	private void loadMaze(Maze maze) {

		WormholeActor[] wormholes = new WormholeActor[Maze.MAX_WORMHOLES];
		GameActor[] gatesAndButtons = new GameActor[Maze.MAX_SWITCHES * 2];

		for (Maze.MazeObject o : maze.getObjects()) {

			if (o.getType() == Maze.ObjectType.SCRIBBLE) {
				new Scribble((String) o.getParams()[0], o.getX(), o.getY(), (Integer) o.getParams()[1]);
				continue;
			}

			GameActor actor;
			BodyDef.BodyType bodyType;

			switch (o.getType()) {
				case WALL:
					actor = new WallActor();
					bodyType = BodyDef.BodyType.StaticBody;
					break;
				case EXPLOSIVE_WALL:
					actor = new ExplosiveWallActor((Float) o.getParams()[0]);
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
				case WORMHOLE: {
					actor = new WormholeActor();
					bodyType = BodyDef.BodyType.KinematicBody;

					final int j = (Integer) o.getParams()[0];
					if (wormholes[j] == null) {
						wormholes[j] = (WormholeActor) actor;
					} else {
						((WormholeActor) actor).setEndpoint(wormholes[j]);
						wormholes[j].setEndpoint((WormholeActor) actor);
					}
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
					((DoorActor) actor).setOrientation(!(Boolean) o.getParam(Maze.DOOR_ORIENTATION));
					bodyType = BodyDef.BodyType.StaticBody;
					break;
				case GATE: {
					actor = new GateActor();
					((GateActor) actor).setOrientation(!(Boolean) o.getParam(Maze.GATE_ORIENTATION));
					bodyType = BodyDef.BodyType.StaticBody;

					final int j = (Integer) o.getParam(Maze.GATE_INDEX);
					if (gatesAndButtons[j] == null) {
						gatesAndButtons[j] = actor;
					} else {
						((ButtonActor) gatesAndButtons[j]).setGate((GateActor) actor);
					}

				}
				break;

				case PUSH_BUTTON: {
					actor = new PushButtonActor();

					((PushButtonActor) actor).setDefaultState((Boolean) o.getParam(Maze.PUSHBUTTON_DEFAULTSTATE));
					bodyType = BodyDef.BodyType.KinematicBody;

					final int j = (Integer) o.getParams()[Maze.PUSHBUTTON_INDEX];
					if (gatesAndButtons[j] == null) {
						gatesAndButtons[j] = actor;
					} else {
						((PushButtonActor) actor).setGate((GateActor) gatesAndButtons[j]);
					}
				}
				break;
				case SWITCH: {

					actor = new SwitchActor();

					((SwitchActor) actor).setDefaultState((Boolean) o.getParam(Maze.SWITCH_DEFAULTSTATE));
					((SwitchActor) actor).setOrientation((Boolean) o.getParam(Maze.SWITCH_ORIENTATION));

					((SwitchActor) actor).setIsReversed((Boolean) o.getParam(Maze.SWITCH_REVERSED));
					bodyType = BodyDef.BodyType.KinematicBody;

					final int j = (Integer) o.getParam(Maze.SWITCH_INDEX);
					if (gatesAndButtons[j] == null) {
						gatesAndButtons[j] = actor;
					} else {
						((SwitchActor) actor).setGate((GateActor) gatesAndButtons[j]);
					}
				}
				break;
				default:
					return;
			}

			actor.setPosition(o.getX(), -o.getY());

			actor.applyWorld(world, bodyType);

			addActor(actor);


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
		//Gdx.app.log("stage", "fling : " + velocityX + " : " + velocityY + " button: "  +button);
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//Gdx.app.log("stage", "fling : " + x + " : " + y + " deltaX: " + deltaX + " deltaY: "  +deltaY);
		additionalPositionX = -deltaX *((OrthographicCamera)getCamera()).zoom;
		additionalPositionY = deltaY *((OrthographicCamera)getCamera()).zoom;
		if (additionalPositionX !=0 || additionalPositionY!=0) {
			additionalPosLockTime = elapsedTime;
		}
		//Gdx.app.log("stage", "pan");
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		additionalPositionX = 0;
		additionalPositionY = 0;
		zoomPrevDistance = -1;
		return super.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		//Gdx.app.log("stage", "panStop");
		return false;
	}

	private float zoomPrevDistance = -1;
	@Override
	public boolean zoom(float initialDistance, float distance) {
		//additionalZoom = Math.max(Math.min(initialDistance / distance, 2f), 1 / 2f);

		//Gdx.app.log("stage", "Init : " + initialDistance + "  Dist " + distance);
		if (zoomPrevDistance >0) {
			additionalZoom += (zoomPrevDistance - distance) * ((OrthographicCamera) getCamera()).zoom;
			if (additionalZoom < 2f/Math.max(maze.getWidth(), maze.getHeight()))
			{
				additionalZoom = 2f/Math.min(maze.getWidth(), maze.getHeight());
			}
			if (additionalZoom>3)
			{
				additionalZoom = 3;
			}
		}
		// TODO
		//Gdx.app.log("stage", "Addzoom : " + additionalZoom);
		zoomPrevDistance = distance;
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		//Gdx.app.log("stage", "pinch");
		return false;
	}

	private class Scribble extends Label {

		private static final float MAGNIFY = 0.0005f;
		private static final float FONT_SCALE = 0.007f; //Ennyin volt jó...
		private float elapsedTime = 0f;

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

		@Override
		public void act(float delta) {
			elapsedTime += delta;
			super.act(delta);
			setFontScale(FONT_SCALE + (float) Math.sin(elapsedTime * 5.0f) * MAGNIFY);
		}
	}
}
