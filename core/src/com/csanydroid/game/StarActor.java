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
	private Batch batch;

	private final Music music = Gdx.audio.newMusic(Gdx.files.internal("teleport.mp3"));


	protected static Animation animationStar;
	private float stFrame = 0;
	protected static Array<TextureAtlas.AtlasRegion> textureAtlas = new TextureAtlas("StarAtlas.atlas").getRegions();

	public StarActor() {
		//sprite = new Sprite(texture);
		sprite = new Sprite(textureAtlas.get(0));
		sprite.setRegion(textureAtlas.get(0));
		animationStar = new Animation(1 / 30f, textureAtlas, Animation.PlayMode.LOOP);
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
		sprite.setPosition(x+0.25f,y+0.25f);
	}


	public void collect() {
		if(hasCollected) return;
		setZIndex(Integer.MAX_VALUE);
		hasCollected = true;
		++((GameStage)getStage()).collectedStars;
		music.play();
	}

	private boolean hasCollected = false;

	@Override
	public void act(final float delta) {
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
	public void draw(Batch batch, float parentAlpha) {
		stFrame+=Gdx.graphics.getDeltaTime();
		sprite.setRegion(animationStar.getKeyFrame(stFrame));
		super.draw(batch, parentAlpha);
		//if(doNothing) starRotating();
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
