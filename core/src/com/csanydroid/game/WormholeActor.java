package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayDeque;

// import com.badlogic.gdx.graphics.g2d.Animation;
// import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class WormholeActor extends GameActor {


	protected static Array<TextureAtlas.AtlasRegion> textureAtlasRegions = new TextureAtlas("teleport.atlas").getRegions();
	protected static Animation animation;
	private float stateTime = 0;

	private final Sound sound = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));
	private float active = 0;
	private WormholeActor endpoint = null;

	private float elapsedTime = 0f;
	private static final float MAGNIFY = 1f;
	private static final float FONT_SCALE = 0.007f;

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
		sprite = new Sprite(textureAtlasRegions.first());
		sprite.setRegion(textureAtlasRegions.first());
		animation = new Animation(1 / 30f, textureAtlasRegions, Animation.PlayMode.LOOP);
		setSize(0.5f, 0.5f);
		/*music.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				delete();
			}
		});*/
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

		stateTime += delta;
		active -= delta;
		elapsedTime += delta;

		float size = (0.15f * (float) (Math.sin(elapsedTime * 5.0f)))+0.85f;
		sprite.setRegion(animation.getKeyFrame(stateTime * 0.2f));
		sprite.setSize(size, size);
		Vector2 v = body.getPosition();
		sprite.setPosition(v.x + (1 - size) / 2, v.y + (1 - size) / 2);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.rotate(1);
		if (balls.size() > 0) {
			// H: Emlékeztessetek, hogy ezzel kapcsolatban meg kell beszélni valamit!
			final BallActor ball = balls.getFirst();
			balls.clear();

			Vector2 newPos = endpoint.body.getPosition();
			//ball.body.setTransform(newPos.x /* + 100 / PIX2M */, newPos.y, 0);
			ball.Teleport(newPos.x, newPos.y, this, endpoint);

			// play transport sound
			//sound.play();
/*
			active = 1;
			endpoint.active = 1;*/
		}

	}

	public float getActive() {
		return active;
	}

	public void setActive(float active) {
		this.active = active;
	}
	/*private class Scribble extends Label {

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

			addActor(this);

		}
	}*/

	@Override
	public void dispose() {
		sound.dispose();
	}
}
