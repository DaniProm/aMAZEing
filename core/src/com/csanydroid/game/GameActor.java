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
	/**
	 * Ha megnézzük, hogy hol van használva ez a változó, akkor kiderül, hogyha ez egy, akkor nincs is rá szükségünk.
	 * Valószínűleg nem lett tesztelve a változtatás, miután át lett írva ez a számot egyre. Ekkor azt láthattuk, hogy a laszti alig mozdul.
	 * A változóra a fizikai szimulátor működéséből kifolyólag van szükségünk.
	 * Célja, hogy skálázza az általunk megjelenített pixel koordinátáját, a szimulátor által (belsőleg) használt egységéhez (ami méter; életszerűség).
	 * Ha ez a változó 1, az azt jelenti, hogy nincs skálázás -> magyarán, ha a képernyőn a csiga 1 pixelt tesz meg, akkor az a valóságban (a fizikai modell szerint) 1 métert jelent. (ugye ez 1 másodperc alatt (60 képkocka/mp-nél) 1s * 60 egység (méter), ami 216km/h-t takar.)
	 * Az utóbb kiszámolt sebesség a mi világunkban imsert maximális sebességtől igencsak messze esik. Akkor miért nem tud gyorsabban mozogni a képernyőn a tárgy?
	 * A probléma, akkor válik láthatóvá, ha elérjük a box2d belső "fénysebességét", ami 2 egységben (méterben) van meghatározva.
	 * Ez a tárgy maximális elmozdulását jelenti 1 darab world.step-nél. Ami 60-szor meghívva egy másodperc alatt: 2 egység * 60 darab = 120 egység elmozdulás = 120m -> 120m-t tesz meg másodpercenként -> 120m/s -> 432km/h -> ez viszonylag, azért elég sok, ahhoz képest, hogy a képernyőn csak 120 pixelt tett meg.
	 * Bővebben itt: http://www.iforce2d.net/b2dtut/gotchas#speedlimit
	 * És mivel, ha ez a szám nem 1, akkor a debuggolásos rendeleléses cucc el lesz csúszva, ezért is kommenteltem ki azokat a sorokat. De biztos van rá valami megoldás...
	 */

	protected static float PIX2M = 1; // különböző átváltásokkal meg lehetne kapni a megfelelő számot TODO majd egy pontos érték kiszámítása
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

	protected Shape getShape() {
		return getTileShape();
	}

	protected static Shape getTileShape() {
		final PolygonShape shape = new PolygonShape();
		final float hs =  GameScreen.TILE_SIZE / 2 / PIX2M;
		shape.setAsBox(hs, hs, new Vector2(hs, hs), 0);
		return shape;

	}

	protected static Shape getCircleShape(float scale) {
		scale /= 2; // diameter -> radius

		final CircleShape shape = new CircleShape();
		shape.setRadius(GameScreen.TILE_SIZE * scale / PIX2M);
		shape.setPosition(new Vector2(GameScreen.TILE_SIZE * scale / PIX2M, GameScreen.TILE_SIZE * scale / PIX2M));
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
		fixtureDef.restitution = 0;
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
		body.setLinearDamping(0);

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

	@Override
	public void dispose() { }

	{
		setBounds(0,0,GameScreen.TILE_SIZE,GameScreen.TILE_SIZE);
	}
}
