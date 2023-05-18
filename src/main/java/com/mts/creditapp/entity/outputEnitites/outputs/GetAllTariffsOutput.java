package com.mts.creditapp.entity.outputEnitites.outputs;

import com.mts.creditapp.entity.tableEntities.Tariff;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class GetAllTariffsOutput {
    private Optional<List> tariffs;
}
