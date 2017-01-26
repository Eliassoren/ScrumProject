package com.CardiacArray.restService.Mail;

import com.CardiacArray.restService.db.ReadConfig;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {

    public static void sendMail(String to, String subject, String body) {
        try {
            ReadConfig readConfig = new ReadConfig();
            String[] result = readConfig.getConfigValues();
            String username = result[2];
            String password = result[3];
            Properties props = System.getProperties();
            String host = "smtp.gmail.com";
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.user", username);
            props.put("mail.smtp.password", password);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            Session session = Session.getDefaultInstance(props, null);
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
