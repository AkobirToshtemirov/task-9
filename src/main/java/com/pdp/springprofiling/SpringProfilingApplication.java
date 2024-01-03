package com.pdp.springprofiling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringProfilingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProfilingApplication.class, args);
    }

}
