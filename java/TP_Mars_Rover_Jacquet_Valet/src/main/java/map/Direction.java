package map;

public enum Direction {
    SOUTH("S"),
    NORTH("N"),
    EAST("E"),
    WEST("W");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String direction() {
        return direction;
    }


}
