package listener;
// Java program to send email

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

class SendFileEmail
{
    public static void main(String[] args)
    {
        // email ID of Recipient.
        String recipient = "radhakrishna059@gmail.com";
        //String[] recipient = new String[2];


        // email ID of Sender.
        String sender = "radhakrishna.iet@gmail.com";

        // using host as localhost
        String host = "gmail-smtp-in.l.google.com";

        // Getting system properties
        Properties properties = System.getProperties();

        // Setting up mail server
        properties.setProperty("mail.smtp.host", host);

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        System.out.println("Sending the mail...");

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("Api automation Report");

            MimeBodyPart messageBodyPart=new MimeBodyPart();
            LocalDate today= LocalDate.now();
            // set body of the email.
            //messageBodyPart.setText("Hi team, Please find below the Report of api automation test "+today);
            message.setText("Please find below the report of Api automation on date "+ today);

            // Add file as attachment in gmail
            Multipart multipart = new MimeMultipart();
            String filename = "E:\\workspace\\CustomReports\\CustomReport.html";
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
}
