package daw.project.apachas.service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import daw.project.apachas.model.MEmailBody;
import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.File;

@Service
public class SEmail{

    @Autowired
    private JavaMailSender sender;

    public boolean sendEmail(@RequestBody MEmailBody emailBody)  {
        return sendEmailTool(emailBody);
    }

    private boolean sendEmailTool(MEmailBody emailBody) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);

            helper.setTo(InternetAddress.parse(emailBody.getEmail()));
            helper.setSubject(emailBody.getSubject());
            helper.setText(emailBody.getContent(), true);
            sender.send(message);
            send = true;
        } catch (MessagingException e) {
           e.printStackTrace();
        }
        return send;
    }

    public boolean sendEmailWithImage(@RequestBody MEmailBody emailBody) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true, CharEncoding.UTF_8);
            FileSystemResource resource = new FileSystemResource(new File("src/main/resources/images/logo.png"));
            helper.setTo(InternetAddress.parse(emailBody.getEmail()));
            helper.setSubject(emailBody.getSubject());
            helper.setText(emailBody.getContent(), true);
            helper.addInline("image001", resource);
            sender.send(message);
            send = true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return send;
    }
}