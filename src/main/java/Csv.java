
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class Csv {
    String CSVFile = System.getProperty("user.home") + "/task-manager-data/events.csv";

    public Csv() {
        java.io.File file = new java.io.File(CSVFile);
        file.getParentFile().mkdirs();
    }

    public void writeToCSV(Event event, Reminder reminder) {
        java.io.File file = new java.io.File(CSVFile);
        boolean hasHeader = file.exists() && file.length() > 0;

        try (CSVWriter writer = new CSVWriter(new FileWriter(CSVFile, true))) {
            if (!hasHeader) {
                String[] header = { "Event Name", "Event Date", "Reminder Date" };
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
        java.io.File file = new java.io.File(CSVFile);
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
            String[] header = { "Event Name", "Event Date", "Reminder Date" };
            writer.writeNext(header);
            for (String[] event : events) {
                writer.writeNext(event);
            }
        } catch (IOException e) {
            System.err.println("Error overwriting CSV: " + e.getMessage());
        }
    }

}