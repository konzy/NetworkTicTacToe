package com.company;

/**
 * Created by konzy on 2/23/2017.
 */
public class Move {

    private static final String VALID_INPUT = "[012] [012]|";

    public int x;
    public int y;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Move(String s) {
        if (s.matches(VALID_INPUT)) {
            x = Integer.valueOf(s.substring(0, 1));
            y = Integer.valueOf(s.substring(2, 3));
        } else {
            x = 0;
            y = 0;
        }
    }

    @Override
    public String toString() {
        return  x + " " + y;
    }
}
