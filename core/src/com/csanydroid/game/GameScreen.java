package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import java.io.IOException;

public class GameScreen extends MyScreen {

	private Box2DDebugRenderer debugger = new Box2DDebugRenderer();
	private GameStage gameStage;
	private ControlStage controlStage;

	GameScreen(Maze maze) {
		super();
            setBackgroundColor(0f,0.3f,0f);

		gameStage = new GameStage(viewport, batch, maze);
        gameStage.lookAtMaze(camera);

        controlStage = new ControlStage(batch, gameStage);

		GestureDetector gd = new GestureDetector(20, 0.5f, 2, 0.15f, gameStage);
		InputMultiplexer im = new InputMultiplexer(gd, gameStage);
		Gdx.input.setInputProcessor(im);

	}

	@Override
	public void render(float delta) {

		super.render(delta);

        gameStage.updateCamera(camera);
        gameStage.act(delta);
        gameStage.draw();

        batch.setProjectionMatrix(controlStage.camera.combined);
        controlStage.act();
        controlStage.draw();

        batch.begin();
        debugger.render(gameStage.world, camera.combined);
        batch.end();

	}


}
