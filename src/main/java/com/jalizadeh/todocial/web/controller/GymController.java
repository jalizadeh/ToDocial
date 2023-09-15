package com.jalizadeh.todocial.web.controller;


import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.gym.GymDay;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import com.jalizadeh.todocial.web.repository.GymDayRepository;
import com.jalizadeh.todocial.web.repository.GymRepository;
import com.jalizadeh.todocial.web.repository.GymWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class GymController {

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private GymRepository gymRepository;

    @Autowired
    private GymDayRepository gymDayRepository;

    @Autowired
    private GymWorkoutRepository gymWorkoutRepository;

    @RequestMapping(value = "/gym", method = RequestMethod.GET)
    public String homepage(ModelMap model){
        model.put("settings", settings);
        model.put("PageTitle", "Gym");
        model.put("plans", gymRepository.findAllByActiveTrue());

        return "gym/home";
    }


    @RequestMapping(value = "gym/plan/{planId}", method = RequestMethod.GET)
    public String showPlan(ModelMap model, @PathVariable Long planId, RedirectAttributes redirectAttributes){
        Optional<GymPlan> plan = gymRepository.findById(planId);

        if(!plan.isPresent()){
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id "+ planId + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        List<GymDay> daysOfPlan = gymDayRepository.findAllByPlanIdOrderByDayNumber(planId);

        model.put("settings", settings);
        model.put("PageTitle", "Gym - Plan: " + foundPlan.getTitle());
        model.put("plan", foundPlan);
        model.put("days", daysOfPlan);

        return "gym/plan";
    }

    @RequestMapping(value = "gym/plan/{planId}/week/{weekId}/day/{dayId}", method = RequestMethod.GET)
    public String showDays(ModelMap model,
                           @PathVariable Long planId, @PathVariable Long weekId, @PathVariable Long dayId,
                           RedirectAttributes redirectAttributes){
        Optional<GymPlan> plan = gymRepository.findById(planId);

        if(!plan.isPresent()){
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id "+ planId + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        GymDay dayOfPlan = gymDayRepository.findByDayNumber(dayId);
        List<GymWorkout> workoutsOfDay = gymWorkoutRepository.findAllByPlanIdAndDayId(planId, dayId);

        model.put("settings", settings);
        model.put("PageTitle", "Gym - Plan: " + foundPlan.getTitle());
        model.put("plan", foundPlan);
        model.put("week", weekId);
        model.put("day", dayOfPlan);
        model.put("workouts", workoutsOfDay);

        return "gym/day";
    }

}
