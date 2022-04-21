package com.example.app.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class CRMLoggingAspect {

    // setup logger
    private Logger logger = Logger.getLogger(getClass().getName());

    // setup pointcut declaration
    @Pointcut("execution(* com.example.app.controller.*.*(..))")
    private void forControllerPackage() {

    }

    // setup pointcut for service
    @Pointcut("execution(* com.example.app.service.*.*(..))")
    private void forServicePackage() {

    }

    // setup pointcut for dao
    @Pointcut("execution(* com.example.app.dao.*.*(..))")
    private void forDaoPackage() {

    }

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {

    }

    // add @Before advice
    @Before("forAppFlow()")
    private void before(JoinPoint joinPoint) {

        // display method we are calling
        String method = joinPoint.getSignature().toShortString();
        System.out.println(">> in @before: calling method: " + method);

        // display the arguments to the method


        // get the arguments
        Object[] args = joinPoint.getArgs();

        // loop through and display args
        for (Object temp : args) {
            logger.info(">> argument: " + temp);
        }
    }//

    // add @AfterReturning advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "result"
    )
    private void afterReturning(JoinPoint joinPoint, Object result) {

        // display method we are returning from
        String method = joinPoint.getSignature().toShortString();
        System.out.println(">> in @AfterReturning from method: " + method);

        // display data returned
        logger.info(">> result: " + result);

    }
}
