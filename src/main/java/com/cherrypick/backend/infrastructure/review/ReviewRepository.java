package com.cherrypick.backend.infrastructure.review;

import com.cherrypick.backend.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}