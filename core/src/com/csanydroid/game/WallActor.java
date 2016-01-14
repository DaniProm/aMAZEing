package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class WallActor extends GameActor {

	// H: szerintem valami másik kép kellene, mert eléggé zavaró mozgás közben

	public WallActor() {
		sprite = new Sprite(Assets.manager.get(Assets.WALL));
		sprite.setSize(1, 1);
	}

}
