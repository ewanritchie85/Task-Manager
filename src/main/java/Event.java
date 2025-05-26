import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Event {
    Util util = new Util();
    Csv csv = new Csv();
    private String name;
    private LocalDateTime date;

    public Event() {
    }

    private String promptForEventName(Scanner input) {
        System.out.println("Enter event name:");
        return input.nextLine().trim();
    }

    private LocalDateTime promptForEventDateTime(Scanner input) {
        System.out.println("Enter event date and time (YYYY/MM/DD hh:mm):");
        String dateInput = input.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        return LocalDateTime.parse(dateInput, formatter);
    }

    private boolean confirmEventDetails(String name, LocalDateTime date, Scanner input) {
        System.out.println(name + " on " + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        System.out.println("Is this correct? (y/n)");
        char confirm = input.nextLine().charAt(0);
        return confirm == 'y' || confirm == 'Y';
    }

    public void addEvent(Scanner input) {
        while (true) {
            try {
                String nameInput = promptForEventName(input);
                LocalDateTime eventDateTime = promptForEventDateTime(input);
                if (eventDateTime.isBefore(LocalDateTime.now())) {
                    System.out.println("Event date must be in the future. Please try again.");
                    continue;
                }

                if (confirmEventDetails(nameInput, eventDateTime, input)) {
                    this.name = nameInput;
                    this.date = eventDateTime;
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

    
    public String getName() {
        return this.name;
    }

    public LocalDateTime getDate() {
        return this.date;
    }



    public void removeEvent(Scanner input) {
        System.out.println("Enter name of event to remove:");
        String eventToRemove = input.nextLine();
        List<String[]> events = csv.readFromCSV();
        List<String[]> updatedEvents = new java.util.ArrayList<>();

        for (String[] event : events) {
            if (!event[0].equals(eventToRemove)) {
                updatedEvents.add(event);
            } else {
                System.out.println("Are you sure you want to delete " + event[0] + " ? (y/n)");
                char confirm = input.nextLine().charAt(0);
                if (confirm == 'y' || confirm == 'Y') {
                    System.out.println("Event removed: " + eventToRemove);
                } else {
                    updatedEvents.add(event);
                    System.out.println("Deletion cancelled.");
                }
            }
        }

        csv.overwriteCSV(updatedEvents);
    }

}