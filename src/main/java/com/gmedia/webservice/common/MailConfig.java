package com.gmedia.webservice.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by betterFLY on 2018. 4. 25.
 * Github : http://github.com/betterfly88
 */

@Configuration
@PropertySource("classpath:properties/mail.properties")
public class MailConfig {
    @Value("${mail.smtp.host}")
    String host;

    @Value("${mail.smtp.port}")
    String port;

    @Value("${mail.smtp.user}")
    String user;

    @Value("${mail.smtp.pass}")
    String password;

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(user);
        mailSender.setPassword(password);
        mailSender.setJavaMailProperties(getMailProperties());
        return mailSender;
    }


    private Properties getMailProperties(){
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.ssl.trust", host);
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.socketFactory.port", port);
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        return properties;
    }

}
