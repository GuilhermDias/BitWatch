package com.guilherme.bitWatch.infra.security.auth.email;

import com.guilherme.bitWatch.domain.user.RequestUser;
import com.guilherme.bitWatch.infra.exception.BusinessRuleException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private static final String ORIGIN_EMAIL = "diasg11227@gmail.com";

    private static final String SENDER_NAME = "BitWatch";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(String userMail, String subject, String text){
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(ORIGIN_EMAIL, SENDER_NAME);
            helper.setTo(userMail);
            helper.setSubject(subject);
            helper.setText(text, true);
        } catch(MessagingException | UnsupportedEncodingException exception){
            throw new BusinessRuleException("Error to send email");
        }

        mailSender.send(message);
    }

    public void sendEmailWithCode(RequestUser user, String code){
        String subject = "Welcome to BitWatch | Active Code";
        String text = generateTextMail("Hi! [[name]],<br>" +
                "Your active code is here <br>" +
                "<strong>Code:</strong> [[code]] <br>" +
                "Count on our team for whatever you need!<br>" +
                "Thank you,<br>" +
                "BitWatch.", user.name(), code);

        sendEmail(user.email(), subject, text);
    }

    public String generateTextMail(String template, String name, String code){
        return template.replace("[[name]]", name).replace("[[code]]", code);
    }

}
