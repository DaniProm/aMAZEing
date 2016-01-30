package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

public class GameScreen extends MyScreen {

	//private Box2DDebugRenderer debugger = new Box2DDebugRenderer();
	private GameStage gameStage;
	private ControlStage controlStage;
    InterMazeWindow imw = null;
    @Override
    public void hide() {
        super.hide();
        if(imw != null) imw.remove();
    }

    GameScreen(Maze maze) {
		super();
            setBackgroundColor(0f, 0.3f, 0f);

		gameStage = new GameStage(viewport, batch, maze);
        gameStage.lookAtMaze(camera);
gameStage.setEventListener(new GameStage.EventListener() {

    @Override
    public void onStateChange() {
        //System.out.println("state changed: " + gameStage.getState());
        if (imw != null) {
            imw.remove();
            imw = null;
        }

        if (gameStage.getState() == GameStage.GameState.PLAYING) {
            Gdx.input.setInputProcessor(gameStage);
            return;
        }
        imw = new InterMazeWindow(gameStage);
        controlStage.addActor(imw);

        Gdx.input.setInputProcessor(controlStage);


    }

    @Override
    public void onBallRemove() {
        Gdx.input.vibrate(250);
    }
});
        controlStage = new ControlStage(batch, gameStage);


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
/*
        batch.begin();
        debugger.render(gameStage.world, camera.combined);
        batch.end();*/

	}


}
