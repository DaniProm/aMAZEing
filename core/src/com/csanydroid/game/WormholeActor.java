package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;

import java.util.ArrayDeque;

// import com.badlogic.gdx.graphics.g2d.Animation;
// import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WormholeActor extends GameActor {

	protected static Texture texture = new Texture("wormhole.png");

	private final Sound sound = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));
	private float active = 0;
	private WormholeActor endpoint = null;

	// private boolean itWillTeleport = false;
	/*
		@Override
		public void draw(Batch batch, float parentAlpha) {
			super.draw(batch, parentAlpha);

			if(itWillTeleport){
				textureAtlasTeleport = new TextureAtlas("teleportAtlas.atlas");
				animationTeleport = new Animation(1 / 30f, textureAtlasTeleport.getRegions());
				sprite = new Sprite();
				sprite.setRegion(textureAtlasTeleport.getRegions().get(tpFrame));
				sprite.draw(batch);


			}
		}*/
	private ArrayDeque<BallActor> balls = new ArrayDeque<BallActor>();

	// private Animation animationTeleport;

	// protected static TextureAtlas textureAtlasTeleport;

	public WormholeActor() {
		sprite = new Sprite(texture);
		setSize(1, 1);
	}

	@Override
	protected Shape getShape() {
		return getCircleShape(.75f);
	}

	public void setEndpoint(WormholeActor endpoint) {this.endpoint = endpoint;}

	public void transportBall(BallActor ball) {
		if (active >= 0) return; // not active
		balls.add(ball);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

	}

	@Override
	public void act(float delta) {
		super.act(delta);

		active -= delta;

		if (balls.size() > 0) {
			// H: Emlékeztessetek, hogy ezzel kapcsolatban meg kell beszélni valamit!
			final BallActor ball = balls.getFirst();
			balls.clear();

			Vector2 newPos = endpoint.body.getPosition();
			ball.body.setTransform(newPos.x /* + 100 / PIX2M */, newPos.y, 0);

			// play transport sound
			sound.play();

			active = 1;
			endpoint.active = 1;
		}

	}

	@Override
	public void dispose() {
		sound.dispose();
	}
}
