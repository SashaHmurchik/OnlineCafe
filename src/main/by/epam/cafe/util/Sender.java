package by.epam.cafe.util;

import by.epam.cafe.resource.ConfigurationManager;
import org.apache.logging.log4j.Level;

import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Sender {
    private static final String USER_NAME = ConfigurationManager.getProperty("path.page.foodcort");

    private String username;
    private String password;
    private Properties props;

    public Sender() {
        this.username = InitSender.eMail;
        this.password = InitSender.password;

        props = new Properties();
        props.put(InitSender.AUTH, InitSender.auth);
        props.put(InitSender.STARTTLS, InitSender.starttls);
        props.put(InitSender.HOST, InitSender.host);
        props.put(InitSender.PORT, InitSender.port);
    }

    /**
     * Method for sending eMail with new password.
     * @param subject theme of eMail
     * @param text text of eMail
     * @param toEmail email for sending to
     */
    public void send(String subject, String text, String toEmail){

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
