package com.logical.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

   // public static void sendMail(String to,String mess) {

//        System.out.println("preparing to send message ...");
//
//        String message = mess;
//        String subject = "Confirmation";
//        String user = to;
//        String from = "hrlogicalsofttech@gmail.com";
//        //   sendEmail(message, subject, to, from);
//
//        boolean f = false;
        //	sendAttach(message,subject,to,from);

//	//this is responsible to send the message with attachment
//	private static void sendAttach(String message, String subject, String to, String from) {
//
//		//Variable for gmail
//		String host="smtp.gmail.com";
//
//		//get the system properties
//		Properties properties = System.getProperties();
//		System.out.println("PROPERTIES "+properties);
//
//		//setting important information to properties object
//
//		//host set
//		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.port","465");
//		properties.put("mail.smtp.ssl.enable","true");
//		properties.put("mail.smtp.auth","true");
//
//		//Step 1: to get the session object..
//		Session session=Session.getInstance(properties, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("kumararjit100@gmail.com", "Bunty789#@");
//			}
//
//
//
//		});
//
//		session.setDebug(true);
//
//		//Step 2 : compose the message [text,multi media]
//		MimeMessage m = new MimeMessage(session);
//
//		try {
//
//		//from email
//		m.setFrom(from);
//
//		//adding recipient to message
//		m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//		//adding subject to message
//		m.setSubject(subject);
//
//
//		//attachement..
//
//		//file path
//		String path="C:\\Users\\chait\\Desktop\\declearation.jpeg";
//
//
//		MimeMultipart mimeMultipart = new MimeMultipart();
//		//text
//		//file
//
//		MimeBodyPart textMime = new MimeBodyPart();
//
//		MimeBodyPart fileMime = new MimeBodyPart();
//
//		try {
//
//			textMime.setText(message);
//
//			File file=new File(path);
//			fileMime.attachFile(file);
//
//
//			mimeMultipart.addBodyPart(textMime);
//			mimeMultipart.addBodyPart(fileMime);
//
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}
//
//
//		m.setContent(mimeMultipart);
//
//
//		//send
//
//		//Step 3 : send the message using Transport class
//		Transport.send(m);
//
//
//
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
//
//
//
//
//
//
//
//
//		System.out.println("Sent success...................");
//
//
//	}

        //this is responsible to send email..
        public boolean sendEmail ( String subject,String message, String to){
            System.out.println("preparing to send message ...");

           // String message = mess;
           // String subject = "Confirmation";
          //  String user = to;
         //   String from = "nemaaakashji5726@gmail.com";
            String from = "hrlogicalsofttech@gmail.com";
            //   sendEmail(message, subject, to, from);

            boolean f = false;

            //Variable for gmail
            String host = "smtp.gmail.com";

            //get the system properties
            Properties properties = System.getProperties();
            System.out.println("PROPERTIES " + properties);

            //setting important information to properties object

            //host set
//            properties.put("mail.smtp.host", host);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            //Step 1: to get the session object..
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("hr.atmarambhide@gmail.com", "ajofjdohswdnasaf\r\n"+"");
                }

            });

            session.setDebug(true);

            // Step 2 : compose the message [text,multi media]
            MimeMessage m = new MimeMessage(session);

            try {

                //from email
                m.setFrom(new InternetAddress(to)); //from

                //adding recipient to message
                m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                //adding subject to message
                m.setSubject(subject);


                //adding text to message
                m.setText(message);

                //send

                //Step 3 : send the message using Transport class
                Transport.send(m);

                System.out.println("Sent success...................");
                f = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
         return f;
        }
    public boolean sendPasswordToEmail ( String subject,String message, String to){
        System.out.println("preparing to send message ...");

        // String message = mess;
        // String subject = "Confirmation";
        //  String user = to;
        //   String from = "nemaaakashji5726@gmail.com";
        String from = "hrlogicalsofttech@gmail.com";
        //   sendEmail(message, subject, to, from);

        boolean f = false;

        //Variable for gmail
        String host = "smtp.gmail.com";

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " + properties);

        //setting important information to properties object

        //host set
//            properties.put("mail.smtp.host", host);
//            properties.put("mail.smtp.port", "465");
//            properties.put("mail.smtp.ssl.enable", "true");
//            properties.put("mail.smtp.auth", "true");

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        //Step 1: to get the session object..
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("hr.atmarambhide@gmail.com", "ajofjdohswdnasaf\r\n"
                        + "");
            }

        });

        session.setDebug(true);

        // Step 2 : compose the message [text,multi media]
        MimeMessage m = new MimeMessage(session);

        try {

            //from email
            m.setFrom(new InternetAddress(to)); //from

            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);


            //adding text to message
            m.setText(message);

            //send

            //Step 3 : send the message using Transport class
            Transport.send(m);

            System.out.println("password send success on email...................");
            f = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
//        System.out.println("preparing to send message ...");
//
//        // String message = mess;
//        // String subject = "Confirmation";
//        //  String user = to;
//        String from = "nemaaakashji5726@gmail.com";
//        //   sendEmail(message, subject, to, from);
//
//        boolean f = false;
//
//        //Variable for gmail
//        String host = "smtp.gmail.com";
//
//        //get the system properties
//        Properties properties = System.getProperties();
//        System.out.println("PROPERTIES " + properties);
//
//        //setting important information to properties object
//
//        //host set
//        properties.put("mail.smtp.host", host);
//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//
//        //Step 1: to get the session object..
//        Session session = Session.getInstance(properties, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("nemaaakashji5726@gmail.com", "bojlhevuloycretl\r\n"
//                        + "");
//            }
//
//
//        });
//
//        session.setDebug(true);
//
//        // Step 2 : compose the message [text,multi media]
//        MimeMessage m = new MimeMessage(session);
//
//        try {
//
//            //from email
//            m.setFrom(new InternetAddress(from));
//
//            //adding recipient to message
//            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//
//            //adding subject to message
//            m.setSubject(subject);
//
//
//            //adding text to message
//            m.setText(message);
//
//            //send
//
//            //Step 3 : send the message using Transport class
//            Transport.send(m);
//
//            System.out.println("Sent success...................");
//            f = true;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return f;
    }

//    public boolean sendEmails ( String subject,String message, String to){
//        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
//        simpleMailMessage.setFrom("hrlogicalsoft@gmail.com");
//        simpleMailMessage.setTo(to);
//        simpleMailMessage.setText(message);
//        simpleMailMessage.setSubject(subject);
//
//        javaMailSender.send(simpleMailMessage);
//        System.out.println("----------------send success----");
//        return true;
//    }
}
