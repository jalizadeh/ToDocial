package com.jalizadeh.todocial.controller.eventline;


import com.jalizadeh.todocial.model.eventline.Event;
import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import com.jalizadeh.todocial.service.impl.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/eventline")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private SettingsGeneralConfig settings;

    @GetMapping
    public String showHome(){
        return "eventline/home";
    }

    @GetMapping("/month/{id}")
    public String showMonth(@PathVariable Long id, ModelMap model){

        if(id == null || id < 1 || id > 12 )
            return "redirect:/eventline";

        List<Event> events = eventService.findAll();

        model.put("monthId", id);
        model.put("monthName", "January");
        model.put("monthDays", 31);
        model.put("events", events);
        model.put("settings", settings);
        model.put("PageTitle", "Event Timeline");
        return "eventline/month";
    }
}
