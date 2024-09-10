package com.example.local_directory_browser.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Aspect
@Component
public class GlobalExceptionHandlerAspect {

    @Pointcut("execution(* com.example.local_directory_browser.controller..*(..))")
    public void controllerMethods() {}

    @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
    public ResponseEntity<Object> handleException(Exception ex) {

        if (ex instanceof FileNotFoundException) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (ex instanceof NullPointerException) {
            return new ResponseEntity<>("An exception occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Default response for any other exception
        System.out.println("Exception: " + ex.getMessage());
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
