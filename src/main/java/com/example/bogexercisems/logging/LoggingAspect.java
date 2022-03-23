package com.example.bogexercisems.logging;

import com.example.bogexercisems.feign.feignDtos.UserDetailsDTO;
import com.example.bogexercisems.utilities.CustomUtility;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@Aspect
@Log4j2
public class LoggingAspect {

    private Throwable throwable;

    @Pointcut("within(com.example.bogexercisems..*)")
    protected void loggingAllOperations() {
    }

    @Pointcut("execution(* com.example.bogexercisems.controller.*.*(..))")
    protected void loggingControllerOperations() {
    }

    @AfterThrowing(pointcut = "loggingAllOperations()", throwing = "exception")
    public void logAllExceptions(JoinPoint joinPoint, Throwable exception) {
        if (!exception.equals(throwable)) {
            log.error("Exception occurred while executing method \"" + joinPoint.getSignature() + "\"");
            log.error("Cause: " + exception.getCause() + ". Exception : " + exception.getClass().getName() + ". Message: " + exception.getMessage());
            log.error("Provided arguments: ");
            Object[] arguments = joinPoint.getArgs();
            for (Object a :
                    arguments) {
                if (Objects.isNull(a)) {
                    log.error("[null]");
                } else {
                    log.error("Class name: " + a.getClass().getSimpleName() + ". Value: " + a);
                }
            }
            throwable = exception;
        }
    }

    @Before("loggingControllerOperations()")
    public void logEndpointAccess(JoinPoint joinPoint) {
        Optional<UserDetailsDTO> currentUser = CustomUtility.getCurrentUser();
        String username = currentUser.isPresent() ? currentUser.get().getUsername() : "Anonymous";
        log.info("User with username \"" + username + "\" got access to the controller method " + joinPoint.getSignature());
    }

    @AfterReturning(value = "execution(* com.example.bogexercisems.service.ExerciseService.createExercise(..))", returning = "id")
    public void logCreatingExercise(JoinPoint joinPoint, UUID id) {
        Optional<UserDetailsDTO> currentUser = CustomUtility.getCurrentUser();
        String username = currentUser.isPresent() ? currentUser.get().getUsername() : "Anonymous";
        log.info("User with username \"" + username + "\" successfully created exercise with id \"" + id.toString() + "\".");
    }

    @AfterReturning(value = "execution(* com.example.bogexercisems.service.MediaStorageService.storeFile(..))", returning = "fileName")
    public void logStoreFile(JoinPoint joinPoint, String fileName) {
        Optional<UserDetailsDTO> currentUser = CustomUtility.getCurrentUser();
        String username = currentUser.isPresent() ? currentUser.get().getUsername() : "Anonymous";
        log.info("User with username \"" + username + "\" successfully uploaded file with name \"" + fileName +  "\" to minio server.");
    }
}
