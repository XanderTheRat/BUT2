package rover;

import map.Direction;

public class Rover {
    private Direction direction;

    private int x;
    private int y;
    private int mapSize;

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
        this.mapSize = mapSize;
    }

    //TODO: Can't move if rover encounters an obstacle and report it
    public void moveToNorth() {
        if (x+DISTANCE >= mapSize) {
            x = START_X;
        } else {
            x += DISTANCE;
        }
    }

    public void moveToSouth() {
        if (x < START_X) {
            x = mapSize - DISTANCE;
        } else {
            x -= DISTANCE;
        }
    }

    public void moveToEast() {
        if (y+DISTANCE >= mapSize) {
            y = START_Y;
        }  else {
            y += DISTANCE;
        }
    }

    public void moveToWest() {
        if (y-DISTANCE < START_Y) {
            y = mapSize - DISTANCE;
        }  else {
            y -= DISTANCE;
        }
    }

    public void turnLeft() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            default:
                direction = Direction.NORTH;
                break;
        }
    }

    public void turnRight() {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            default:
                direction = Direction.NORTH;
        }
    }

    public int x() {
        return x;
    }
    public int y() {
        return y;
    }

    public String direction() {
        return direction.direction();
    }
}
