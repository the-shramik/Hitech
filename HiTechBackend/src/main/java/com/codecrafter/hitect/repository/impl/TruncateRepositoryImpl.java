package com.codecrafter.hitect.repository.impl;

import com.codecrafter.hitect.repository.ITruncateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TruncateRepositoryImpl implements ITruncateRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void disableForeignKeyChecks() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
    }

    @Override
    public void truncateTable(String tableName) {
        jdbcTemplate.execute("TRUNCATE TABLE " + tableName);
    }

    @Override
    public void enableForeignKeyChecks() {
        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
}
