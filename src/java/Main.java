import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // give options to the user to show events list
        // or add new event along with the date and reminder date
        System.out.println("Welcome to the Event Manager!");
        System.out.println("Please choose an option:");
        System.out.println("1. Show events list");
        System.out.println("2. Add new event");
        System.out.println("3. Exit");
        int choice = input.nextInt();
        input.nextLine(); // consume the newline character
        while (choice != 3) {
            switch (choice) {
                case 1:
                    // Show events list
                    System.out.println("Events list:"); 
                    // Here you would normally fetch and display the events from a database or a file
                    // For now, we'll just print a placeholder message
                    System.out.println("No events available.");
                    break;
                case 2: 
                    // Add new event
                    System.out.println("Enter event name:");
                    String eventName = input.nextLine();
                    System.out.println("Enter event date (YYYY-MM-DD):");
                    String eventDate = input.nextLine();
                    System.out.println("Event added: " + eventName + " on " + eventDate);
                    // Here you would normally save the new event to a database or a file
                    break;  
                default:   
            }
                

        input.close();
    }
    }
}