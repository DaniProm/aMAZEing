package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PushButtonActor extends ButtonActor {

	public PushButtonActor() {
		sprite = new Sprite();
		setSize(1, 1);
	}

	@Override
	protected void setDefault() {
		setState(false);
	}

	@Override
	public void setState(boolean state) {

		if(this.defaultState != state) {
			gate.open();
			sprite.setRegion(Assets.manager.get(Assets.PUSH_BUTTON));
		}
		else {
			gate.tryClose();
			sprite.setRegion(Assets.manager.get(Assets.PUSH_BUTTON2));
		}

	}
}
