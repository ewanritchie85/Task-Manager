import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Event {
    Util util = new Util();
    private String name;
    private LocalDateTime date;
    private LocalDateTime reminderDate;
    private LocalDateTime dateTime;

    public Event() {
    }

    public void addEvent(Scanner input) {
        while (true) {
            System.out.println("Enter event name:");
            String nameInput = input.nextLine().trim();
            System.out.println("Enter event date and time (YYYY/MM/DD hh:mm):");
            String dateInput = input.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                dateTime = LocalDateTime.parse(dateInput, formatter);

                System.out.println(nameInput + " on " + dateInput);
                System.out.println("Is this correct? (y/n)");
                char confirm = input.nextLine().charAt(0);
                if (confirm == 'y' || confirm == 'Y') {
                    this.name = nameInput;
                    this.date = dateTime;
                    System.out.println("Event added: " + this.name + " on " + this.date);
                    break;
                } else {
                    System.out.println("Event not added. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    public void addReminder(Scanner input) {
        while (true) {
            System.out.println("Enter reminder date and time (YYYY/MM/DD hh:mm):");
            String reminderDateInput = input.nextLine();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
                dateTime = LocalDateTime.parse(reminderDateInput, formatter);

                System.out.println("Reminder date: " + reminderDateInput);
                System.out.println("Is this correct? (y/n)");
                char confirm = input.nextLine().charAt(0);
                if (confirm == 'y' || confirm == 'Y') {
                    if (this.date != null && dateTime.isBefore(this.date) && dateTime.isAfter(LocalDateTime.now())) {
                        this.reminderDate = dateTime;
                        System.out.println("Reminder added");
                        break;
                    } else {
                        System.out.println("Reminder must be before the event date. Please try again.");
                    }
                } else {
                    System.out.println("Reminder not added. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public LocalDateTime getReminderDate() {
        return this.reminderDate;
    }

    public void removeEvent(Scanner input) {
        System.out.println("Enter name of event to remove:");
        String eventToRemove = input.nextLine();
        List<String[]> events = util.readFromCSV();
        List<String[]> updatedEvents = new java.util.ArrayList<>();

        for (String[] event : events) {
            if (!event[0].equals(eventToRemove)) {
                updatedEvents.add(event);
            } else {
                System.out.println("Event removed: " + eventToRemove);
            }
        }

        util.overwriteCSV(updatedEvents);
    }

}