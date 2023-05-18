package com.mts.creditapp.repository.impl;

import com.mts.creditapp.entity.tableEntities.Tariff;
import com.mts.creditapp.repository.TariffRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class TariffRepImpl implements TariffRep {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String SELECT_ALL_FROM_TARIFF="SELECT * FROM TARIFF";
    private final String CHECK_IF_TARIFF_EXISTS="SELECT EXISTS (SELECT * FROM TARIFF WHERE ID=?)";
    private final String INSERT_NEW_TARIFF ="INSERT INTO TARIFF (TYPE, INTEREST_RATE) VALUES (?,?)";
    private final String DELETE_TARIFF="DELETE FROM TARIFF WHERE ID=?";
    private static final class TariffRowMapper implements RowMapper {
        @Override
        public Tariff mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new Tariff(rs.getLong("ID"),
                    rs.getString("TYPE"),
                    rs.getString("INTEREST_RATE"));
        }
    }
    @Override
    public Optional<List> getAllTariffs() {
        return Optional.of(jdbcTemplate.query(
                SELECT_ALL_FROM_TARIFF,
                new  TariffRowMapper())
        );
    }
    @Override
    public Optional<Boolean> checkIfTariffExists(long id){
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    CHECK_IF_TARIFF_EXISTS,
                    Boolean.class, id)
            );
        }
        catch(EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }
    @Override
    public int addTariff(Tariff tariff){
        return jdbcTemplate.update(
                INSERT_NEW_TARIFF,
                tariff.getType(),
                tariff.getInterestRate()
        );
    }
    @Override
    public int deleteTariff(long id){
        return jdbcTemplate.update(DELETE_TARIFF, id);
    }
}
