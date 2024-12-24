package com.codecrafter.hitect.services.impl;

import com.codecrafter.hitect.repository.ITruncateRepository;
import com.codecrafter.hitect.services.IResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResetServiceImpl implements IResetService {

    private final ITruncateRepository truncateRepository;
    @Override
    public void truncateTables() {
        // Disable foreign key checks
        truncateRepository.disableForeignKeyChecks();

        // Independent tables (order doesn't matter)
        List<String> independentTables = List.of(
                "special_offer", "reviews", "inquiry_form", "users"
        );
        for (String table : independentTables) {
            truncateRepository.truncateTable(table);
        }

        // Dependent tables (order matters)
        List<String> dependentTables = List.of(
                "image_details", "product", "subcategories", "submaincategories", "maincategories"
        );
        for (String table : dependentTables) {
            truncateRepository.truncateTable(table);
        }

        // Re-enable foreign key checks
        truncateRepository.enableForeignKeyChecks();
    }
}
