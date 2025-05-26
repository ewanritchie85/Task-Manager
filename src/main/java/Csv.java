import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Csv {
    String CSVFile = "/home/pi/task-manager-data/events.csv";
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
            // Convert event and reminder LocalDateTime (assumed in system default time
            // zone) to UTC and write as ISO string
            ZonedDateTime eventUtc = event.getDate().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
            ZonedDateTime reminderUtc = reminder.getReminderDate().atZone(ZoneId.systemDefault())
                    .withZoneSameInstant(ZoneOffset.UTC);
            String[] data = {
                    event.getName(),
                    eventUtc.toString(),
                    reminderUtc.toString()
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
        boolean isHeader = true;
        for (String[] event : events) {
            if (isHeader) {
                for (String cell : event) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
                isHeader = false;
                continue;
            }
            // Parse event and reminder dates as UTC and convert to system default time zone for display,
            // fallback to raw string if parsing fails
            String eventName = event[0];
            String eventDateStr = event[1];
            String reminderDateStr = event[2];
            String displayEventDate;
            String displayReminderDate;
            try {
                displayEventDate = ZonedDateTime.parse(eventDateStr).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString();
            } catch (Exception e) {
                System.err.println("Error parsing event date: " + e.getMessage());
                displayEventDate = eventDateStr;
            }
            try {
                displayReminderDate = ZonedDateTime.parse(reminderDateStr).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString();
            } catch (Exception e) {
                System.err.println("Error parsing reminder date: " + e.getMessage());
                displayReminderDate = reminderDateStr;
            }
            System.out.print(eventName + "\t" + displayEventDate + "\t" + displayReminderDate + "\t");
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

        // Keep the header row always
        if (!events.isEmpty() && events.get(0).length > 0 && events.get(0)[0].equals("Event Name")) {
            futureEvents.add(events.get(0));
        }

        for (int i = 1; i < events.size(); i++) {
            String[] event = events.get(i);
            if (event.length < 2) {
                continue;
            }
            try {
                // Parse event date as UTC ZonedDateTime and compare with current UTC time
                ZonedDateTime eventDateUtc = ZonedDateTime.parse(event[1]);
                if (eventDateUtc.isAfter(ZonedDateTime.now(ZoneOffset.UTC))) {
                    futureEvents.add(event);
                }
            } catch (DateTimeParseException e) {
                System.err.println("Skipping invalid date row: " + String.join(",", event) + " - " + e.getMessage());
            }
        }
        overwriteCSV(futureEvents);
    }
}