package com.jalizadeh.todocial.service.impl;

import com.jalizadeh.todocial.model.gym.GymDay;
import com.jalizadeh.todocial.model.gym.GymPlan;
import com.jalizadeh.todocial.model.gym.GymPlanWeekDay;
import com.jalizadeh.todocial.model.gym.dto.GymWorkoutLogSetRep_DTO;
import com.jalizadeh.todocial.repository.gym.*;
import com.jalizadeh.todocial.utils.GymUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymService {

    @Autowired
    private GymPlanRepository gymRepository;

    @Autowired
    private GymPlanWeekDayRepository gymWeekRepository;

    @Autowired
    private GymDayRepository gymDayRepository;

    @Autowired
    private GymDayWorkoutRepository gymDayWorkoutRepository;

    @Autowired
    private GymWorkoutLogRepository gymWorkoutLogRepository;


    public List<GymPlan> findAll() {
        return gymRepository.findAll();
    }

    public List<GymPlanWeekDay> findAllWeeksByPlanId(Long planId) {
        return gymWeekRepository.findAllByPlanId(planId);
    }

    public List<GymDay> findAllDaysByPlanId(Long planId) {
        return gymDayRepository.findAllByPlanId(planId);
    }

    public List<GymWorkoutLogSetRep_DTO> parseLogNote(String log) {
        return GymUtils.workoutLogNoteParser(log);
    }

    public List<GymPlan> findAllPlansByUserIdAndIsPublicTrue(Long id) {
        return gymRepository.findAllByUserIdAndIsPublicTrue(id);
    }

    public List<GymPlan> findAllPlansByUserId(Long id) {
        return gymRepository.findAllByUserId(id);
    }

    public List<GymPlan> searchAllPlans(String q) {
        return gymRepository.searchAll(q);
    }
}
