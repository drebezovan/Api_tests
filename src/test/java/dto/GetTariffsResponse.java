package dto;

import com.mts.creditapp.entity.tableEntities.Tariff;
import lombok.Data;

import java.util.List;

@Data
public class GetTariffsResponse {
    private List<Tariff> tariffs;
}