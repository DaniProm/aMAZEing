package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

/* Ez fog elnyelni*/
public class BlackHoleActor extends GameActor {

	protected static Texture texture = new Texture("black_hole.png");

	public BlackHoleActor() {
		sprite = new Sprite(texture);
		setSize(GameScreen.BASE_SIZE, GameScreen.BASE_SIZE);
	}

    @Override
    public void dispose() {

    }

}
