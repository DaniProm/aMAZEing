package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

public class StarActor extends GameActor {
	private Batch batch;

	private final Music music = Gdx.audio.newMusic(Gdx.files.internal("teleport.mp3"));

	static boolean doNothing = false;

	//protected static Texture textureStar = null, texture=new Texture("star.png");
	//protected static TextureAtlas textureAtlasStar;
	protected static Animation animationStar;

	private float stFrame = 0;

	protected static Array<TextureAtlas.AtlasRegion> textureAtlasStar = new TextureAtlas("StarAtlas.atlas").getRegions();

	public StarActor() {
		//sprite = new Sprite(texture);
		sprite = new Sprite(textureAtlasStar.get(0));
		sprite.setRegion(textureAtlasStar.get(0));
		animationStar = new Animation(1 / 15f, textureAtlasStar, Animation.PlayMode.LOOP);
		setSize(0.5f, 0.5f);
	}

	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		sprite.setPosition(x+0.25f,y+0.25f);
	}

	/*
	public void starRotating(){
		textureStar = new Texture("starAtlas.png");
		sprite = new Sprite(textureStar);
		sprite.setSize(1, 1);
		textureAtlasStar = new TextureAtlas("StarAtlas.png");


		stFrame++;
		sprite.draw(batch);
	}*/

	public void collect() {
		if(hasCollected) return;
		else doNothing = true;
		hasCollected = true;
		++((GameStage)getStage()).collectedStars;
	}

	private boolean hasCollected = false;

	@Override
	public void act(final float delta) {
		if(!hasCollected) super.act(delta);
		else {
			setVisible(false);
			music.play();
			music.setOnCompletionListener(new Music.OnCompletionListener() {
				@Override
				public void onCompletion(Music music) {
					delete();
				}
			});
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
