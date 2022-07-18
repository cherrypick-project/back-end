package com.cherrypick.backend.infrastructure.category;

import com.cherrypick.backend.domain.category.FirstCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FirstCategoryRepository extends JpaRepository<FirstCategory, Long> {

}
