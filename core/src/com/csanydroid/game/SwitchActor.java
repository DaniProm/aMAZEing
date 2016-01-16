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

	private boolean orientation;


	public boolean isFlipped() {
		return flipped;
	}

	private boolean isPushButton = false;

	@Override
	protected Shape getShape() {
		return super.getShape();
	}

	private boolean flipped;

	public boolean isHorizontal() {
		return orientation;
	}

	public void setFlipped(boolean flipped) {
		this.flipped = flipped;
		sprite.setFlip(this.orientation && flipped, !(this.orientation && flipped));
	}

	public void setOrientation(boolean horizontal) {
		this.orientation = horizontal;
		sprite.setRotation(horizontal ? 0 : 90);

	}

	private Array<TextureAtlas.AtlasRegion> textureAtlasRegions = Assets.manager.get(Assets.SWITCH_ATLAS).getRegions();

	@Override
	public void setState(boolean state) {
		if(state) {
			gate.open();
			sprite.setRegion(textureAtlasRegions.get(0));
		} else {
			gate.tryClose();
			sprite.setRegion(textureAtlasRegions.get(1));
		}
	}

}
