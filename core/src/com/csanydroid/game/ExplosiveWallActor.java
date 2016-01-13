package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class ExplosiveWallActor extends WallActor {

	// H: szerintem valami másik kép kellene, mert eléggé zavaró mozgás közben
	protected static Texture texture = new Texture("expwall1.png");
	protected static Array<TextureAtlas.AtlasRegion> textureAtlasRegions = new TextureAtlas("boom1.atlas").getRegions();

	protected final float strength;

	private Animation explosionAnimation = null;
	private float stateTime = 0;

	public ExplosiveWallActor(float strength) {
		sprite = new Sprite(texture);
		sprite.setSize(1, 1);

		this.strength = strength;
	}

	public float getStrength() {
		return strength;
	}

	public void explode() {
		explosionAnimation = new Animation(1 / 10f, textureAtlasRegions);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (explosionAnimation != null) {
			deactivate();
			stateTime += delta;
			if (!explosionAnimation.isAnimationFinished(stateTime)) {
				sprite.setRegion(explosionAnimation.getKeyFrame(stateTime));
			} else delete();
		}
	}

}
