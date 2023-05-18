package com.mts.creditapp.entity.outputEnitites.errors;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericErrorEntity {
    private String code;
    private String message;
}
