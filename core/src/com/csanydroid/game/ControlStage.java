package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ControlStage extends Stage {

    private final GameStage gameStage;

    private final static Texture ballTexture = new Texture("hole.png"), starTexture = new Texture("hole.png");

    protected OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public ControlStage(Batch batch, GameStage gameStage) {
        super(new ScalingViewport(Scaling.fill, 0, 0), batch);
        setViewport(new ScreenViewport(camera));
        camera.position.x = -10;
        camera.update();
        camera.setToOrtho(true);

        this.gameStage = gameStage;
        this.batch = getBatch();
    }

    private final static float STAR_WIDTH = 30, STAR_BAR_WIDTH = 200;

    Batch batch;

    private float elapsedTime = 0;

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;
    }

    @Override
    public void draw() {
        super.draw();

        batch.begin();
        int i = gameStage.getCollectedStars();
        if(i > 0) {
            final float step = STAR_BAR_WIDTH / (i - 1);
            float x = 0;
            do {
                double angle = Math.toRadians(elapsedTime * 60 + i * 20) % MathUtils.PI2;
                if(angle < 0 || angle > Math.PI) angle = 0;
                batch.draw(starTexture, x, 0 - (float)Math.sin(angle) * (STAR_WIDTH / 4), STAR_WIDTH, STAR_WIDTH);

                x += step;
            } while (--i > 0);
        }
        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();

    }
}
