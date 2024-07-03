package mail;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Main {
    public static void main(String[] args) {
        // Címzettek email címe
        String to = "cimzett@example.com";

        // Feladó email címe
        String from = "felado@example.com";

        // Email szerver
        String host = "localhost";
        
        // SMTP szerver portja (pl.: 2525)
        String port = "80";

        // Rendszer tulajdonságai
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", port);

        // Sesszió létrehozása az SMTP szerverrel
        Session session = Session.getDefaultInstance(properties);

        try {
            // MimeMessage objektum létrehozása
            MimeMessage message = new MimeMessage(session);

            // Feladó beállítása
            message.setFrom(new InternetAddress(from));

            // Címzettek hozzáadása
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Email tárgya
            message.setSubject("Ez egy példa email");

            // Email tartalma
            message.setText("Üdvözlet! Ez egy példa email.");

            // Email küldése
            Transport.send(message);
            System.out.println("Az email sikeresen elküldve.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}