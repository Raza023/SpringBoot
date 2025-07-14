package com.example.batch.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/*
https://support.cloudways.com/en/articles/5131076-how-to-configure-gmail-smtp#h_064a32f5ea
*/
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender sender;

    public String sendEmail(String to, String TextBody) {
        String msg = "";
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setTo(to);
            message.setText(TextBody);
            message.setSubject("Payment Dues Alert");
            message.setFrom("imhraza023@gmail.com");
            sender.send(message);
            msg = "mail triggered successfully to : " + to;
        } catch (Exception e) {
            msg = e.getMessage();
        }
        return msg;
    }

}
