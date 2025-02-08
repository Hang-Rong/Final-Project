package com.codegym.service;

import com.codegym.model.ComplaintForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendComplaintEmail(ComplaintForm complaint) throws MessagingException {
        String adminEmail = "admin@example.com"; // Email của admin

        String subject = "Khiếu nại từ " + complaint.getName();
        String content = "<p><strong>Họ tên:</strong> " + complaint.getName() + "</p>"
                + "<p><strong>Email:</strong> " + complaint.getEmail() + "</p>"
                + "<p><strong>Chủ đề:</strong> " + complaint.getSubject() + "</p>"
                + "<p><strong>Nội dung:</strong></p>"
                + "<p>" + complaint.getMessage() + "</p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(adminEmail);
        helper.setSubject(subject);
        helper.setText(content, true); // true để cho phép HTML

        mailSender.send(message);
    }
}