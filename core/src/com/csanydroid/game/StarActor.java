package com.csanydroid.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class StarActor extends GameActor {
	private final Music music = Assets.manager.get(Assets.STAR_MUSIC);

	protected static Animation animation;
	private float stateTime = 0;
	private boolean hasCollected = false;

	public StarActor() {
		final Array<TextureAtlas.AtlasRegion> textureAtlasRegions = Assets.manager.get(Assets.STAR_ATLAS).getRegions();
		sprite = new Sprite(textureAtlasRegions.first());
		sprite.setRegion(textureAtlasRegions.first());
		animation = new Animation(1 / 30f, textureAtlasRegions, Animation.PlayMode.LOOP);
		setSize(0.5f, 0.5f);
		setTouchable(Touchable.disabled);
		music.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				delete();
			}
		});
	}

    @Override
    public void applyWorld(World world, BodyDef.BodyType bodyType) {
        super.applyWorld(world, bodyType);
        setSensor(true);
    }

    @Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		sprite.setPosition(x + 0.25f, y + 0.25f);
	}

	public void collect() {
		if(hasCollected) return;
		setZIndex(Integer.MAX_VALUE);
		hasCollected = true;
		((GameStage)getStage()).collectStar();
		music.play();
	}

	@Override
	public void act(final float delta) {

		stateTime += delta;

		sprite.setRegion(animation.getKeyFrame(stateTime));

		if(!hasCollected) super.act(delta);
		else {
			deactivate();
			float width=getWidth();
			setSize(getWidth() * 1.02f, getHeight() * 1.02f);
			setPosition(getX()-(getWidth()-width)/2, getY()-(getWidth()-width)/2);
			sprite.setAlpha(sprite.getColor().a*0.97f);
			//sprite.setColor(0,0,0,sprite.getColor().a*0.95f);

			//setVisible(false);
		}
	}

	@Override
	protected Shape getShape() {
		return getCircleShape(.5f);
	}
}
