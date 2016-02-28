package com.csanydroid.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/** A játék menüje*/
public class MenuScreen extends MyScreen{
    Stage stage;
    static float getButtonPosX = 0,getButtonPosY = 0, getButtonHeight = 0;
    private final Music music = Assets.manager.get(Assets.MENU_MUSIC);

    GameActor actor;


	public MenuScreen() {
      super();
         setBackgroundColor(0.3f, 0.8f, 0.9f);

        stage = new Stage() {
            @Override
            public boolean keyDown(int keycode) {
                switch (keycode) {
                    case Input.Keys.ESCAPE:
                    case Input.Keys.BACK:
                        ((Game) Gdx.app.getApplicationListener()).setScreen(new MazeSelectorScreen());
                        break;
                }
                return false;
            }

        };



        MyButton button;

        //Sprite sprite = new Sprite(Assets.manager.get(Assets.PLAY));
        //sprite.setPosition(487, 265);





        actor = new BallActor();
        actor.setSize(128, 128);
        //camera = new OrthographicCamera(1024,768);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
  
        stage.setViewport(viewport);

        MenuBackgroundActor menuBackgroundActor = new MenuBackgroundActor();
        menuBackgroundActor.setPosition(0, 0);
        menuBackgroundActor.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        button = new MyButton("Play", MyWindow.textButtonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MazeSelectorScreen());
            }
        });
        button.setPosition(Gdx.graphics.getWidth() * 0.7f, Gdx.graphics.getHeight() * 0.3f);
        getButtonPosX = button.getX(); getButtonPosY= button.getY(); getButtonHeight = button.getHeight();

        stage.addActor(menuBackgroundActor);

        stage.addActor(button);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        viewport.update(width, height);
        camera.update();
       /* camera.viewportWidth = width;
        camera.viewportHeight = height;

        stage.getViewport().update(width, height);*/
        /*camera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.translate(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        viewport = new ExtendViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera);
        stage.setViewport(viewport);
        stage.getCamera().update();
*/
    }


    @Override
    public void hide() {
        super.hide();
        music.pause();
    }

    @Override
    public void show() {
        music.play();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        stage.act(delta);
        stage.draw();
        batch.end();
    }
}
