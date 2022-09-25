package com.cherrypick.backend.infrastructure.feedback;

import com.cherrypick.backend.domain.feedback.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
