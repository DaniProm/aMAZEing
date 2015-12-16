package com.csanydroid.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;

public class Maze {

    static ArrayList<Maze> mazes = new ArrayList<Maze>();
    static {
        ArrayList<String> mazes = new ArrayList<String>();
        for(FileHandle fileHandle : Gdx.files.internal("mazes").list(".txt")) {
            mazes.add(fileHandle.nameWithoutExtension());
        }
    }

    private boolean unlocked = false;
    private int size;

    /*
    O golyó
    X lyuk
    . fal
    * ajtó
   [ angol ábécé betűi ] féregjárat
    @ feketelyuk
    ~ pocsolya
     */

    Maze(String name) {
        // TODO a név alapján olvassuk be a fájlt


    }

    public void unlock() {
        unlocked = true;
    }



}
