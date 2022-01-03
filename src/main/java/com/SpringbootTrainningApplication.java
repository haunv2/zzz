package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
@EnableCaching
public class SpringbootTrainningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootTrainningApplication.class, args);
    }

    @Bean
    public static JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.mailtrap.io");
        mailSender.setPort(465);

        mailSender.setUsername("0845954f4600a5");
        mailSender.setPassword("8252158432ddf2");

        Properties props = mailSender.getJavaMailProperties();
        props.setProperty("mail.smtp.auth", "true");

        return mailSender;
    }
}
