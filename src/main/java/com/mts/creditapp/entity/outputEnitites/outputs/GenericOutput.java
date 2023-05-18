package com.mts.creditapp.entity.outputEnitites.outputs;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericOutput<T> {
    public T data;
}
