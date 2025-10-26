package com.example.proyecto_pi3_backend.Feedback.infrastructure;

import com.example.proyecto_pi3_backend.Feedback.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
