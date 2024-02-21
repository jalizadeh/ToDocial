package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.model.eventline.Event;
import com.jalizadeh.todocial.repository.eventline.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;


    public List<Event> findAll() {
        return eventRepository.findAll();
    }
}
