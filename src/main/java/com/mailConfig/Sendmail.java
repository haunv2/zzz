package com.mailConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class Sendmail {
    private final JavaMailSender javaMailSender;
    Logger mailLogger = LoggerFactory.getLogger(Sendmail.class);

    @Autowired
    public Sendmail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        mailLogger.info("Sendmail init and sender is null?->".concat(javaMailSender == null ? "true" : "false"));
    }

    public void sendMail(String to, String subject, String content) {
        mailLogger.info("Begin send mail");
        SimpleMailMessage message = new SimpleMailMessage(); // init new SimpleMailMessage

        // set recipient, subject, content
        message.setFrom("noreply@aladintech.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailLogger.info("Send mail to ".concat(to.concat(" successfully!")));
    }

    // use MimeMessageHelper to send mail with attachment files
    public void sendMailWithAttachment(String to, String subject, String content, String... files) throws MessagingException {
        mailLogger.info("Begin send mail attachment");
        MimeMessage message = javaMailSender.createMimeMessage(); // init new MimeMessage from JavaMailSender
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, true); // init MineMessageHelper

        // set recipient, subject, content
        messageHelper.setFrom("noreply@aladintech.com");
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(content);

        // walk through files and attachment
        if (files != null) {
            for (String filePath : files) {
                File file = new File(filePath);
                messageHelper.addAttachment(file.getName(), file);
            }
        }

        javaMailSender.send(message); // send mail
        mailLogger.info("Send mail attachment to ".concat(to.concat(" successfully!")));
    }

}
