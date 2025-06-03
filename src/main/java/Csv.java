import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ArrayList;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Csv {
    private static final String DEFAULT_CSV_FILE = "/home/pi/task-manager-data/events.csv";
    private final String CSVFile;
    private final java.io.File file;
    private final String[] header = { "Event Name", "Event Date", "Reminder Date" };

    public Csv() {
        // Allow override by environment variable
        String csvPath = System.getenv("EVENTS_CSV");
        this.CSVFile = (csvPath != null && !csvPath.isEmpty()) ? csvPath : DEFAULT_CSV_FILE;
        this.file = new java.io.File(CSVFile);
        file.getParentFile().mkdirs();
    }

    /**
     * Writes a new event+reminder to the CSV, storing all datetimes in UTC.
     */
    public void writeToCSV(Event event, Reminder reminder) {
        boolean hasHeader = file.exists() && file.length() > 0;
        try (
                CSVWriter writer = new CSVWriter(
                        new OutputStreamWriter(new FileOutputStream(CSVFile, true), StandardCharsets.UTF_8))) {
            if (!hasHeader) {
                writer.writeNext(header);
            }
            ZonedDateTime eventUtc = event.getDateAsUtc();
            ZonedDateTime reminderUtc = reminder.getReminderDate().withZoneSameInstant(ZoneOffset.UTC);
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

    /**
     * Reads all events from the CSV, trimming all fields for whitespace.
     */
    public List<String[]> readFromCSV() {
        List<String[]> rows = new ArrayList<>();
        if (!file.exists()) {
            return rows;
        }
        try (
                CSVReader reader = new CSVReader(
                        new InputStreamReader(new FileInputStream(CSVFile), StandardCharsets.UTF_8))) {
            for (String[] row : reader) {
                for (int i = 0; i < row.length; i++) {
                    if (row[i] != null)
                        row[i] = row[i].trim();
                }
                rows.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading from CSV: " + e.getMessage());
        }
        return rows;
    }

    /**
     * Pretty-prints all events in the CSV, converting UTC to local time zone for
     * display.
     */
    public void printFromCSV() {
        List<String[]> events = readFromCSV();
        boolean isHeader = true;
        for (String[] event : events) {
            if (isHeader) {
                System.out.println(String.join("\t", event));
                isHeader = false;
                continue;
            }
            String eventName = event[0];
            String eventDateStr = event[1];
            String reminderDateStr = event[2];
            String displayEventDate, displayReminderDate;
            try {
                displayEventDate = ZonedDateTime.parse(eventDateStr)
                        .withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString();
            } catch (Exception e) {
                System.err.println("Error parsing event date: " + e.getMessage());
                displayEventDate = eventDateStr;
            }
            try {
                displayReminderDate = ZonedDateTime.parse(reminderDateStr)
                        .withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().toString();
            } catch (Exception e) {
                System.err.println("Error parsing reminder date: " + e.getMessage());
                displayReminderDate = reminderDateStr;
            }
            System.out.println(eventName + "\t" + displayEventDate + "\t" + displayReminderDate);
        }
    }

    /**
     * Overwrites the CSV with the given list of events (including header).
     */
    public void overwriteCSV(List<String[]> events) {
        try (
                CSVWriter writer = new CSVWriter(
                        new OutputStreamWriter(new FileOutputStream(CSVFile), StandardCharsets.UTF_8))) {
            writer.writeNext(header);
            for (String[] event : events) {
                if (event[0].equals("Event Name"))
                    continue;
                writer.writeNext(event);
            }
        } catch (IOException e) {
            System.err.println("Error overwriting CSV: " + e.getMessage());
        }
    }

    /**
     * Removes events that are in the past (based on UTC), keeping the header.
     */
    public void removePastEvents() {
        List<String[]> events = readFromCSV();
        List<String[]> futureEvents = new ArrayList<>();
        for (int i = 1; i < events.size(); i++) {
            String[] event = events.get(i);
            if (event[0].equals("Event Name"))
                continue;
            if (event.length < 2)
                continue;
            try {
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