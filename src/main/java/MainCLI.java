import java.util.Scanner;

public class MainCLI {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Util util = new Util();
        Csv csv = new Csv();

        int choice = util.getChoice(input);
        while (choice != 4) {
            handleUserChoice(choice, input, csv);

            if (util.chooseAgain(input)) {
                choice = util.getChoice(input);
            } else {
                System.out.println("Goodbye...");
                break;
            }
        }
        input.close();
    }

    private static void handleUserChoice(int choice, Scanner input, Csv csv) {
        switch (choice) {
            case 1:
                csv.printFromCSV();
                break;
            case 2:
                Event newEvent = new Event();
                Reminder newReminder = new Reminder();
                newEvent.addEvent(input);
                newReminder.setDate(newEvent.getDate());
                newReminder.addReminder(input);
                csv.writeToCSV(newEvent, newReminder);
                break;
            case 3:
                Event eventToRemove = new Event();
                eventToRemove.removeEvent(input);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }
}