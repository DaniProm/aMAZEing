package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MyScreen implements Screen {

	protected final SpriteBatch batch = new SpriteBatch();
	public final static float WORLD_WIDTH = 160, WORLD_HEIGHT = 90;
	protected OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
	//protected Viewport viewport = new ScreenViewport(camera);
	protected Viewport viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
	private static String CHARS = "0123456789öüóqwertzuiopőúasdfghjkléáűíyxcvbnm'+!%/=()ÖÜÓQWERTZUIOPŐÚASDFGHJKLÉÁŰÍYXCVBNM?:_*<>#&@{}[],-.";
	protected static BitmapFont FONT_HOBO_STD, /*FONT_HOBO_STD_TOP, */FONT_CALIBRI;

     private float r=0.5f,g=0.5f,b=0.5f;

	public MyScreen() { }
/*
	protected static BitmapFont font;
	static {

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AlegreyaSC-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 200;
		parameter.kerning = false;
		parameter.color = Color.BLACK;
		parameter.characters = CHARS;
		//font = generator.generateFont(parameter);
		generator.dispose();

	}*/

	static {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AlegreyaSC-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		parameter.characters = CHARS;
		FONT_HOBO_STD = generator.generateFont(parameter);
		FONT_HOBO_STD.setColor(0, 0, 0, 1f);
		generator.dispose();
	}

	/*static {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AlegreyaSC-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 55;
		parameter.characters = CHARS;
		FONT_HOBO_STD_TOP = generator.generateFont(parameter);
		FONT_HOBO_STD_TOP.setColor(0, 0, 0, 1f);
		generator.dispose();
	}*/

	static {
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AlegreyaSC-Regular.otf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 27;
		parameter.characters = CHARS;
		FONT_CALIBRI = generator.generateFont(parameter);
		FONT_CALIBRI.setColor(0, 0, 0, 1f);
		generator.dispose();
	}





	protected static final TextButton.TextButtonStyle TEXT_BUTTON_STYLE;

	static {
		TEXT_BUTTON_STYLE = new TextButton.TextButtonStyle();
		TEXT_BUTTON_STYLE.font = FONT_HOBO_STD;
		TEXT_BUTTON_STYLE.fontColor = Color.WHITE;
		TEXT_BUTTON_STYLE.downFontColor = Color.GREEN;
		TEXT_BUTTON_STYLE.overFontColor = Color.valueOf("880000");
		TEXT_BUTTON_STYLE.pressedOffsetX = 3;
		TEXT_BUTTON_STYLE.pressedOffsetY = 3;
	}

	protected static final Label.LabelStyle LABEL_STYLE;

	static {
		LABEL_STYLE = new Label.LabelStyle();
		LABEL_STYLE.font = FONT_HOBO_STD; // 45
		LABEL_STYLE.fontColor = Color.WHITE;
	}

	protected static final Label.LabelStyle LABEL_STYLE_TOP;

	static {
		LABEL_STYLE_TOP = new Label.LabelStyle();
		LABEL_STYLE_TOP.font = FONT_HOBO_STD;
		LABEL_STYLE_TOP.fontColor = Color.WHITE;
	}

	protected static final Label.LabelStyle LABEL_STYLE2;

	static {
		LABEL_STYLE2 = new Label.LabelStyle();
		LABEL_STYLE2.font = FONT_HOBO_STD; //40
		LABEL_STYLE2.fontColor = Color.WHITE;
	}

        public void setBackgroundColor(float r, float g, float b)
        {
            this.r=r;
            this.g=g;
            this.b=b;
        }

	@Override
	public void render(float delta) {
		    Gdx.gl.glClearColor(r, g, b, 1);
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
