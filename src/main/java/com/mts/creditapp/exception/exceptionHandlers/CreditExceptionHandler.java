package com.mts.creditapp.exception.exceptionHandlers;

import com.mts.creditapp.entity.outputEnitites.errors.GenericError;
import com.mts.creditapp.entity.outputEnitites.errors.GenericErrorEntity;
import com.mts.creditapp.exception.customException.CreditException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CreditExceptionHandler {
    @ExceptionHandler(CreditException.class)
    public ResponseEntity<GenericError> creditExceptionHandler(CreditException exception){
        log.error(exception.getErrorCode());
        return new ResponseEntity<>(
                new GenericError(
                        new GenericErrorEntity(
                                exception.getErrorCode(),
                                exception.getErrorMessage()
                        )), HttpStatusCode.valueOf(400));
    }
}
