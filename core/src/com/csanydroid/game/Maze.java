package com.csanydroid.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.sun.media.sound.InvalidFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Maze {

	private static ArrayList<Maze> mazes = new ArrayList<Maze>();

	static {
		for (FileHandle fh : Arrays.asList(Gdx.files.internal("mazes").list())) {
			mazes.add(new Maze(fh.nameWithoutExtension()));
		}

	}

	public static Maze findMaze(String name) {
		for(Maze maze : mazes) {
			if(maze.getName().compareToIgnoreCase(name) == 0) return maze;
		}

		throw new NoSuchElementException("Maze not exists");

	}

	public static ArrayList<Maze> getMazes() {
		return mazes;
	}

	private final String name;

	public String getDescription() {
		return description;
	}

	private String description;
	private int width;

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getName() {
		return name;
	}

	private int height;
	private final ArrayList<MazeObject> objects = new ArrayList<MazeObject>();

	public int getStarsCount() {
		return starsCount;
	}

	private int starsCount = 0;

	private Maze(String name) {
		this.name = name;

		try {
			BufferedReader reader = new BufferedReader(Gdx.files.internal("mazes/" + name + ".txt").reader());
			String line;

			this.description = reader.readLine();

			{
				int y = 0;
				while(null != (line = reader.readLine())) {
					if (line.compareTo("") == 0) break;

					y++;
					int x = 0;
					for (int i = 0; i < line.length(); i++, x++) {
						final char ch = line.charAt(i);
						switch (ch) {
							case ' ':
								break;
							case '<':
								if (x >= 2) x -= 2;
								else x = 0;
								break;
							case '.':
								new MazeObject(ObjectType.WALL, x, y);
								break;
							case ':':
								new MazeObject(ObjectType.EXPLOSIVE_WALL, x, y, 3.0f);
								break;
							case 'O':
								new MazeObject(ObjectType.BALL, x, y);
								break;
							case 'X':
								new MazeObject(ObjectType.HOLE, x, y);
								break;
							case '%':
								new MazeObject(ObjectType.DOOR, x, y);
								break;
							case '@':
								new MazeObject(ObjectType.BLACK_HOLE, x, y);
								break;
							case '~':
								new MazeObject(ObjectType.PUDDLE, x, y);
								break;
							case '*':
								new MazeObject(ObjectType.STAR, x, y);
								break;
							default:

								if (ch >= 'a' && ch <= 'f') {
									new MazeObject(ObjectType.WORMHOLE, x, y, ch - 'a');
								} else if (ch >= 'A' && ch <= 'F') {
									new MazeObject(ObjectType.WORMHOLE, x, y, ch - 'A');
								}

								break;
						}

					}
					if(x > this.width) this.width = x;

				}
				this.height = y;
			}

			final Pattern pattern = Pattern.compile("(\\d+),(\\d+):(\\d) (.*)");

			for (int i = 0; null != (line = reader.readLine()); i++) {
				try {
					Matcher matcher = pattern.matcher(line);
					matcher.find();

					int x = Integer.parseInt(matcher.group(1));
					int y = Integer.parseInt(matcher.group(2)) + 1;
					int width = Integer.parseInt(matcher.group(3));
					String caption = matcher.group(4);

					new MazeObject(ObjectType.SCRIBBLE, x, y, caption, width);

				} catch(IllegalStateException ignored) {
				}

			}

			Gdx.app.log("maze", "maze loaded: " + getName());

			reader.close();

		} catch (IOException ignored) { }

	}

	public void beginPlay() {

		((Game) Gdx.app.getApplicationListener())
				.setScreen(new GameScreen(this));

	}

	public void load(MazeObjectLoader mazeObjectLoader) {

		for (MazeObject object : this.objects) {
			mazeObjectLoader.createObject(object);
		}

	}

	public enum ObjectType {
		WALL, EXPLOSIVE_WALL, BALL, HOLE, BLACK_HOLE, WORMHOLE, PUDDLE, STAR, DOOR, SCRIBBLE;
	}

	public class MazeObject {

		private final ObjectType type;
		private final int x;

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		private final int y;

		public ObjectType getType() {
			return type;
		}

		private final Object[] params;

		public Object[] getParams() {
			return params;
		}

		private MazeObject(ObjectType type, int x, int y, Object... params) {
			this.type = type;
			this.x = x;
			this.y = y;
			this.params = params;

			switch (type) {
				case STAR:
					++starsCount;
					break;
			}

		}

		{
			objects.add(this);
		}

	}

	interface MazeObjectLoader {
		void createObject(final MazeObject o);
	}

}
