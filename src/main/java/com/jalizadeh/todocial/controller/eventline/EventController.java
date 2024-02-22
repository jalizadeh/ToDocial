package com.jalizadeh.todocial.controller.eventline;


import com.jalizadeh.todocial.model.eventline.Event;
import com.jalizadeh.todocial.model.settings.SettingsGeneralConfig;
import com.jalizadeh.todocial.model.todo.Todo;
import com.jalizadeh.todocial.service.impl.EventService;
import com.jalizadeh.todocial.service.impl.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/eventline")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TodoService todoService;

    @Autowired
    private SettingsGeneralConfig settings;

    @GetMapping
    public String showHome(){
        return "eventline/home";
    }

    @GetMapping("/month_/{monthIndex}")
    public String showMonth_(@PathVariable int monthIndex, ModelMap model){

        if(monthIndex < 1 || monthIndex > 12 )
            return "redirect:/eventline";

        List<Event> events = eventService.findAllByMonth(monthIndex);
        int monthDays = eventService.getMonthDays(monthIndex);

        //put each event in its day
        Map<Integer, List<Event>> eventsMap = new HashMap<>();
        for(int i = 0; i < monthDays; i++){
            int day = i + 1;
            List<Event> dayEvents = new ArrayList<>();
            for(Event e : events) {
                if(day == eventService.getDayOfMonth(e.getDate())) {
                    dayEvents.add(e);
                }
            }

            eventsMap.put(day, dayEvents);
        }

        model.put("monthIndex", monthIndex);
        model.put("monthName", eventService.getMonthName(monthIndex));
        model.put("monthDays", monthDays);
        model.put("events", events);
        model.put("eventsMap", eventsMap);
        model.put("settings", settings);
        model.put("PageTitle", "Event Timeline");

        return "eventline/month";
    }

    @GetMapping("/month/{monthIndex}")
    public String showMonth(@PathVariable int monthIndex, ModelMap model){

        if(monthIndex < 1 || monthIndex > 12 )
            return "redirect:/eventline";

        List<Todo> todos = todoService.findAllPublicByMonth(monthIndex);
        int monthDays = todoService.getMonthDays(monthIndex);

        //put each event in its day
        Map<Integer, List<Todo>> todosMap = new HashMap<>();
        for(int i = 0; i < monthDays; i++){
            int day = i + 1;
            List<Todo> dayEvents = new ArrayList<>();
            for(Todo e : todos) {
                if(day == todoService.getDayOfMonth(e.getTarget_date())) {
                    dayEvents.add(e);
                }
            }

            todosMap.put(day, dayEvents);
        }

        model.put("monthIndex", monthIndex);
        model.put("monthName", todoService.getMonthName(monthIndex));
        model.put("monthDays", monthDays);
        model.put("events", todos);
        model.put("eventsMap", todosMap);
        model.put("settings", settings);
        model.put("PageTitle", "Event Timeline");

        return "eventline/month2";
    }


}
