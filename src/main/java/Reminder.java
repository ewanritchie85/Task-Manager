import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Reminder {

    Util util = new Util();
    private LocalDateTime date;
    private LocalDateTime reminderDate;

    public Reminder() {
    }

    private LocalDateTime promptForReminderDateTime(Scanner input) throws Exception {
        System.out.println("Enter reminder date and time (YYYY/MM/DD hh:mm):");
        String reminderDateInput = input.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return LocalDateTime.parse(reminderDateInput, formatter);
    }

    private boolean confirmReminder(LocalDateTime reminderDate, Scanner input) {
        System.out.println("Reminder date: " + reminderDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        System.out.println("Is this correct? (y/n)");
        char confirm = input.nextLine().charAt(0);
        return confirm == 'y' || confirm == 'Y';
    }

    public void setDate(LocalDateTime date) {
    this.date = date;
}

    public void addReminder(Scanner input) {
        while (true) {
            try {
                LocalDateTime reminderDateTime = promptForReminderDateTime(input);

                if (confirmReminder(reminderDateTime, input)) {
                    if (this.date != null && reminderDateTime.isBefore(this.date)
                            && reminderDateTime.isAfter(LocalDateTime.now())) {
                        this.reminderDate = reminderDateTime;
                        System.out.println("Reminder added");
                        break;
                    } else {
                        System.out.println(
                                "Reminder must be before the event date and after the current time. Please try again.");
                    }
                } else {
                    System.out.println("Reminder not added. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    public LocalDateTime getReminderDate() {
        return this.reminderDate;
    }
}
