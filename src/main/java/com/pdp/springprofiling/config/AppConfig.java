package com.pdp.springprofiling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class AppConfig {

    @Bean
    public FreeMarkerConfigurationFactoryBean freemarkerConfiguration() {
        FreeMarkerConfigurationFactoryBean configFactoryBean = new FreeMarkerConfigurationFactoryBean();
        configFactoryBean.setTemplateLoaderPath("classpath:/templates/");
        return configFactoryBean;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("sandbox.smtp.mailtrap.io");
        javaMailSender.setPort(587);
        javaMailSender.setUsername("aedeb5a3378e1d");
        javaMailSender.setPassword("2c6ac457a8ff8b");
        return javaMailSender;
    }
}
