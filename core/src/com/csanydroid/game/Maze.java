package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

	public Maze getNextMaze() {
		return getMaze(getMazeIndex() + 1);
	}

	public static Maze getMaze(int index) {
		return mazes.get(index);
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

	
    private int remainingBalls = 0;

	public int getRemainingBalls() {
		return remainingBalls;
	}

	public static Maze createRandomMaze() {
		return new Maze("A", "B", 11 + (int)(Math.random() * 6));
	}

	private Maze(String name, String description, int size) {
		this.name = name;
		this.description = description;

		this.width = 2 * size + 2;
		this.height = 2 * size;

		final int [][] maze = new int[size][size];

		generateMaze(0,0, maze, size);

		new MazeObject(ObjectType.BALL, 0, 0);

		// doors
		for(int n = (int)(Math.random() * 1.8 * size);n > 0;) {
			final int
					x = (int)(Math.random() * size),
					y = (int)(Math.random() * size);

			if(Math.random() < .5) {
				if ((maze[x][y] & 1) == 0) continue;
				new MazeObject(ObjectType.DOOR, 2 * x, 2 * y - 1, false);
			} else {
				if ((maze[x][y] & 8) == 0) continue;
				new MazeObject(ObjectType.DOOR, 2 * x - 1, 2 * y, false);
			}

			--n;

		}

		// stars
		for(final int n = (int)(Math.random() * 3.5 * size);starsCount < n;) {
			final int
					x = (int)(Math.random() * size),
					y = (int)(Math.random() * size);

			if(Math.random() < .5) {
				if ((maze[x][y] & 1) != 0) {
					new MazeObject(ObjectType.STAR, 2 * x, 2 * y - 1);
				}
			} else {
				if ((maze[x][y] & 8) != 0) {
					new MazeObject(ObjectType.STAR, 2 * x - 1, 2 * y);
				}
			}
		}

		// puddles
		for(int n = (int)(Math.random() * 2.5 * size);n > 0;) {
			final int
					x = (int)(Math.random() * size),
					y = (int)(Math.random() * size);

			if(Math.random() < .5) {
				if ((maze[x][y] & 1) == 0) continue;
				new MazeObject(ObjectType.PUDDLE, 2 * x, 2 * y - 1);
			} else {
				if ((maze[x][y] & 8) == 0) continue;
				new MazeObject(ObjectType.PUDDLE, 2 * x - 1, 2 * y);
			}

			--n;
		}



		{
			ArrayList<Character> chars = new ArrayList<Character>(Arrays.asList('A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'));
			Collections.shuffle(chars);
			while(chars.size() > 0) {
				final int
						x = (int)(Math.random() * size),
						y = (int)(Math.random() * size);

				if(Math.random() < .5) {
					if ((maze[x][y] & 1) == 0) continue;
					new MazeObject(ObjectType.SCRIBBLE, 2 * x, 2 * y - 1, Character.toString(chars.remove(0)), 1);
				} else {
					if ((maze[x][y] & 8) == 0) continue;
					new MazeObject(ObjectType.SCRIBBLE, 2 * x - 1, 2 * y, Character.toString(chars.remove(0)), 1);
				}

			}

		}


		for (int y = 0; y < size; y++) {

			for (int x = 0; x < size; x++) {
				new MazeObject(ObjectType.WALL, 2 * x - 1, 2 * y - 1);

				if((maze[x][y] & 1) == 0) { // north edge
					new MazeObject(ObjectType.WALL, 2 * x, 2 * y - 1);
				}

				if((maze[x][y] & 8) == 0){ // west edge
					new MazeObject(ObjectType.WALL, 2 * x - 1, 2 * y);

				}
			}

			new MazeObject(ObjectType.WALL, 2 * size - 1, 2 * y);
			new MazeObject(ObjectType.WALL, 2 * size - 1, 2 * y - 1);
		}

		// bottom wall
		for(int x = 0;true;++x) {
			new MazeObject(ObjectType.WALL, 2 * x - 1, 2 * size - 1);
			if(x + 1 >= size) break;
			new MazeObject(ObjectType.WALL, 2 * x, 2 * size - 1);
		}


		new MazeObject(ObjectType.WALL, 2 * size - 1, 2 * size - 1);
		new MazeObject(ObjectType.WALL, 2 * size - 1, 2 * size);
		new MazeObject(ObjectType.WALL, 2 * size - 3, 2 * size);
		new MazeObject(ObjectType.WALL, 2 * size - 3, 2 * size + 1);
		new MazeObject(ObjectType.WALL, 2 * size - 2, 2 * size + 1);
		new MazeObject(ObjectType.WALL, 2 * size - 1, 2 * size + 1);
		new MazeObject(ObjectType.DOOR, 2 * size - 2, 2 * size - 1, false);
		new MazeObject(ObjectType.HOLE, 2 * size - 2, 2 * size);

		adjustObjects();

	}

	private void generateMaze(int cx, int cy, int [][] maze, int size) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, size) && between(ny, size) && (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny, maze, size);
			}
		}
	}

	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}

	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;

		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}

		DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};

	public static final int MAX_WORMHOLES = '9' - '1' + 1;
	public static final int MAX_SWITCHES = 'F' - 'A' + 1;

	private Maze(String name) {

		this.name = name;

		try {
			BufferedReader reader = new BufferedReader(Gdx.files.internal("mazes/" + name + ".txt").reader());
			String line;

			this.description = reader.readLine();

			{
				int y = 0;
				while(null != (line = reader.readLine())) {
					if (line.trim().compareTo("") == 0) break;

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
								new MazeObject(ObjectType.DOOR, x, y, false);
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

								if (ch >= '1' && ch <= '9') {
									new MazeObject(ObjectType.WORMHOLE, x, y, ch - '0');

								} else if (ch >= 'a' && ch <= 'g') {
									new MazeObject(ObjectType.SWITCH, x, y, false, ch - 'a', false, false);
								} else if (ch >= 'A' && ch <= 'G') {
									new MazeObject(ObjectType.GATE, x, y, false, ch - 'A');

								} else if (ch >= 'h' && ch <= 'n') {
									new MazeObject(ObjectType.PUSH_BUTTON, x, y, ch - 'a', false);
								} else if (ch >= 'H' && ch <= 'N') {
									new MazeObject(ObjectType.GATE, x, y, false, ch - 'A');
								}

								break;
						}

					}
					if(x > this.width) this.width = x;

				}
				this.height = y;
			}

			{
				final Pattern pattern = Pattern.compile("(\\d+),(\\d+):(\\d) (.*)");

				for (int i = 0; null != (line = reader.readLine()); i++) {
					if (line.trim().compareTo("") == 0) break;
					try {
						Matcher matcher = pattern.matcher(line);
						matcher.find();

						int x = Integer.parseInt(matcher.group(1));
						int y = Integer.parseInt(matcher.group(2)) + 1;
						int width = Integer.parseInt(matcher.group(3));
						String caption = matcher.group(4);

						new MazeObject(ObjectType.SCRIBBLE, x, y, caption, width);

					} catch (IllegalStateException ignored) {
					}

				}
			}

			{
				for (int i = 0; null != (line = reader.readLine()); i++) {
					if (line.trim().compareTo("") == 0) break;
					try {

						final int j = line.charAt(0) - 'a';

						MazeObject object = findButton(j);
						if(object == null) continue;

						final String[] params = line.substring(1).trim().split(",");
						if(object.type == ObjectType.SWITCH) {
							object.setParam(SWITCH_DEFAULTSTATE, Boolean.parseBoolean(params[0].trim()));
							object.setParam(SWITCH_REVERSED, Boolean.parseBoolean(params[1].trim()));
						} else {
							object.setParam(PUSHBUTTON_DEFAULTSTATE, Boolean.parseBoolean(params[0].trim()));
						}

					} catch (IllegalStateException ignored) {
					}

				}
			}

			reader.close();

		} catch (IOException ignored) { }

		adjustObjects();
	}

	private void adjustObjects() {

		for (MazeObject object : this.objects) {
			switch (object.type) {
				case DOOR:
				case GATE:
				case SWITCH:
				{

					final int
							h = (findWall(object.getX() - 1, object.getY()) != null ? 1 : 0) + (findWall(object.getX() + 1, object.getY()) != null ? 1 : 0),
							v = (findWall(object.getX(), object.getY() - 1) != null ? 1 : 0) + (findWall(object.getX(), object.getY() + 1) != null ? 1 : 0);

					object.setParam(0, v >= h);
				}
			}
		}

	}

    public ArrayList<MazeObject> getObjects() {
        return objects;
    }

    public void beginPlay() throws Exception {

        if (!isUnlocked()) {
            throw new Exception("Maze is locked.");
        }

	    ((AmazingGame) Gdx.app.getApplicationListener())
			    .setScreen(new GameScreen(this));

	}

    private static String PREF_LEVEL_NUMBER = "level_number";

    private final static AmazingGame ag = (AmazingGame) Gdx.app.getApplicationListener();
    public void unlockNext() {
		if (ag.prefs.getInteger(PREF_LEVEL_NUMBER)<getMazeIndex() + 1) {
			ag.prefs.putInteger(PREF_LEVEL_NUMBER, getMazeIndex() + 1);
			ag.prefs.flush();
		}
    }

    public boolean isUnlocked() {
        int i = getMazeIndex();
        if(i > 0) {
            return ag.prefs.getInteger(PREF_LEVEL_NUMBER) >= i;
        } else return true;
    }

	public void unlockAll() {
		ag.prefs.putInteger(PREF_LEVEL_NUMBER, mazes.size());
		ag.prefs.flush();
	}
	public void lockAll() {
		ag.prefs.putInteger(PREF_LEVEL_NUMBER, mazes.size());
		ag.prefs.clear();
	}

    public int getMazeIndex() {
        return mazes.indexOf(this);
    }

	public static final int PUSHBUTTON_INDEX = 0;
	public static final int GATE_ORIENTATION = 0;
	public static final int DOOR_ORIENTATION = 0;
	public static final int SWITCH_ORIENTATION = 0;
	public static final int SWITCH_INDEX = 1;
	public static final int PUSHBUTTON_DEFAULTSTATE = 1;
	public static final int GATE_INDEX = 1;
	public static final int SWITCH_DEFAULTSTATE = 2;
	public static final int SWITCH_REVERSED = 3;

	public enum ObjectType {
		WALL(Assets.manager.get(Assets.WALL)),
		EXPLOSIVE_WALL(Assets.manager.get(Assets.EXPLOSIVE_WALL)),
		BALL(Assets.manager.get(Assets.BALL_RED_ATLAS), Assets.manager.get(Assets.BALL_BLUE_ATLAS),Assets.manager.get(Assets.BALL_GREEN_ATLAS),Assets.manager.get(Assets.BALL_ORANGE_ATLAS)),
		HOLE(Assets.manager.get(Assets.HOLE)),
		BLACK_HOLE(Assets.manager.get(Assets.BLACK_HOLE)),
		WORMHOLE(Assets.manager.get(Assets.WORMHOLE)),
		PUDDLE(Assets.manager.get(Assets.PUDDLE)),
		STAR,
		DOOR(Assets.manager.get(Assets.DOOR_ATLAS)),
		SCRIBBLE,
		SWITCH,
		PUSH_BUTTON,
		GATE(Assets.manager.get(Assets.DOOR_ATLAS));

		public TextureRegion getPreview() {
			return textureRegion;
		}

		private final TextureRegion textureRegion;

		ObjectType() {
			textureRegion = null;
		}

		ObjectType(Texture... textures) {
			this(textures[(int)(textures.length * Math.random())]);
		}

		ObjectType(Texture texture) {
			this(new TextureRegion(texture));
		}

		ObjectType(TextureAtlas... atlases) {
			this(atlases[(int)(atlases.length * Math.random())]);
		}

		ObjectType(TextureAtlas atlas) {
			this(atlas.findRegion("preview"));
		}


		ObjectType(TextureRegion textureRegion) {
			this.textureRegion = textureRegion;
		}
	}


	public MazeObject findButton(int i) {
		for (MazeObject object : this.objects) {
			switch (object.type) {
				case SWITCH:
					if((Integer)object.getParam(SWITCH_INDEX) == i) return object;
					break;
				case PUSH_BUTTON:
					if((Integer)object.getParam(PUSHBUTTON_INDEX) == i) return object;
					break;
			}
		}

		return null;
	}

	public MazeObject findGate(int i) {
		for (MazeObject object : this.objects) {
			if(object.type == ObjectType.GATE && (Integer)object.getParam(GATE_INDEX) == i) return object;
		}

		return null;
	}

	public MazeObject findWall(int x, int y) {
		for (MazeObject object : this.objects) {
			if(object.x == x && object.y == y && object.type == ObjectType.WALL) return object;
		}

		return null;
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

		private Object[] params;

		public Object[] getParams() {
			return params;
		}
		public Object getParam(int i) { return params[i]; }

        public void setParams(Object[] params) {
            this.params = params;
        }
		public void setParam(int i, Object o) {
			this.params[i] = o;
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

}
