import java.util.Scanner;
import java.util.List;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Util {
    String CSVFile = "events.csv";

    public Util() {
    }

    public void showEventsList(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events scheduled.");
        } else {
            System.out.println("Events list:");
            for (Event e : events) {
                System.out.println("- " + e.getDate() + " : " + e.getName());

            }
        }
    }

    public int getChoice(Scanner input) {
        System.out.println("Please choose an option:");
        System.out.println("1. Show events list");
        System.out.println("2. Add new event");
        System.out.println("3. Exit");
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    public Boolean chooseAgain(Scanner input) {
        System.out.println("Do you want to add another event? (y/n)");
        char choice = input.nextLine().charAt(0);
        if (choice == 'y' || choice == 'Y') {
            return true;
        }
        return false;
    }

    public void writeToCSV(Event event) {

        boolean fileExists = new java.io.File(CSVFile).exists();

        try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFile, true))) {
            if (!fileExists) {
                String[] header = { "Event Name", "Event Date", "Reminder Date" };
                writer.writeNext(header);
            }

            String[] data = {
                    event.getName(),
                    event.getDate().toString(),
                    event.getReminderDate().toString()
            };
            writer.writeNext(data);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    public void readFromCSV() {
        // Implement reading from CSV if needed
    }

}
