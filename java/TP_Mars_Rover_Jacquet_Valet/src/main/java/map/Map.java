package map;

import rover.Rover;

import java.util.HashMap;

public class Map {
    Rover rover;
    int[][] map;

    Map(int nbTile) {
        map = new int[nbTile][nbTile];
        rover = new Rover(nbTile);
    }


}
