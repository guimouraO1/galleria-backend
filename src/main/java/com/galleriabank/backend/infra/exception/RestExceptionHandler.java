package com.galleriabank.backend.infra.exception;

import com.galleriabank.backend.exceptions.InvalidCredentialsException;
import com.galleriabank.backend.exceptions.UserNotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private @NonNull ResponseEntity<RestErrorMessage> userNotFoundException(@NonNull UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage()));
    }


    @ExceptionHandler(InvalidCredentialsException.class)
    private @NonNull ResponseEntity<RestErrorMessage> invalidUserCredentialsException(@NonNull InvalidCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new RestErrorMessage(HttpStatus.FORBIDDEN, exception.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {

        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.computeIfAbsent(error.getField(), key -> new ArrayList<>()).add(error.getDefaultMessage());
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}