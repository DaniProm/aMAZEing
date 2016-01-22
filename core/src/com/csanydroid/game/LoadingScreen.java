package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import javafx.scene.Camera;

public class LoadingScreen extends MyScreen {

    Stage stage;
	Array<TextureAtlas.AtlasRegion> loadingAtlasRegions;
    public LoadingScreen() {
        setBackgroundColor(0f, 0f, 0f);

    }
	Sprite sprite = new Sprite();



    @Override
	public void show() {
		Assets.manager.load(Assets.LOADING_ATLAS);
	    Assets.manager.finishLoading();
		loadingAtlasRegions = Assets.manager.get(Assets.LOADING_ATLAS).getRegions();
		sprite.setSize(loadingAtlasRegions.get(0).getRegionWidth(), loadingAtlasRegions.get(0).getRegionHeight());
		sprite.setPosition(Gdx.graphics.getWidth()/2-sprite.getWidth()/2,Gdx.graphics.getHeight()/2-sprite.getHeight()/2);
        /*camera = new OrthographicCamera(1024,768);
        camera.translate(512,384);
        viewport = new ExtendViewport(1024, 768, camera);
		stage = new Stage(viewport) {

			final Array<TextureAtlas.AtlasRegion> loadingAtlasRegions = Assets.manager.get(Assets.LOADING_ATLAS).getRegions();


			Sprite sprite = new Sprite();
			{
				sprite.setSize(400,400);
				sprite.setPosition(312, 184);
			}
			@Override
			public void draw() {
				super.draw();

				sprite.draw(batch);

			}

			@Override
			public void act() {

				int i = (int)(loadingAtlasRegions.size * Assets.manager.getProgress()) - 1;
				sprite.setRegion(loadingAtlasRegions.get(Math.max(0, i)));
			}
		};*/

		Assets.load();

	}

	@Override
	public void render(float delta) {
		//super.render(delta);
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Assets.manager.update()) {
            Assets.afterLoaded();
            ((AmazingGame) Gdx.app.getApplicationListener())
					.setScreen(new MenuScreen());
		}
		//stage.act();
		batch.begin();

		int i = (int) (loadingAtlasRegions.size * Assets.manager.getProgress()) - 1;
		sprite.setRegion(loadingAtlasRegions.get(Math.max(0, i)));
		sprite.draw(batch);
		//stage.draw();
		batch.end();

	}

	@Override
	public void hide() {
		Assets.manager.unload(Assets.LOADING_ATLAS.fileName);

	}
}
