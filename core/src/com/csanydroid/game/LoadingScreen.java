package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;

public class LoadingScreen extends MyScreen {

	Stage stage;

    public LoadingScreen() {
        setBackgroundColor(0f,0f,0f);
    }

    @Override
	public void show() {

		//game.manager.finishLoading();

		final Label label = new Label("?", LABEL_STYLE);
		label.setVisible(true);
		label.setAlignment(Align.center);
		label.setFillParent(true);

		stage = new Stage() {
			@Override
			public void act() {
				super.act();
				label.setText((int)(Assets.manager.getProgress() * 100) + "%");
			}

		};


		stage.addActor(label);

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


	}
}
