import io.github.cdimascio.dotenv.Dotenv;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

    public static void send(String messagem){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.mail.yahoo.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.auth", "true");

        Dotenv env = Dotenv.load();
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(env.get("EMAIL_LOGIN"), env.get("EMAIL_KEY"));
            }
        });

      session.setDebug(false);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(env.get("EMAIL_LOGIN")));   //Remetente

            Address[] toUser = InternetAddress.parse(env.get("EMAIL_DESTINY"));   //Destinatário(s)

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Licenças usadas hoje!");
            message.setText(messagem);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}