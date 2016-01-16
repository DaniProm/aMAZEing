package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class LoadingScreen extends MyScreen {

	Stage stage;

    public LoadingScreen() {
        setBackgroundColor(0f,0f,0f);
    }

    @Override
	public void show() {
		Assets.manager.load(Assets.LOADING_ATLAS);
	    Assets.manager.finishLoading();

		stage = new Stage() {

			final Array<TextureAtlas.AtlasRegion> loadingAtlasRegions = Assets.manager.get(Assets.LOADING_ATLAS).getRegions();

			Sprite sprite = new Sprite();
			{
				sprite.setSize(15,15);
				sprite.setPosition(-7, -8);
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