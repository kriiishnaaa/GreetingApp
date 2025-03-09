package com.GreetingApp.GreetingApp.Controller;

import com.GreetingApp.GreetingApp.Service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;
    
    @GetMapping("/send")
    public String sendTestEmail(@RequestParam String to) {
        emailService.sendSimpleEmail(to, "Test Email", "Hello! This is a test email from Spring Boot.");
        return "Simple email sent successfully!";
    }

    @GetMapping("/sendHtml")
    public String sendHtmlEmail(@RequestParam String to) {
        String htmlContent = "<h1>Hello from Spring Boot</h1><p>This is a <b>test email</b> with HTML content.</p>";
        try {
            emailService.sendHtmlEmail(to, "Test HTML Email", htmlContent);
            return "HTML email sent successfully!";
        } catch (MessagingException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }

    @GetMapping("/sendWithAttachment")
    public String sendEmailWithAttachment(@RequestParam String to, @RequestParam String filePath) {
        try {
            emailService.sendEmailWithAttachment(to, "Test Email with Attachment", "Please find the attachment below.", filePath);
            return "Email with attachment sent successfully!";
        } catch (MessagingException e) {
            return "Failed to send email: " + e.getMessage();
        }
    }
}
