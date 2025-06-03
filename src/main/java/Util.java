import java.util.Scanner;
import java.time.LocalDateTime;

public class Util {

    public Util() {
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
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
        while (true) {
            try {
                System.out.println("Do you want to do anything else? (y/n)");
                char choice = input.nextLine().charAt(0);
                if (choice == 'y' || choice == 'Y') {
                    return true;
                } else if (choice == 'n' || choice == 'N') {
                    return false;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter 'y' or 'n'.");
            }
        }
    }

}