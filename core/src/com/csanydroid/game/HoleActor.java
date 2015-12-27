package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class HoleActor extends GameActor {

	private boolean hasSwallowedBall = false;

	public void swallowBall(BallActor ball) {
		if(hasSwallowedBall) return;
		ballToSwallow = ball;
	}

	private BallActor ballToSwallow;

	protected static Texture texture = new Texture("hole.png");

	public HoleActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.TILE_SIZE, GameScreen.TILE_SIZE);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if(ballToSwallow != null) {
			hasSwallowedBall = true;
			ballToSwallow.delete();
			ballToSwallow = null;

			// itt lehetne megváltoztatni a sprite-ot is...

		}

	}

	@Override
    public void dispose() {  }

}
