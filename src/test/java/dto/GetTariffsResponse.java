package dto;


import lombok.Data;

import java.util.List;

@Data
public class GetTariffsResponse {
    private List<TariffDTO> tariffs;
}