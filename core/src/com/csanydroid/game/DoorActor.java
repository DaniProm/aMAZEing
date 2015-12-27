package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;

public class DoorActor extends GameActor {

	protected static Texture texture = new Texture("door.png");
	private static Timer timer = new Timer();

	public DoorActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
	}

	{

		setTouchable(Touchable.enabled);
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setVisible(false);

				timer.scheduleTask(new Timer.Task() {
					@Override
					public void run() {
						setVisible(true);
					}
				}, 3);
			}
		});

		addListener(new ActorGestureListener() {
			@Override
			public boolean longPress(Actor actor, float x, float y) {
				delete();
				return true;
			}

			@Override
			public void fling(InputEvent event, float velocityX, float velocityY, int button) { }

			@Override
			public void zoom(InputEvent event, float initialDistance, float distance) { }
		});

	}

}
