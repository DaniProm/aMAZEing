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

    private int ballsToSurvive = 0;


	public static Maze createRandomMaze() {
		return new Maze("A", "B", 10);
	}

	private Maze(String name, String description, int size) {
		this.name = name;
		this.description = description;

		this.width = size * 2 + 1;
		this.height = size * 2 + 4;

		final int [][] maze = new int[size][size];

		generateMaze(0,0, maze, size);

		new MazeObject(ObjectType.BALL, 0, 0);

		// doors
		for(int n = (int)(Math.random() * size);n > 0;) {
			final int
					x = (int)(Math.random() * size),
					y = (int)(Math.random() * size);

			if(Math.random() < .5) {
				if ((maze[x][y] & 1) == 0) continue;
				new MazeObject(ObjectType.DOOR, 2 * x, 2 * y - 1);
			} else {
				if ((maze[x][y] & 8) == 0) continue;
				new MazeObject(ObjectType.DOOR, 2 * x - 1, 2 * y);
			}

			--n;

		}

		// stars
		for(final int n = (int)(Math.random() * 4 * size);starsCount < n;) {
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
		for(int n = (int)(Math.random() * 3 * size);n > 0;) {
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
		new MazeObject(ObjectType.DOOR, 2 * size - 2, 2 * size - 1);
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

			reader.close();

		} catch (IOException ignored) { }

		adjustObjects();
	}

	private void adjustObjects() {

		for (MazeObject object : this.objects) {
			if(object.type == ObjectType.DOOR) {

				final MazeObject o1 = findMazeObject(object.getX() - 1, object.getY());
				final MazeObject o2 = findMazeObject(object.getX() + 1, object.getY());
				boolean vertical = (o1 == null || o1.type != ObjectType.WALL) || (o2 == null || o2.type != ObjectType.WALL);

				object.setParams(new Object[] {!vertical});

			}
		}

	}

    public ArrayList<MazeObject> getObjects() {
        return objects;
    }

    public void beginPlay() {
	    /*
	   if (!isUnlocked()) {
		    throw new Exception("Maze is locked.");
	    }
		*/

	    Gdx.app.log("játék maze", "begin play: " + getName());

	    ((AmazingGame) Gdx.app.getApplicationListener())
			    .setScreen(new GameScreen(this));

	    {
		    final AmazingGame ag = (AmazingGame) Gdx.app.getApplicationListener();
		    ag.prefs.putBoolean("known/" + getName(), true);
		    ag.prefs.flush();
	    }

	}

	public boolean isKnown() {
		final AmazingGame ag = (AmazingGame) Gdx.app.getApplicationListener();
		return ag.prefs.getBoolean("known/" + getName());
	}


    public void setLock(boolean lock) {
        final AmazingGame ag = (AmazingGame) Gdx.app.getApplicationListener();
        ag.prefs.putBoolean("level/" + getName(), lock);
        ag.prefs.flush();
    }

    public boolean isUnlocked() {
        final AmazingGame ag = (AmazingGame) Gdx.app.getApplicationListener();
        return ag.prefs.getBoolean("level/" + getName());
    }

    public int getMazeIndex() {
        return mazes.indexOf(this);
    }

	public enum ObjectType {
		WALL, EXPLOSIVE_WALL, BALL, HOLE, BLACK_HOLE, WORMHOLE, PUDDLE, STAR, DOOR, SCRIBBLE;
	}

	public MazeObject findMazeObject(int x, int y) {
		for (MazeObject object : this.objects) {
			if(object.x == x && object.y == y) return object;
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

        public void setParams(Object[] params) {
            this.params = params;
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
