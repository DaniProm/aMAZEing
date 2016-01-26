package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class DoorActor extends GateActor {

	private float timeSinceOpened;



    {
		setTouchable(Touchable.enabled);
		addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                open(); Assets.manager.get(Assets.DOOR_OPEN_SOUND).play();
            }
        });
	}

	@Override
	public void act(float delta) {
		if(state == State.OPENED) {
			timeSinceOpened += delta;
			if(timeSinceOpened > 3) tryClose();Assets.manager.get(Assets.DOOR_CLOSE_SOUND).play();
		}
		super.act(delta);

	}

	@Override
	public void open() {
		super.open();
		timeSinceOpened = 0;
	}
}
