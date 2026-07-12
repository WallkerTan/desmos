package com.example.backend.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    // đo thời gian thực thi các phương thức trong service
    @Around("execution(* com.example.backend.service.impl.*.*(..))")
    public Object LogExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Long startTime = System.currentTimeMillis();
        Object res = proceedingJoinPoint.proceed();
        Long timeTaken = System.currentTimeMillis() - startTime;

        try {
            String methodName = proceedingJoinPoint.getSignature().getName();
            String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();
            
            System.out.println("---- Log thời gian thực thi Service [LogAspect] ----");
            System.out.println("Class thực thi (Tầng gọi): " + className);
            System.out.println("Method được gọi: " + methodName);
            System.out.println("Thời gian chạy (ms): " + timeTaken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    // Log trước khi bất kỳ method nào trong các Service được gọi
    @org.aspectj.lang.annotation.Before("execution(* com.example.backend.service.impl.*.*(..))")
    public void logBeforeService(org.aspectj.lang.JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("---- Log trước khi thao tác Service [LogLoggingAspect] ----");
        System.out.println("Class thực thi: " + joinPoint.getTarget().getClass().getSimpleName());
        System.out.println("Method được gọi: " + methodName);
    }


    // đo thời gian thực thi các phương thức đưuọc đánh dấu
    @Around("execution(* com.example.backend.annotation.TrackTime)")
    public Object LogAnnotationExecutionTime(ProceedingJoinPoint proceedingJoinPoint)
            throws Throwable {

        Long startTime = System.currentTimeMillis();
        Object res = proceedingJoinPoint.proceed();
        Long timeTaken = System.currentTimeMillis() - startTime;

        try {
            System.out.println("---Transaction Around log time [LogAspect annotation]---");
            System.out.println("phương thức: " + proceedingJoinPoint.getSignature().getName());
            System.out.println("thời gian: " + timeTaken);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return res;
    }

}
