package com.pdp.springprofiling.controller;

import com.pdp.springprofiling.entity.User;
import com.pdp.springprofiling.service.UserService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final JavaMailSender emailSender;

    private final Configuration freemarkerConfig;
    private final UserService userService;

    public RegistrationController(JavaMailSender emailSender, @Qualifier("freemarkerConfiguration") Configuration freemarkerConfig, UserService userService) {
        this.emailSender = emailSender;
        this.freemarkerConfig = freemarkerConfig;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userService.saveUser(user);

        sendActivationEmail(user);

        return ResponseEntity.ok("Registration successful. Activation email sent.");
    }

    @Async
    protected void sendActivationEmail(User user) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(user.getEmail());
            helper.setSubject("Activate your account");

            Map<String, Object> model = new HashMap<>();
            model.put("username", user.getUsername());
            model.put("activationToken", UUID.randomUUID().toString());

            String htmlContent = processFreemarkerTemplate(model);
            helper.setText(htmlContent, true);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String processFreemarkerTemplate(Map<String, Object> model) {
        try {
            Template template = freemarkerConfig.getTemplate("activation-email-template.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return "";
        }
    }
}
