package com.eugeniomoreira.serviceorderapi.api.exceptionhandler;

import com.eugeniomoreira.serviceorderapi.domain.exception.DomainException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation
        .ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Object> handleDomain(DomainException ex,
    WebRequest webRequest) {
        var status = HttpStatus.BAD_REQUEST;
        var problem = new Problem();

        problem.setStatus(status.value());
        problem.setDateHour(LocalDateTime.now());
        problem.setMessage(ex.getMessage());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status
                , webRequest);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        var problem = new Problem();
        var fields = new ArrayList<Problem.Field>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error,
                    LocaleContextHolder.getLocale());

            fields.add(new Problem.Field(name, message));
        }

        problem.setStatus(status.value());
        problem.setDateHour(LocalDateTime.now());
        problem.setMessage("One or more fields are invalid. Please try again");
        problem.setFields(fields);

        return super.handleExceptionInternal(ex, problem, headers, status,
                request);
    }
}
