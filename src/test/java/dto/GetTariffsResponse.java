package dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.mts.creditapp.entity.tableEntities.Tariff;
import lombok.Data;

import java.util.List;
@JsonRootName(value = "data")
@Data
public class GetTariffsResponse {
    private List<Tariff> tariffs;
}