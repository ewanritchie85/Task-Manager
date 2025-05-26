
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Csv {
    String CSVFile = System.getProperty("user.home") + "/task-manager-data/events.csv";
    private final java.io.File file;

    public Csv() {
        this.file = new java.io.File(CSVFile);
        file.getParentFile().mkdirs();
    }

    private final String[] header = { "Event Name", "Event Date", "Reminder Date" };

    public void writeToCSV(Event event, Reminder reminder) {
        boolean hasHeader = file.exists() && file.length() > 0;

        try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFile, true))) {
            if (!hasHeader) {
                writer.writeNext(header);
            }

            String[] data = {
                    event.getName(),
                    event.getDate().toString(),
                    reminder.getReminderDate().toString()
            };
            writer.writeNext(data);
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }
    }

    public List<String[]> readFromCSV() {
        if (!file.exists()) {
            return new java.util.ArrayList<>();
        }
        try (CSVReader reader = new CSVReader(new FileReader(CSVFile))) {
            return reader.readAll();
        } catch (IOException e) {
            System.err.println("Error reading from CSV: " + e.getMessage());
            return new java.util.ArrayList<>();
        }
    }

    public void printFromCSV() {
        List<String[]> events = readFromCSV();
        for (String[] event : events) {
            for (String cell : event) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }

    }

    public void overwriteCSV(List<String[]> events) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFile))) {
            writer.writeNext(header);
            for (String[] event : events) {
                writer.writeNext(event);
            }
        } catch (IOException e) {
            System.err.println("Error overwriting CSV: " + e.getMessage());
        }
    }

    public void removePastEvents() {
    List<String[]> events = readFromCSV();
    List<String[]> futureEvents = new java.util.ArrayList<>();

    // Always keep the header row
    if (!events.isEmpty() && events.get(0).length > 0 && events.get(0)[0].equals("Event Name")) {
        futureEvents.add(events.get(0));
    }

    for (int i = 1; i < events.size(); i++) {
        String[] event = events.get(i);
        // Check for enough columns
        if (event.length < 2) continue;

        try {
            LocalDateTime eventDate = LocalDateTime.parse(event[1]);
            if (eventDate.isAfter(LocalDateTime.now())) {
                futureEvents.add(event);
            }
        } catch (DateTimeParseException e) {
            // Only warn if it's not the header
            if (!event[1].equalsIgnoreCase("Event Date")) {
                System.err.println("Skipping invalid date row: " + String.join(",", event));
            }
        }
    }
    overwriteCSV(futureEvents);
}
}