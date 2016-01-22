package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

    public LoadingScreen() {
        setBackgroundColor(0f,0f,0f);
    }

    @Override
	public void show() {
		Assets.manager.load(Assets.LOADING_ATLAS);
	    Assets.manager.finishLoading();
        camera = new OrthographicCamera(1024,768);
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
				batch.begin();
				sprite.draw(batch);
				batch.end();
			}

			@Override
			public void act() {

				int i = (int)(loadingAtlasRegions.size * Assets.manager.getProgress()) - 1;
				sprite.setRegion(loadingAtlasRegions.get(Math.max(0, i)));
			}
		};

		Assets.load();

	}

	@Override
	public void render(float delta) {
		super.render(delta);

		if (Assets.manager.update()) {
            Assets.afterLoaded();
            ((AmazingGame) Gdx.app.getApplicationListener())
					.setScreen(new MenuScreen());
		}

		stage.act();
		stage.draw();

	}

	@Override
	public void hide() {
		Assets.manager.unload(Assets.LOADING_ATLAS.fileName);

	}
}
