package com.gmedia.webservice.common;

import com.gmedia.webservice.mail.vo.MailVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by betterFLY on 2018. 5. 1.
 * Github : http://github.com/betterfly88
 */

@Configuration
@PropertySource(value="classpath:properties/connection.properties")
public class MailSender {
    @Value("${mail.smtp.host}")
    String host;

    @Value("${mail.smtp.port}")
    String port;

    @Value("${mail.smtp.user}")
    String user;

    @Value("${mail.smtp.pass}")
    String pass;


    private Session mailAuthCheck(){
        Session session = Session.getDefaultInstance(getMailProperties(), new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, pass);
            }
        });

        return session;
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

    public boolean sendMail(MailVO vo){
        boolean flag = false;
        try{
            MimeMessage message = new MimeMessage(this.mailAuthCheck());
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, (Address) vo.getAddress());

            message.setSubject(vo.getSubject());
            message.setText(vo.getContents());

            Transport.send(message);

            flag = true;

            return flag;

        }catch (AddressException e){
            e.printStackTrace();
            return flag;
        }catch (MessagingException e) {
            e.printStackTrace();
            return flag;
        }

    }
}