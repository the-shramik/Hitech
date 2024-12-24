package com.codecrafter.hitect.repository;

public interface ITruncateRepository {
    void disableForeignKeyChecks();
    void truncateTable(String tableName);
    void enableForeignKeyChecks();
}
