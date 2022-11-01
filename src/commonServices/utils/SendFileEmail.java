package src.commonServices.utils;
// Java program to send email

import org.testng.annotations.Test;
import src.commonServices.utils.ExtentTestFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.time.LocalDate;

public class SendFileEmail
{
    public SendFileEmail() throws InterruptedException {
        System.out.println("Sleeping for 3 seconds to generate the report successfully");
        Thread.sleep(3000);
        // email ID of Recipient.
        String recipient = "radhakrishna059@gmail.com,akash@100ms.live,sita@100ms.live,sonal@100ms.live,shatabdi@100ms.live,kritika.dusad@100ms.live";
//        //String[] recipient = new String[2];
//
//
//        // email ID of Sender.
        String sender = "radhakrishna.iet@gmail.com";
//
//        // using host as localhost
        String host = "gmail-smtp-in.l.google.com";

        // Setting up mail server
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "25");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, "wmlbhowblszszhuz");
            }
        };
//      // creating session object to get properties
        Session session = Session.getInstance(props, auth);

        System.out.println("Sending the mail...");

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));
            //message.setReplyTo(InternetAddress.parse(recipient, false));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(recipient,false));
            // Set Subject: subject of the email
            message.setSubject("Api automation Report");

            MimeBodyPart messageBodyPart=new MimeBodyPart();
            LocalDate today= LocalDate.now();
            // set body of the email.
            //messageBodyPart.setText("Hi team, Please find below the Report of api automation test "+today);
            message.setText("Please find below the report of Api automation on date "+ today +". Please download it first and then open it for better look");

            // Add file as attachment in gmail
            Multipart multipart = new MimeMultipart();
            String filename = ExtentTestFactory.filePath;
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart );

            // Send email.
            Transport.send(message);
            System.out.println("Mail sent successfully");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }

//    public static void main(String args[]) throws Exception{
//        SendFileEmail sendFileEmail=new SendFileEmail();
//    }
}
