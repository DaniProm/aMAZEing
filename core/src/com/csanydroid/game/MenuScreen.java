package com.csanydroid.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/** A játék menüje*/
public class MenuScreen extends MyScreen{
    Stage stage;
    private final Sound s = Gdx.audio.newSound(Gdx.files.internal("teleport.mp3"));
    private Sprite sprite;

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
        MenuBackgroundActor menuBackgroundActor = new MenuBackgroundActor();
        menuBackgroundActor.setPosition(0, 0);
        menuBackgroundActor.setSize(1024, 768);
        stage.addActor(menuBackgroundActor);
        System.out.println("Log: 'GameMenu' meghíva.");

        System.out.println("Log: 'Tábla' létrehozva.");

        TextButton button;
        // gombok hozzáadása

        final float ROW_HEIGHT = 75f;

        sprite = new Sprite(Assets.manager.get(Assets.PLAY));
        sprite.setPosition(1024 / 2.1f, 768 / 2.9f);


        button = new TextButton("Play ", MyWindow.textButtonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MazeSelectorScreen());
            }
        });
        button.setPosition(1024 / 2.1f, 768 / 2.9f);
        stage.addActor(button);
        System.out.println((char) 0 * 34);
        actor = new BallActor();
        actor.setSize(128, 128);
        camera = new OrthographicCamera(1024,768);
        camera.translate(512,384);
        viewport = new ExtendViewport(1024, 768, camera);
        stage.setViewport(viewport);


        //myDialog.remove();
        //myDialog.show(stage);
        //stage.addActor(new MyWindow());
        //stage.addActor(actor);
    }
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        stage.getViewport().update(width, height);
    }


    @Override
    public void hide() {
        super.hide();
        s.pause();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        /*s.setVolume(1,0.5f);
        s.play();
        s.setLooping(1,true);*/
    }



    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        batch.end();
    }
}

/**GameStage gamestage = new GameStage(Viewport viewport, Batch batch, Maze maze);
 * NextLevelWindow nextLevelWindow = new NextLevelWindow(gamestage);
 * stage.addActor(nextLevelWindow);
 */