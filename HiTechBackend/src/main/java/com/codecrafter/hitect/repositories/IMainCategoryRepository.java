package com.codecrafter.hitect.repositories;

import com.codecrafter.hitect.entities.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMainCategoryRepository extends JpaRepository<MainCategory, Long> {
}
