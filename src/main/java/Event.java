import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Event {
    Util util = new Util();
    Csv csv = new Csv();
    private String name;
    private ZonedDateTime zonedDate;

    public Event() {
    }

    public ZonedDateTime getDateAsUtc() {
        return this.zonedDate.withZoneSameInstant(ZoneOffset.UTC);
    }

    private String promptForEventName(Scanner input) {
        System.out.println("Enter event name:");
        return input.nextLine().trim();
    }

    private ZonedDateTime promptForEventDateTime(Scanner input) {
        System.out.println("Enter event date and time (YYYY/MM/DD hh:mm):");
        String dateInput = input.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime localEventDate = LocalDateTime.parse(dateInput, formatter);
        ZoneId localZone = ZoneId.of("Europe/London");
        return localEventDate.atZone(localZone);
    }

    private boolean confirmEventDetails(String name, ZonedDateTime date, Scanner input) {
        System.out.println(name + " on " + date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        System.out.println("Is this correct? (y/n)");
        char confirm = input.nextLine().charAt(0);
        return confirm == 'y' || confirm == 'Y';
    }

    public void addEvent(Scanner input) {
    while (true) {
        try {
            String nameInput = promptForEventName(input);
            ZonedDateTime eventDateTime = promptForEventDateTime(input);
            if (eventDateTime.isBefore(ZonedDateTime.now(ZoneId.of("Europe/London")))) {
                System.out.println("Event date must be in the future. Please try again.");
                continue;
            }
            if (confirmEventDetails(nameInput, eventDateTime, input)) {
                this.zonedDate = eventDateTime;
                this.name = nameInput;
                System.out.println("Event added: " + this.name + " on " +
                    this.zonedDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
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
        return this.zonedDate.toLocalDateTime();
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