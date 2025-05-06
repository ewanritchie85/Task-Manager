import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Util util = new Util();

        int choice = util.getChoice(input);
        while (choice != 3) {
            switch (choice) {
                case 1:
                    util.printFromCSV();
                    break;
                case 2:
                    Event event = new Event();
                    event.addEvent(input);
                    event.addReminder(input);
                    util.writeToCSV(event);
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