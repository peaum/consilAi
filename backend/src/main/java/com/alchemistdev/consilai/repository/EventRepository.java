package com.alchemistdev.consilai.repository;

import com.alchemistdev.consilai.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
