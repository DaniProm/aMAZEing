package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MyScreen implements Screen {
	protected Viewport viewport;
	protected OrthographicCamera camera;
	protected SpriteBatch batch;
	protected final static float WORLD_WIDTH=160;
	protected final static float WORLD_HEIGHT=90;


	public MyScreen() {
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		((OrthographicCamera)(viewport.getCamera())).zoom=1f;
		((OrthographicCamera)(viewport.getCamera())).translate(0,0);
		viewport.getCamera().update();
	}

	//float x=0;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//camera.zoom*=0.99f;
		camera.update();
		batch.setProjectionMatrix(viewport.getCamera().combined);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
	public void show() {
		batch.dispose();
	}


}
