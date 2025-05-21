import java.util.Scanner;
import java.util.List;
import java.time.LocalDateTime;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;

public class Util {
    String CSVFile = "events.csv";

    public Util() {
    }

    public int getChoice(Scanner input) {
        System.out.println("Please choose an option:");
        System.out.println("1. Show events list");
        System.out.println("2. Add new event");
        System.out.println("3. Delete event");
        System.out.println("4. Exit");
        int choice = input.nextInt();
        input.nextLine();
        return choice;
    }

    public Boolean chooseAgain(Scanner input) {
        while (true){
            try{
                System.out.println("Do you want to do anything else? (y/n)");
                char choice = input.nextLine().charAt(0);
                if (choice == 'y' || choice == 'Y') {
                    return true;
                } else if (choice == 'n' || choice == 'N') {
                    System.out.println("Goodbye...");
                    return false;
                }
                else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

    public void writeToCSV(Event event, Reminder reminder) {
        java.io.File file = new java.io.File(CSVFile);
        boolean hasHeader = file.exists() && file.length() > 0;

        try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
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
            // add verification etc.
            for (String[] event : events) {
                writer.writeNext(event);
            }
        } catch (IOException e) {
            System.err.println("Error overwriting CSV: " + e.getMessage());
        }
    }

    public LocalDateTime getLocalDateTime(){
        return LocalDateTime.now();
    }

}

    