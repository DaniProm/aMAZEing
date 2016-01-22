package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class ControlStage extends Stage {

    private final GameStage gameStage;

    protected OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    public ControlStage(Batch batch, GameStage gameStage) {
        super(new ExtendViewport(1024, 768), batch);
        camera.position.x = -10;
        camera.update();
        camera.setToOrtho(true);

        this.gameStage = gameStage;
        starSpace = STAR_BAR_WIDTH / gameStage.getMaze().getStarsCount();

    }

    private final static float STAR_WIDTH = 30, STAR_BAR_WIDTH = 200;

    private Animation animation = new Animation(1 / 30f, Assets.manager.get(Assets.STAR_ATLAS).getRegions(), Animation.PlayMode.LOOP);

    private List<Star> stars = new ArrayList<Star>();

    private final float starSpace;

    @Override
    public void draw() {
        super.draw();

        for(int collectedStarsNum = gameStage.getCollectedStars();collectedStarsNum > stars.size();)
            new Star();
    }

    private class Star extends Actor {

        private float stateTime = 0;

        Sprite sprite = new Sprite();

        public Star() {
            addActor(this);
            stars.add(this);
            setSize(STAR_WIDTH, STAR_WIDTH);
            setPosition(10 + (stars.size() - 1) * starSpace, 768 - STAR_WIDTH - 10);

        }

        @Override
        public void act(float delta) {
            stateTime += delta;
            sprite.setRegion(animation.getKeyFrame(stateTime));
        }

        @Override
        protected void positionChanged() {
            super.positionChanged();
            sprite.setPosition(getX(), getY());
        }

        @Override
        protected void sizeChanged() {
            super.sizeChanged();
            sprite.setSize(getWidth(), getHeight());
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            sprite.draw(batch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();

    }
}
