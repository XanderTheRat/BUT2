package map;

import rover.Rover;

import java.util.ArrayList;

public class Map {
    Rover rover;
    int[][] map;
    ArrayList<Obstacle> obstacles;

    public Map(int nbTile) {
        map = new int[nbTile][nbTile];
        rover = new Rover(nbTile);
    }

    // TODO : Refacto the moving methods into a new Controller class
    public void moveRoverForward() {
        switch (rover.direction()) {
            case "N" :
                rover.moveToNorth();
                break;
            case "S" :
                rover.moveToSouth();
                break;
            case "W" :
                rover.moveToWest();
                break;
            case "E" :
                rover.moveToEast();
                break;
            default :
                System.out.println("Invalid direction");
        }
    }

    public void moveRoverBackward() {
        switch (rover.direction()) {
            case "S" :
                rover.moveToNorth();
                break;
            case "N" :
                rover.moveToSouth();
                break;
            case "E" :
                rover.moveToWest();
                break;
            case "W" :
                rover.moveToEast();
                break;
            default :
                System.out.println("Invalid direction");
        }
    }

    public void moveRoverLeft() {
        rover.turnLeft();
    }

    public void moveRoverRight() {
        rover.turnRight();
    }

    public Rover rover() {
        return rover;
    }

    public void addObstacle(Obstacle obstacle) {
        int x = obstacle.x();
        int y = obstacle.y();

        // TODO : throw an error if an obstcle already exists
        if (x == rover.x() &&  y == rover.y()) {
            // TODO : implement a comportment when a obstacle destroy the rover
            System.out.println("The rover is on the obstacle !!");
        }
        else {
            obstacles.add(obstacle);
        }
    }

}
