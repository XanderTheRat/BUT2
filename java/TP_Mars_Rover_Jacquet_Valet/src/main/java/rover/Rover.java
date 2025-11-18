package rover;

import map.Direction;

import java.awt.*;

public class Rover {
    private Direction direction;

    private int x;
    private int y;

    public Rover() {
        x= 0;
        y = 0;

        direction = Direction.NORTH;
    }
}
