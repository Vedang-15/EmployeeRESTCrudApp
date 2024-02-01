package com.example.crudRestProjectMVC.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    //setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    // setup pointcut declarations
    @Pointcut("execution(* com.example.crudRestProjectMVC.controller.*.*(..))")
    private void forControllerPackage(){

    }

    @Pointcut("execution(* com.example.crudRestProjectMVC.dao.*.*(..))")
    private void forDaoPackage(){

    }

    @Pointcut("execution(* com.example.crudRestProjectMVC.service.*.*(..))")
    private void forServicePackage(){

    }

    @Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
    private void forAppFlow(){

    }

    // add @Before advice
    @Before("forAppFlow()")
    public void beforeMethodCall(JoinPoint theJoinPoint){

        //display the method we are calling
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("***** Inside @Before : calling the method : " + theMethod);

        // get and display the arguments of that method
        Object[] args = theJoinPoint.getArgs();
        for(Object tempArg : args){
            myLogger.info(">>>>> argument : " + tempArg);
        }

    }

    // add @AfterReturning advice
    @AfterReturning(pointcut = "forAppFlow()", returning = "theResult")
    public void afterMethodReturns(JoinPoint theJoinPoint, Object theResult){

        //display the method we are returning from
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("***** Inside @AfterReturning: from the method : " + theMethod);

        //display the data that is being returned by that method
        myLogger.info(">>>>> result is : " + theResult);
    }


}
