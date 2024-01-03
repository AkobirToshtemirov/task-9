package com.pdp.springprofiling.service;

import com.pdp.springprofiling.entity.Todo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TodoScheduler {

    private final TodoService todoService;
    private final JavaMailSender emailSender;
    private final Configuration freemarkerConfig;

    public TodoScheduler(TodoService todoService, JavaMailSender emailSender, @Qualifier("freeMarkerConfiguration") Configuration freemarkerConfig) {
        this.todoService = todoService;
        this.emailSender = emailSender;
        this.freemarkerConfig = freemarkerConfig;
    }

    @Scheduled(cron = "0 0 9,18 * * *")
    public void sendUncompletedTodos() {
        List<Todo> uncompletedTodos = todoService.getAllTodos().stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());

        sendEmailWithTodos(uncompletedTodos);
    }

    @Async
    protected void sendEmailWithTodos(List<Todo> todos) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo("recipient@example.com");
            helper.setSubject("Uncompleted Todos");

            Map<String, Object> model = new HashMap<>();
            model.put("todos", todos);

            String htmlContent = processFreemarkerTemplate(model);
            helper.setText(htmlContent, true);

            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String processFreemarkerTemplate(Map<String, Object> model) {
        try {
            Template template = freemarkerConfig.getTemplate("uncompleted-todos.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
            return "";
        }
    }
}
