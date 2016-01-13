package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class DoorActor extends GameActor {

	//protected static Texture texture = new Texture("ajto.png");
	protected static Array<TextureAtlas.AtlasRegion> textureAtlasRegions = new TextureAtlas("AjtoAtlas.atlas").getRegions();
	//TextureAtlas textureAtlasTeleport = new TextureAtlas("ajtoAtlas.atlas");
	//Animation animationTeleport = new Animation(1 / 30f, textureAtlasTeleport.getRegions());
	private float timeOpen = -1;
	private int animationFrame=0;

	public DoorActor() {
		sprite = new Sprite(textureAtlasRegions.first());
		setSize(1, 1);
	}

    {

		setTouchable(Touchable.enabled);
		addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                open();
            }
        });

        /*
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
*/
	}

	public void setOrientation(boolean horizontal)
	{
		if (horizontal) {
			sprite.setRotation(0);
		} else {
			sprite.setRotation(90);
		}
	}


	@Override
	public void act(float delta) {
		super.act(delta);

		if (timeOpen >= 0)
		{
            // nyitódik
			if (animationFrame < textureAtlasRegions.size - 1) {
				animationFrame++;
				sprite.setRegion(textureAtlasRegions.get(animationFrame));
			}

			timeOpen += delta;
		}

		if (timeOpen > 3) {
            if(!isTouchBall()) {
                timeOpen = -1;
               setSensor(false);
            }
		} else if (timeOpen < 0) {
            // csukódik
			if (animationFrame > 0)
			{
				animationFrame--;
				sprite.setRegion(textureAtlasRegions.get(animationFrame));
			}
		}

	}

	public void open() {
        setSensor(true);

		timeOpen = 0;
/*
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {

                setVisible(true);
            }
        }, 3);
*/
    }

}
