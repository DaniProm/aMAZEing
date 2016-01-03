package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.IOException;

public class GameScreen extends MyScreen {

	public static float TILE_SIZE = 128;
	private Box2DDebugRenderer debugger = new Box2DDebugRenderer();
	private GameStage gameStage;
	private Stage asfd = new Stage() {

	};

	GameScreen(String maze) throws IOException {
		super();
		gameStage = new GameStage(viewport, batch, maze);
		GestureDetector gd = new GestureDetector(20, 0.5f, 2, 0.15f, gameStage);
		InputMultiplexer im = new InputMultiplexer(gd, gameStage);
		Gdx.input.setInputProcessor(im);

	}

	@Override
	public void render(float delta) {

		super.render(delta);

		gameStage.act(delta);
		gameStage.draw();

		batch.begin();

		gameStage.updateCamera(camera);
		debugger.render(gameStage.world, camera.combined);
		batch.end();

	}


}
