import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Event> events = new ArrayList<>();
        Util util = new Util();

        int choice = util.getChoice(input);
        while (choice != 3) {
            switch (choice) {
                case 1:
                    util.showEventsList(events);
                    break;
                case 2:
                    Event event = new Event();
                    event.addEvent(input);
                    event.addReminder(input);
                    // change this to add to a file for persistence
                    util.writeToCSV(event);
                    events.add(event);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;

            }
            if (util.chooseAgain(input)) {
                choice = util.getChoice(input);
            } else {
                util.showEventsList(events);
                System.out.println("Exiting...");
                break;
            }

        }
        input.close();
    }
}