package com.jalizadeh.todocial.web.controller;


import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.FlashMessage;
import com.jalizadeh.todocial.web.model.gym.*;
import com.jalizadeh.todocial.web.model.gym.types.GymMainGoal;
import com.jalizadeh.todocial.web.model.gym.types.GymTrainingLevel;
import com.jalizadeh.todocial.web.model.gym.types.GymWorkoutType;
import com.jalizadeh.todocial.web.repository.GymDayRepository;
import com.jalizadeh.todocial.web.repository.GymDayWorkoutRepository;
import com.jalizadeh.todocial.web.repository.GymPlanRepository;
import com.jalizadeh.todocial.web.repository.GymWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class GymController {

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private GymPlanRepository gymPlanRepository;

    @Autowired
    private GymDayRepository gymDayRepository;

    @Autowired
    private GymDayWorkoutRepository gymDayWorkoutRepository;

    @Autowired
    private GymWorkoutRepository gymWorkoutRepository;

    private static GymPlan newPlan;
    private static GymPlanIntroduction newPlanIntroduction;

    private final List<Integer> WEEKS = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16);
    private final List<Integer> DAYS = Arrays.asList(1,2,3,4,5,6,7);

    @RequestMapping(value = "/gym", method = RequestMethod.GET)
    public String homepage(ModelMap model){
        newPlan = null;

        model.put("settings", settings);
        model.put("PageTitle", "Gym");
        model.put("activePlans", gymPlanRepository.findAllByActiveTrue());
        model.put("allPlans", gymPlanRepository.findAll());

        return "gym/home";
    }

    @RequestMapping(value = "/gym/plan/new", method = RequestMethod.GET)
    public String addNewPlan(ModelMap model, @RequestParam Long step, RedirectAttributes redirectAttributes){
        model.put("settings", settings);
        model.put("PageTitle", "Gym - New Plan");

        if(step == 1){
            model.put("weeks", WEEKS);
            model.put("days", DAYS);

            if(newPlan == null) model.addAttribute("plan",new GymPlan());
            else                model.addAttribute("plan",newPlan);

            return "gym/new_plan_step1";
        } else if (step == 2){
            model.put("weeks", WEEKS);
            model.put("mainGoals", GymMainGoal.values());
            model.put("workoutTypes", GymWorkoutType.values());
            model.put("trainingLevels", GymTrainingLevel.values());
            model.put("workoutsList", gymWorkoutRepository.findAll());
            //newPlan.setWorkouts(gymWorkoutRepository.findAll());
            model.addAttribute("plan",newPlan);
            return "gym/new_plan_step2";
        }

        redirectAttributes.addFlashAttribute("exception", "Something went wrong, try again.");
        return "redirect:/gym";
    }

    @PostMapping("/gym/plan/new")
    public String addNewPlanStep2(@Valid GymPlan plan, @RequestParam Long step,
                                  BindingResult result, RedirectAttributes redirectAttributes, ModelMap model) {
        model.put("settings", settings);
        model.put("PageTitle", "Gym - New Plan");

        if(result.hasErrors()) {
            model.put("error", result.getAllErrors());
            return "/gym";
        }

        if(step == 1){
            newPlan = plan;
            newPlan.setGymPlanIntroduction(new GymPlanIntroduction());

            List<GymDay> gymDaysList = new ArrayList<>();
            for(int i = 0; i < plan.getNumberOfDays();i++){
                GymDay day = new GymDay();
                day.setDayNumber((long) (i+1));
                day.setProgress(0);
                gymDaysList.add(day);
            }
            newPlan.setDays(gymDaysList);

            return "redirect:/gym/plan/new?step=2";
        } else if (step == 2){
            newPlan = plan;
            //System.err.println(newPlan);

            GymPlan savedPlan = gymPlanRepository.save(newPlan);

            newPlan.getDays().forEach(d -> {
                d.setPlan(savedPlan);
                GymDay savedDay = gymDayRepository.save(d);

                List<GymDayWorkout> dayWorkouts = d.getDayWorkouts();
                for(int i = 0; i < dayWorkouts.size(); i++){
                    GymDayWorkout w = dayWorkouts.get(i);
                    w.setWorkout(gymWorkoutRepository.getOne(dayWorkouts.get(i).getWorkout().getId()));
                    w.setDay(savedDay);
                    w.setWorkoutNumber(i);
                    gymDayWorkoutRepository.save(w);
                }
            });

            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("Plan " + newPlan.getTitle() + " with desc : " + newPlan.getGymPlanIntroduction().getMoreInfo() , FlashMessage.Status.success));
            return "redirect:/gym";
        }

        return "redirect:/gym";
    }


    @GetMapping("/gym/plan/{id}/delete")
    public String deletePlan(@PathVariable Long id, RedirectAttributes redirectAttributes, ModelMap model) {
            //TODO: if only the plan is not active
            gymPlanRepository.deleteById(id);

            return "redirect:/gym";
    }


    @RequestMapping(value = "gym/plan/{planId}", method = RequestMethod.GET)
    public String showPlan(ModelMap model, @PathVariable Long planId, RedirectAttributes redirectAttributes){
        Optional<GymPlan> plan = gymPlanRepository.findById(planId);

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
        Optional<GymPlan> plan = gymPlanRepository.findById(planId);

        if(!plan.isPresent()){
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id "+ planId + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        GymDay dayOfPlan = gymDayRepository.findById(dayId).get();
        List<GymDayWorkout> dayWorkouts = gymDayWorkoutRepository.findAllByDayId(dayId);

        model.put("settings", settings);
        model.put("PageTitle", "Gym - Plan: " + foundPlan.getTitle());
        model.put("plan", foundPlan);
        model.put("week", weekId);
        model.put("day", dayOfPlan);
        model.put("dayWorkouts", dayWorkouts);

        return "gym/day";
    }

}
