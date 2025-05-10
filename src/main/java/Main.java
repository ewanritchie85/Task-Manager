import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        Util util = new Util();

        int choice = util.getChoice(input);
        while (choice != 4) {
            switch (choice) {
                case 1:
                    util.printFromCSV();
                    break;
                case 2:
                    Event newEvent = new Event();
                    newEvent.addEvent(input);
                    newEvent.addReminder(input);
                    util.writeToCSV(newEvent);
                    break;
                case 3:
                    Event eventToRemove = new Event();
                    eventToRemove.removeEvent(input);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }
            if (util.chooseAgain(input)) {
                choice = util.getChoice(input);
            } else {
                util.printFromCSV();
                System.out.println("Goodbye...");
                break;
            }

        }
        input.close();
    }
}