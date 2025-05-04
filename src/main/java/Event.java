import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event {
    private String name;
    private LocalDateTime date;
    private LocalDateTime reminderDate;
    private LocalDateTime dateTime;

    public Event() {
    }

    public void addEvent(Scanner input) {

        System.out.println("Enter event name:");
        String nameInput = input.nextLine();
        System.out.println("Enter event date and time(YYYY/MM/DD hh:mm):");
        String dateInput = input.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            dateTime = LocalDateTime.parse(dateInput, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(nameInput + " on " + dateInput);
        System.out.println("Is this correct? (y/n)");
        char confirm = input.nextLine().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            this.name = nameInput;
            this.date = dateTime;
            System.out.println("Event added: " + this.name + " on " + this.date);
        } else {
            System.out.println("Event not added.");
        }
    }

    public void addReminder(Scanner input) {
        System.out.println("Enter reminder date and time (YYYY/MM/DD hh:mm):");
        String reminderDateInput = input.nextLine();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            dateTime = LocalDateTime.parse(reminderDateInput, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Reminder date: " + reminderDateInput);
        System.out.println("Is this correct? (y/n)");
        char confirm = input.nextLine().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            this.reminderDate = dateTime;
            System.out.println("Reminder added successfully!");
        } else {
            System.out.println("Reminder not added.");
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

}