package com.pdp.springprofiling.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceLoggingAspect {

    @Before("@within(org.springframework.stereotype.Service)")
    public void logBeforeServiceMethodExecution(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Log => Before execution of method in Service class: " + className);
    }

    @After("@within(org.springframework.stereotype.Service)")
    public void logAfterServiceMethodExecution(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("Log => After execution of method in Service class: " + className);
    }
}
