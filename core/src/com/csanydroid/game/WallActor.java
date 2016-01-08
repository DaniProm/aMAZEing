package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WallActor extends GameActor {

	// H: szerintem valami másik kép kellene, mert eléggé zavaró mozgás közben
	protected static Texture texture = new Texture("normwall.png");

	public WallActor() {
		sprite = new Sprite(texture);
		sprite.setSize(1, 1);
	}

}
