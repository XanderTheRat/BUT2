package rover;

public class RoverController {
    Rover rover;

    public RoverController(Rover rover) {
        this.rover = rover;
    }

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
}
