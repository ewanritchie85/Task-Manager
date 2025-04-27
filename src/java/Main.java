import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<Event> events = new ArrayList<>();
        Util util = new Util();

        int choice = util.getChoice(input);
        while (choice != 3) {
            switch (choice) {
                case 1:
                    // Show events list
                    util.showEventsList(events);
                    break;   
                case 2: 
                    // Add new event
                    Event event = new Event();
                    event.addEvent(input);
                    event.addReminder(input);
                    events.add(event);
                    break;  
                default:  
                    System.out.println("Invalid choice. Please try again.");
                    break; 
                    
            }
            choice = util.getChoice(input);
        
        }
        input.close();
    }
}