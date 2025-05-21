import java.time.LocalDateTime;
import java.util.List;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Email {
    Util util = new Util();

    public void sendReminderEmails() throws IOException {
        List<String[]> events = util.readFromCSV();
        LocalDateTime now = util.getLocalDateTime().withSecond(0).withNano(0);

        Properties credentials = new Properties();
        InputStream input = getClass().getClassLoader().getResourceAsStream("email.properties");
        credentials.load(input);

        String recipient = credentials.getProperty("email.recipient");
        String username = credentials.getProperty("email.username");
        String password = credentials.getProperty("email.password");

        for (String[] event : events) {
            if (event[2].equalsIgnoreCase("Reminder Date")) {
                continue; 
            }
            try {
                String eventName = event[0];
                String eventDate = event[1];
                String reminderDateStr = event[2];
                LocalDateTime reminderDate = LocalDateTime.parse(reminderDateStr);

                if (reminderDate.withSecond(0).withNano(0).equals(now)) {
                    sendEmail(
                        recipient,
                        "Reminder: " + eventName,
                        "Your event \"" + eventName + "\" is scheduled at " + eventDate,
                        username,
                        password
                    );
                }
            } catch (Exception e) {
                System.out.println("Error parsing reminder date for event: " + event[0]);
            }
        }
    }

    // Email sending method using Jakarta Mail
    public void sendEmail(String to, String subject, String body, String username, String password) throws MessagingException {
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
