package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class StarActor extends GameActor {
	private final Music music = Gdx.audio.newMusic(Gdx.files.internal("teleport.mp3"));

	protected static Animation animation;
	private float stateTime = 0;
	private boolean hasCollected = false;
	protected static Array<TextureAtlas.AtlasRegion> textureAtlasRegions = new TextureAtlas("star.atlas").getRegions();

	public StarActor() {
		sprite = new Sprite(textureAtlasRegions.first());
		sprite.setRegion(textureAtlasRegions.first());
		animation = new Animation(1 / 30f, textureAtlasRegions, Animation.PlayMode.LOOP);
		setSize(0.5f, 0.5f);
		music.setOnCompletionListener(new Music.OnCompletionListener() {
			@Override
			public void onCompletion(Music music) {
				delete();
			}
		});
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		sprite.setPosition(x + 0.25f, y+0.25f);
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
	public void dispose() {
		music.dispose();
	}

	@Override
	protected Shape getShape() {
		return getCircleShape(.5f);
	}
}
