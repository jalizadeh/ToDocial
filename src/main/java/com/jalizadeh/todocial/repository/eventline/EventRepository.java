package com.jalizadeh.todocial.repository.eventline;

import com.jalizadeh.todocial.model.eventline.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query("Select e from Event e where (e.date >= :startDate AND e.date < :endDate) order by e.date")
    List<Event> findAllBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
