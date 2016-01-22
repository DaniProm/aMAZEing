package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class ExplosiveWallActor extends WallActor {

	protected final float strength;

	private Animation explosionAnimation = null;
	private float stateTime = 0;

	public ExplosiveWallActor(float strength) {
		sprite = new Sprite(Assets.manager.get(Assets.EXPLOSIVE_WALL));
		sprite.setSize(1, 1);

		this.strength = strength;
	}

	public void ballCollide(float externStrength) {
		if(externStrength < this.strength) return;
        Assets.manager.get(Assets.WALLEXPLOSION_SOUND).play();
		explosionAnimation = new Animation(1 / 10f, Assets.manager.get(Assets.EXPLOSION_ATLAS).getRegions());
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
