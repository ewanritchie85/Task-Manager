import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Util util = new Util();
        Csv csv = new Csv();
        Email email = new Email();

        startReminderThread(email);
        startCleanupThread(csv);

        int choice = util.getChoice(input);
        while (choice != 4) {
            handleUserChoice(choice, input, csv);

            if (util.chooseAgain(input)) {
                choice = util.getChoice(input);
            } else {
                csv.printFromCSV();
                System.out.println("Goodbye...");
                break;
            }
        }
        input.close();
    }

    private static void startReminderThread(Email email) {
        new Thread(() -> {
            while (true) {
                try {
                    email.sendReminderEmails();
                    Thread.sleep(60000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void startCleanupThread(Csv csv) {
    new Thread(() -> {
        while (true) {
            try {
                csv.removePastEvents();
                Thread.sleep(24 * 60 * 60 * 1000); // sleep 24 hours
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }).start();
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