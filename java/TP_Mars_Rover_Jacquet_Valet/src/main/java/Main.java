import map.Map;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter commands : ");
        System.out.print("$ ");
        String commands = scanner.nextLine();
        Map map = new Map(100);

        for  (char command : commands.toCharArray()) {
            switch (command) {
                case 'f' :
                    map.roverControler().moveRoverForward();
                    break;
                case 'b' :
                    map.roverControler().moveRoverBackward();
                    break;
                case 'l' :
                    map.roverControler().moveRoverLeft();
                    break;
                case 'r' :
                    map.roverControler().moveRoverRight();
                    break;
                default :
                    System.out.println("Invalid command");
            }
        }

        System.out.println("Rover at positions (" + map.roverControler().rover().x() + ":" + map.roverControler().rover().y() + ").");
    }
}

