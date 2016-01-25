package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayDeque;

/* Ez fog elnyelni*/
public class BlackHoleActor extends GameActor {

	private ArrayDeque<BallActor> balls = new ArrayDeque<BallActor>();
	private float stateTime = 0;
	protected static Animation animation;
	public BlackHoleActor() {
		final Array<TextureAtlas.AtlasRegion> textureAtlasRegions = Assets.manager.get(Assets.BLACK_HOLE).getRegions();
		sprite = new Sprite(textureAtlasRegions.first());
		sprite.setRegion(textureAtlasRegions.first());
		animation = new Animation(1 / 30f, textureAtlasRegions, Animation.PlayMode.LOOP);
		setSize(1f, 1f);
		setTouchable(Touchable.disabled);
	}

	@Override
	protected Shape getShape() {
		return getCircleShape(.55f);
	}

	public void swallowBall(BallActor ball) {

        Assets.manager.get(Assets.BLACKHOLE_SOUND).play();

		balls.add(ball);
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		stateTime += delta;

		sprite.setRegion(animation.getKeyFrame(stateTime));

		for (BallActor ball; (ball = balls.poll()) != null; ) {
			//float width=getWidth();
			ball.setBlackhole();
			//setSize(getWidth() * 1.02f, getHeight() * 1.02f);
			//setPosition(getX() - (getWidth() - width) / 2, getY() - (getWidth() - width) / 2);
			//ball.sprite.setAlpha(ball.sprite.getColor().a * 0.97f);
			//final float alpha = ball.sprite.getColor().a;
			//if(alpha < 0.1f) {
			//	ball.delete();
			//}
		}


	}

}
