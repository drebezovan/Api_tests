package com.mts.creditapp.exception.customException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CreditException extends RuntimeException{
    private String errorCode;
    private String errorMessage;
    public CreditException(String errorCode, String errorMessage){
        this.errorCode=errorCode;
        this.errorMessage=errorMessage;
    }
}
