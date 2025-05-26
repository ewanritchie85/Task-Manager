public class MainDaemon {
    public static void main(String[] args) {
        Csv csv = new Csv();
        Email email = new Email();

        // Start reminder thread
        new Thread(() -> {
            while (true) {
                try {
                    email.sendReminderEmails();
                    Thread.sleep(60000); // 1 minute
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Start cleanup thread
        new Thread(() -> {
            while (true) {
                try {
                    csv.removePastEvents();
                    Thread.sleep(24 * 60 * 60 * 1000); // 24 hours
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Keep main thread alive forever
        try {
            while (true) {
                Thread.sleep(60000);
            }
        } catch (InterruptedException e) {
            // Exit cleanly on interruption
        }
    }
}
