package com.peigo.msvcaccounts.common.exceptions.handler;

import com.peigo.msvcaccounts.common.exceptions.PaymentException;
import com.peigo.msvcaccounts.common.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class Exception extends ResponseEntityExceptionHandler {
    @ExceptionHandler(PaymentException.class)
    public final ResponseEntity<Object> configExceptionHandler(PaymentException ex, WebRequest request) {
        return new ResponseEntity(new ExceptionResponse(Utils.getTime(), ex.getMessage(),
                request.getDescription(false)), HttpStatus.BAD_REQUEST);
    }
}
