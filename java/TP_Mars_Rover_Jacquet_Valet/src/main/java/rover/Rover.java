package rover;

import map.Direction;

import java.awt.*;

public class Rover {
    private Direction direction;

    private int x;
    private int y;

    private int MAP_SIZE;
    private final int START_X = 0;
    private final int START_Y = 0;
    private final int DISTANCE = 1;

    private Rover() {
        x= START_X;
        y = START_Y;

        direction = Direction.NORTH;
    }

    public Rover(int mapSize) {
        this();
        MAP_SIZE = mapSize;
    }

    public void moveToNorth() {
        if (x+DISTANCE >= MAP_SIZE) {
            x = START_X;
        } else {
            x += DISTANCE;
        }
    }

    public void moveToSouth() {
        if (x < START_X) {
            x = MAP_SIZE - DISTANCE;
        } else {
            x -= DISTANCE;
        }
    }

    public void moveToEast() {
        if (y+DISTANCE >= MAP_SIZE) {
            y = START_Y;
        }  else {
            y += DISTANCE;
        }
    }

    public void moveToWest() {
        if (y-DISTANCE < START_Y) {
            y = MAP_SIZE - DISTANCE;
        }  else {
            y -= DISTANCE;
        }
    }
}
