package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.model.eventline.Event;
import com.jalizadeh.todocial.repository.eventline.EventRepository;
import com.jalizadeh.todocial.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventService {

    public static final int CURRENT_YEAR = 2024;

    @Autowired
    private EventRepository eventRepository;


    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findAllByMonth(int monthIndex) {
        // Create LocalDateTime object for the first day of the specified month
        LocalDateTime startDateTime = LocalDateTime.of(getCurrentYear(monthIndex), Month.of(monthIndex), 1, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(getCurrentYear(monthIndex), Month.of(monthIndex).plus(1), 1, 0, 0);

        // Convert LocalDateTime objects to Unix timestamps in seconds
        long startTimestamp = startDateTime.toEpochSecond(ZoneOffset.UTC);
        long endTimestamp = endDateTime.toEpochSecond(ZoneOffset.UTC);

        // Convert Unix timestamps to Date objects
        Date startDate = new Date(startTimestamp * 1000); // Multiply by 1000 to convert seconds to milliseconds
        Date endDate = new Date(endTimestamp * 1000); // Multiply by 1000 to convert seconds to milliseconds

        return eventRepository.findAllBetween(startDate, endDate);
    }

    //current year value is used for find results according to [startDate, endDate), so if the month is 12, so it should be next year
    private int getCurrentYear(int monthIndex) {
        return monthIndex != 12 ? CURRENT_YEAR : CURRENT_YEAR + 1;
    }

    public List<Event> findAllByMonthDay(int month, int day) {
        return null;
    }

    public String getMonthName(int monthIndex) {
        return Month.of(monthIndex).toString();
    }


    public int getMonthDays(int monthIndex) {
        return YearMonth.of(getCurrentYear(monthIndex), monthIndex).lengthOfMonth();
    }

    public int getDayOfMonth(Date date) {
        // Create a Calendar instance and set the time to the given date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Get the day of the month
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        return dayOfMonth;
    }
}
