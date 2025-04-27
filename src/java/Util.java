import java.util.Scanner;
import java.util.List;

public class Util {
    public Util(){
        // Constructor
    }

    public int getChoice(Scanner input){
        System.out.println("Please choose an option:");
        System.out.println("1. Show events list");
        System.out.println("2. Add new event");
        System.out.println("3. Exit");
        int choice = input.nextInt();
        input.nextLine(); 
        return choice;
    } 

    public void showEventsList(List<Event> events){
        if (events.isEmpty()) {
            System.out.println("No events scheduled.");
        } else {
            System.out.println("Events list:");
            for (Event e : events) {
                System.out.println("- "+ e.getDate()+ " : " + e.getName() );
                
            }
        }
    }



}
