package com.johanrivas.jlearning.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    protected JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(EmailSender.class);

    public void sendEmail(String email, String password) {

        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("johanrivascastro@gmail.com");
            msg.setTo(email);
            msg.setSubject("Your password");
            msg.setText(password);

            javaMailSender.send(msg);
            logger.info("*************");
        } catch (MailException e) {
            logger.info("############################");
            logger.info(e.getMessage());
            logger.info("############################");
        }
    }
}