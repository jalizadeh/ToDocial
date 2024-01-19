package com.jalizadeh.todocial.web.utils;


import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import org.springframework.data.domain.Page;

public class Pager {

    private final Page<GymWorkout> workouts;

    public Pager(Page<GymWorkout> workouts) {
        this.workouts = workouts;
    }

    public int getPageIndex() {
        return workouts.getNumber() + 1;
    }

    public int getPageSize() {
        return workouts.getSize();
    }

    public boolean hasNext() {
        return workouts.hasNext();
    }

    public boolean hasPrevious() {
        return workouts.hasPrevious();
    }

    public int getTotalPages() {
        return workouts.getTotalPages();
    }

    public long getTotalElements() {
        return workouts.getTotalElements();
    }

    public boolean indexOutOfBounds() {
        return this.getPageIndex() < 0 || this.getPageIndex() > this.getTotalElements();
    }

}