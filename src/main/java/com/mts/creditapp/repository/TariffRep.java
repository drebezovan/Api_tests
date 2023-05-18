package com.mts.creditapp.repository;

import com.mts.creditapp.entity.tableEntities.Tariff;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TariffRep {
    Optional<List> getAllTariffs();
    Optional<Boolean> checkIfTariffExists(long id);
    int addTariff(Tariff tariff);
    int deleteTariff(long id);
}
