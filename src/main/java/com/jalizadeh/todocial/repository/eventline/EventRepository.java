package com.jalizadeh.todocial.repository.eventline;

import com.jalizadeh.todocial.model.eventline.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
