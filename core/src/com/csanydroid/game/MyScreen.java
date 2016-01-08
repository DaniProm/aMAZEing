package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class MyScreen implements Screen {

	protected final SpriteBatch batch = new SpriteBatch();
	protected final static float WORLD_WIDTH = 160, WORLD_HEIGHT = 90;
	protected OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
	//protected Viewport viewport = new ScreenViewport(camera);
	protected Viewport viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);


	protected static BitmapFont font;
	static {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AlegreyaSC-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 200;
		parameter.kerning = false;
		parameter.color = Color.BLACK;
		font = generator.generateFont(parameter);
		generator.dispose();

	}

	public MyScreen() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.7f, .7f, .7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
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
		batch.dispose();
	}

	@Override
	public void show() {


	}


}
