package com.mts.creditapp.entity.outputEnitites.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericError<T> {
    private T error;
}
