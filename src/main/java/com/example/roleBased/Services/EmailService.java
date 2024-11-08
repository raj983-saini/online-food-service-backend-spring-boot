package com.example.roleBased.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendOtpEmail(String to, String subject, String otp) {
        // Validate the email address format
        if (to == null || !EMAIL_PATTERN.matcher(to).matches()) {
            throw new IllegalArgumentException("Invalid email address: " + to);
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText("Your OTP code is: " + otp);

            mailSender.send(message);
            logger.info("OTP email sent to: {}", to);
        } catch (MailParseException e) {
            logger.error("MailParseException while sending email to {}: {}", to, e.getMessage());
            throw new MailParseException("Could not send email to " + to, e);
        } catch (Exception e) {
            logger.error("Exception while sending email to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Could not send email to " + to, e);
        }
    }
}
