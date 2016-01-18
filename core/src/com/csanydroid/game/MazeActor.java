package com.csanydroid.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MazeActor extends Actor {
    private static Texture lockTexture = new Texture("lock.png");
	private static Texture starTexture = new Texture("lock.png");

    private final Maze maze;
	private float size;


    MazeActor(final Maze maze){
        super();
        this.maze = maze;

	    addListener(new ClickListener() {
		    @Override
		    public void clicked(InputEvent event, float x, float y) {
			    try {
				    MazeActor.this.maze.beginPlay();
			    } catch (Exception e) {
				    e.printStackTrace();
			    }
		    }
	    });

    }

	@Override
    public void draw(Batch batch, float parentAlpha) {
		final float
			sourceX = getX() + (getWidth() - (size * maze.getWidth())) / 2,
			sourceY = getY() + getHeight() - (getHeight() - (size * maze.getHeight())) / 2;

	    for(final Maze.MazeObject object : maze.getObjects()) {

		    final TextureRegion texture = object.getType().getPreview();
		    if(texture != null) {
		        batch.draw(
					texture,
					sourceX + object.getX() * size,
					sourceY + -object.getY() * size,
					size,
					size
				);
			}
	    }

        if(!maze.isUnlocked()) {
            batch.draw(lockTexture,
		                      getX() + (getWidth() - lockTexture.getWidth()) / 2,
		                      getY() + (getHeight() - lockTexture.getHeight()) / 2);
        } else if (!maze.isKnown()) {
	        batch.draw(starTexture,
			                  getX(),
			                  getY() - (getHeight() - lockTexture.getHeight()));

        }

    }

	@Override
	protected void sizeChanged() {
		super.sizeChanged();
		size = Math.min(getWidth() / maze.getWidth(), getHeight() / maze.getHeight());
	}

}
