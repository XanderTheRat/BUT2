package map;

import rover.Rover;
import rover.RoverController;

import java.util.ArrayList;

public class Map {
    RoverController rovercontroller;
    int[][] map;
    ArrayList<Obstacle> obstacles;

    public Map(int nbTile) {
        map = new int[nbTile][nbTile];
        rovercontroller = new RoverController(new Rover(nbTile));
    }

    public void addObstacle(Obstacle obstacle) {
        int x = obstacle.x();
        int y = obstacle.y();

        // TODO : throw an error if an obstcle already exists
        if (x == rovercontroller.rover().x() &&  y == rovercontroller.rover().y()) {
            // TODO : implement a comportment when a obstacle destroy the rover
            System.out.println("The rover is on the obstacle !!");
        }
        else {
            obstacles.add(obstacle);
        }
    }

    public RoverController roverControler() {
        return rovercontroller;
    }

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }
}
