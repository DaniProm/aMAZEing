package com.csanydroid.game;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class ButtonActor extends GameActor {

	public void setDefaultState(boolean defaultState) {
		this.defaultState = defaultState;
	}

	protected boolean defaultState;

	@Override
	public void applyWorld(World world, BodyDef.BodyType bodyType) {
		super.applyWorld(world, bodyType);

		if(gate != null) setDefault();
		setSensor(true);
	}

	protected GateActor gate;

	public void setGate(GateActor gate) {
		this.gate = gate;

		setDefault();
	}

	protected void setDefault() {
		this.setState(this.defaultState);
	}

	public void setState(boolean state) {

        Assets.manager.get(Assets.SWITCHING_SOUND).play();

    }

	@Override
	protected Shape getShape() {
		return getCircleShape(.8f);
	}
}
