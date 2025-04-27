import java.util.Scanner;
public class Event {
    private String name;
    private String date;
    private String reminderDate;

 
    public Event(String name, String date, String reminderDate) {
        this.name = name;
        this.date = date;
        this.reminderDate = reminderDate;
    }

    public Event() {
        // Empty constructor
    }


    public void addEvent(Scanner input) {
        
        System.out.println("Enter event name:");
        String nameInput = input.nextLine();
        System.out.println("Enter event date (YYYY-MM-DD-hh-mm):");
        String dateInput = input.nextLine();
        System.out.println(nameInput + " on " + dateInput);
        System.out.println("Is this correct? (y/n)");
        char confirm = input.nextLine().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            this.name = nameInput;
            this.date = dateInput;
            System.out.println("Event added: " + this.name + " on " + this.date);
        } else {
            System.out.println("Event not added.");
        }
    }

    public void addReminder(Scanner input) {
        System.out.println("Enter reminder date and time (YYYY-MM-DD-hh-mm):");
        String reminderDateInput = input.nextLine();
        System.out.println("Reminder date: " + reminderDateInput);
        System.out.println("Is this correct? (y/n)");
        char confirm = input.nextLine().charAt(0);
        if (confirm == 'y' || confirm == 'Y') {
            this.reminderDate = reminderDateInput;
            System.out.println("Reminder added successfully!");
        } else {
            System.out.println("Reminder not added.");
        }
    }
    public String getName() {
        return this.name;
    }
    
    public String getDate() {
        return this.date;
    }
}