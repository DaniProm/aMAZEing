package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

public class WormholeActor extends GameActor {

	private WormholeActor endpoint = null;
	public void setEndpoint(WormholeActor endpoint) {this.endpoint = endpoint;}

	public float active = 0;

	private int tpFrame = 0;

	private boolean itWillTeleport = false;

	protected static Texture texture = new Texture("wormhole.png");

	private Animation animationTeleport;

	protected static TextureAtlas textureAtlasTeleport;

	final Sound teleportSound = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));

	public WormholeActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
		if(BallActor.ballX == sprite.getX() && BallActor.ballY == sprite.getY()) {
			itWillTeleport = true;
		}
	}

	public WormholeActor getEndpoint() {return endpoint;}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		if(itWillTeleport){
			textureAtlasTeleport = new TextureAtlas("teleportAtlas.atlas");
			animationTeleport = new Animation(1 / 30f, textureAtlasTeleport.getRegions());
			sprite = new Sprite();
			sprite.setRegion(textureAtlasTeleport.getRegions().get(tpFrame));
			sprite.draw(batch);
			teleportSound.play(parentAlpha);
			if (textureAtlasTeleport.getRegions().size-1 > tpFrame) {
				tpFrame++;
			}
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		active -= delta;
	}

	@Override @SuppressWarnings("unused")public void dispose() {}
}
