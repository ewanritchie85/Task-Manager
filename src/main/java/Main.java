import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Util util = new Util();
        Email email = new Email();

        startReminderThread(email);

        int choice = util.getChoice(input);
        while (choice != 4) {
            handleUserChoice(choice, input, util);

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

    private static void handleUserChoice(int choice, Scanner input, Util util) {
        switch (choice) {
            case 1:
                util.printFromCSV();
                break;
            case 2:
                Event newEvent = new Event();
                Reminder newReminder = new Reminder();
                newEvent.addEvent(input);
                newReminder.setDate(newEvent.getDate());
                newReminder.addReminder(input);
                util.writeToCSV(newEvent, newReminder);
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