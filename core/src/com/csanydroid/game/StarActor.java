package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StarActor extends GameActor {

	protected static Texture texture = new Texture("star.png");

	private final Music music = Gdx.audio.newMusic(Gdx.files.internal("teleport.mp3"));

	public StarActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
	}

	public void collect() {
		if(hasCollected) return;
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
	public void dispose() {
		music.dispose();
	}

}