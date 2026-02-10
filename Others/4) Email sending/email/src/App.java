
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class App {

    public static void main(String[] args) {
        try {
            sendEmail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendEmail() throws Exception {
        // Sender's email and password
        String senderEmail = "imhraza023@gmail.com";
        String senderPassword = "get app password from smtp@gmail.com, it is written in secret diary :)"; // Use app password for Gmail

        // Recipient email
        String recipientEmail = "hr770735@gmail.com";

        // SMTP server properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Create session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail, "Imhraza023"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("This email is for testing..");

            // Create the message body
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent("<h1>This is content</h1>", "text/html; charset=utf-8");

            // Create multipart for HTML content
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
