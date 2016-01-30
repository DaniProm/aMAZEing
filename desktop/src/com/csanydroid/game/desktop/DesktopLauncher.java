package com.csanydroid.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.csanydroid.game.AmazingGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty("user.name","CorrectUserName");
		config.title = "The aMAZEing game";
		config.width = 1024;
		config.height = 576;
		new LwjglApplication(new AmazingGame(), config);
	}
}
