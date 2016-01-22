package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class SwitchActor extends ButtonActor {

	public SwitchActor() {
		sprite = new Sprite();
		setSize(1, 1);
	}

	private boolean orientation, isReversed;


	public boolean isReversed() {
		return this.isReversed;
	}

	private boolean isPushButton = false;

	@Override
	protected Shape getShape() {
		return super.getShape();
	}

	public boolean isHorizontal() {
		return orientation;
	}

	public void setIsReversed(boolean reversed) {
		this.isReversed = reversed;
	}

	public void setOrientation(boolean horizontal) {
		this.orientation = horizontal;
		sprite.setRotation(horizontal ? 0 : 90);

	}

	private final TextureAtlas textureAtlasRegions = Assets.manager.get(Assets.SWITCH_ATLAS);

	@Override
	public void setState(boolean state) {
        super.setState(state);

		if(state) {
			gate.open();
		} else {
			gate.tryClose();
		}

		sprite.setRegion(textureAtlasRegions.findRegion(state ? "opened" : "closed"));

		sprite.setFlip(this.isReversed, false);

		//sprite.setFlip(!this.orientation && this.isReversed, this.orientation && this.isReversed);
	}

}
