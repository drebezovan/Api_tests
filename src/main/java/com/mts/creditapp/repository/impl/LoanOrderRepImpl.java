package com.mts.creditapp.repository.impl;

import com.mts.creditapp.entity.tableEntities.LoanOrder;
import com.mts.creditapp.repository.LoanOrderRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LoanOrderRepImpl implements LoanOrderRep {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String SELECT_ALL_FROM_LOAN_ORDER="SELECT * FROM LOAN_ORDER";
    private final String SELECT_ALL_WITH_USER_ID ="SELECT * FROM LOAN_ORDER WHERE USER_ID=?";
    private final String INSERT_NEW_ORDER="INSERT INTO " +
            "LOAN_ORDER (ORDER_ID, USER_ID, TARIFF_ID, CREDIT_RATING, STATUS, TIME_INSERT, TIME_UPDATE) " +
            "VALUES (?,?,?,?,?,?,?)";
    private final String SELECT_ID_WITH_STATUS ="SELECT ID FROM LOAN_ORDER WHERE STATUS=?";
    private final String UPDATE_STATUS="UPDATE LOAN_ORDER SET STATUS=? WHERE ID=?";
    private final String UPDATE_TIME_UPDATE="UPDATE LOAN_ORDER SET TIME_UPDATE=? WHERE ID=?";
    private final String SELECT_STATUS_WITH_ORDER_ID ="SELECT STATUS FROM LOAN_ORDER WHERE ORDER_ID=?";
    private final String CHECK_IF_EXISTS_ORDER_ID_WITH_USER_ID_AND_ORDER_ID =
            "SELECT EXISTS (SELECT * FROM LOAN_ORDER WHERE USER_ID=? AND ORDER_ID=?)";
    private final String DELETE_ORDER="DELETE FROM LOAN_ORDER WHERE USER_ID=? AND ORDER_ID=?";
    private static final class LoanOrderRowMapper implements RowMapper {
        @Override
        public LoanOrder mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return new LoanOrder(rs.getLong("ID"),
                    rs.getString("ORDER_ID"),
                    rs.getLong("USER_ID"),
                    rs.getLong ("TARIFF_ID"),
                    rs.getDouble("CREDIT_RATING"),
                    rs.getString("STATUS"),
                    rs.getTimestamp("TIME_INSERT"),
                    rs.getTimestamp("TIME_UPDATE"));
        }
    }
    private static final class LongMapper implements RowMapper {
        @Override
        public Long mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            return Long.valueOf(rs.getLong("ID"));
        }
    }
    @Override
    public Optional<List> getAllLoanOrder() {
        return Optional.of(jdbcTemplate.query(
                SELECT_ALL_FROM_LOAN_ORDER,
                new LoanOrderRowMapper()
        ));
    }

    @Override
    public Optional<List> findAllLoanOrderByUserId(long userId) {
        return Optional.of(jdbcTemplate.query(
                SELECT_ALL_WITH_USER_ID,
                new LoanOrderRowMapper(),
                userId
        ));
    }
    @Override
    public UUID addLoanOrder(LoanOrder loanOrder) {
        jdbcTemplate.update(
                INSERT_NEW_ORDER,
                loanOrder.getOrderId(),
                loanOrder.getUserId(),
                loanOrder.getTariffId(),
                loanOrder.getCreditRating(),
                loanOrder.getStatus(),
                loanOrder.getTimeInsert(),
                loanOrder.getTimeUpdate()
        );
        return UUID.fromString(loanOrder.getOrderId());
    }
    @Override
    public Optional<List> getAllOrdersWithStatus (String status){
        return Optional.of(jdbcTemplate.query(
                SELECT_ID_WITH_STATUS,
                new LongMapper(),
                status
        ));
    }
    @Override
    public int updateOrderStatus(long id, String status){
        return jdbcTemplate.update(UPDATE_STATUS, status, id);
    }
    @Override
    public int updateTimeUpdate(long id, Timestamp timeUpdate){
        return jdbcTemplate.update(UPDATE_TIME_UPDATE, timeUpdate, id);
    }
    @Override
    public Optional<String> getOrderStatusById(UUID orderId){
        try {
            return Optional.of(jdbcTemplate.queryForObject(
                    SELECT_STATUS_WITH_ORDER_ID,
                    String.class,
                    orderId.toString()
            ));
        }
        catch(EmptyResultDataAccessException exception){
            return Optional.empty();
        }
    }
    @Override
    public boolean checkExistOrderByUserIdAndOrderId(long userId, UUID orderId){
        return jdbcTemplate.queryForObject(
                CHECK_IF_EXISTS_ORDER_ID_WITH_USER_ID_AND_ORDER_ID,
                Boolean.class,
                userId,
                orderId.toString()
        );
    }
    @Override
    public int deleteLoanOrder(UUID orderId, long userId){
        return jdbcTemplate.update(DELETE_ORDER, userId, orderId.toString());
    }

    @Override
    public void deleteAllOrders() {
            String sql = "TRUNCATE TABLE loan_order";
            jdbcTemplate.execute(sql);
    }
}
