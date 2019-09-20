package com.focusteam.dealhunter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

public class MailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String title, String message){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(toEmail);

        msg.setSubject(title);
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);
    }

    public void sendEmailWithAttachment(String email, String body, String subject) throws MessagingException, IOException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setFrom("DEAL");
        helper.setTo(email);
        helper.setSubject(subject);
        // default = text/plain
        //helper.setText("Check attachment for image!");
        // true = text/html
        helper.setText(body, true);
        //helper.addAttachment("my_photo.png", new ClassPathResource("android.png"));
        javaMailSender.send(msg);

    }

}
