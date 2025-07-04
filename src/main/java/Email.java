import java.util.List;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Email {
    Util util = new Util();
    Csv csv = new Csv();

    public void sendReminderEmails() throws IOException {
        System.out.println("sendReminderEmails() called");
        List<String[]> events = csv.readFromCSV();
        System.out.println("Loaded " + events.size() + " events from CSV.");
        ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC).withSecond(0).withNano(0);
        Properties credentials = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("email.properties");
        if (input == null) {
            // Fallback: try loading from filesystem for Docker mount
            try {
                java.io.File file = new java.io.File("/app/email.properties");
                if (file.exists()) {
                    input = new java.io.FileInputStream(file);
                    System.out.println("Loaded email.properties from /app/email.properties");
                } else {
                    System.err.println("email.properties NOT FOUND on classpath or /app/email.properties!");
                    return;
                }
            } catch (Exception ex) {
                System.err.println("Error loading email.properties from /app/email.properties");
                ex.printStackTrace();
                return;
            }
        } else {
            System.out.println("email.properties loaded successfully from classpath");
        }
        credentials.load(input);

        String recipient = credentials.getProperty("email.recipient");
        String username = credentials.getProperty("email.username");
        String password = credentials.getProperty("email.password");

        for (String[] event : events) {
            System.out.println("Raw event row: " + java.util.Arrays.toString(event));

            if (event[2].equalsIgnoreCase("Reminder Date")) {
                continue;
            }
            try {
                String eventName = event[0];
                String eventDate = event[1];
                String reminderDateStr = event[2];
                ZonedDateTime reminderDate = ZonedDateTime.parse(reminderDateStr);
                System.out.println("Now: " + now + " | Reminder: " + reminderDate.withSecond(0).withNano(0));
                System.out.println("Checking event: " + eventName + ", reminder at: " + reminderDate);

                if (!reminderDate.isAfter(now) && !reminderDate.isBefore(now.minusMinutes(1))) {
                    System.out.println("Sending reminder for event: " + eventName + " at " + reminderDate);
                    sendEmail(
                            recipient,
                            "Reminder: " + eventName,
                            "Your event \"" + eventName + "\" is scheduled at " + eventDate,
                            username,
                            password);
                }
            } catch (Exception e) {
                System.out.println("Error parsing reminder date for event: " + event[0]);
                e.printStackTrace();
            }
        }
        System.out.println("Reminder check complete.");
    }

    // Email sending method using Jakarta Mail
    public void sendEmail(String to, String subject, String body, String username, String password)
            throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
        System.out.println("Email sent to " + to);
    }

}
