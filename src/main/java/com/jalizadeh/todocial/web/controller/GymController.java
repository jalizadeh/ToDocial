package com.jalizadeh.todocial.web.controller;


import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.FlashMessage;
import com.jalizadeh.todocial.web.model.gym.*;
import com.jalizadeh.todocial.web.model.gym.types.GymMainGoal;
import com.jalizadeh.todocial.web.model.gym.types.GymTrainingLevel;
import com.jalizadeh.todocial.web.model.gym.types.GymWorkoutType;
import com.jalizadeh.todocial.web.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.*;

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
    private GymWorkoutLogRepository gymWorkoutLogRepository;

    @Autowired
    private GymPlanWeekDayRepository gymplanWeekDayRepository;

    @Autowired
    private GymWorkoutRepository gymWorkoutRepository;

    private static GymPlan newPlan;
    private static GymPlanIntroduction newPlanIntroduction;

    private final List<Integer> WEEKS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
    private final List<Integer> DAYS = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

    @RequestMapping(value = "/gym", method = RequestMethod.GET)
    public String homepage(ModelMap model) {
        newPlan = null;

        model.put("settings", settings);
        model.put("PageTitle", "Gym");
        model.put("activePlans", gymPlanRepository.findAllByActiveTrue());
        model.put("allPlans", gymPlanRepository.findAll());

        return "gym/home";
    }

    @RequestMapping(value = "/gym/plan/new", method = RequestMethod.GET)
    public String addNewPlan(ModelMap model, @RequestParam Long step, RedirectAttributes redirectAttributes) {
        model.put("settings", settings);
        model.put("PageTitle", "Gym - New Plan");

        if (step == 1) {
            model.put("weeks", WEEKS);
            model.put("days", DAYS);

            if (newPlan == null) model.addAttribute("plan", new GymPlan());
            else model.addAttribute("plan", newPlan);

            return "gym/new_plan_step1";
        } else if (step == 2) {
            model.put("weeks", WEEKS);
            model.put("mainGoals", GymMainGoal.values());
            model.put("workoutTypes", GymWorkoutType.values());
            model.put("trainingLevels", GymTrainingLevel.values());
            model.put("workoutsList", gymWorkoutRepository.findAll());
            //newPlan.setWorkouts(gymWorkoutRepository.findAll());
            model.addAttribute("plan", newPlan);
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

        if (result.hasErrors()) {
            model.put("error", result.getAllErrors());
            return "/gym";
        }

        if (step == 1) {
            newPlan = plan;
            newPlan.setGymPlanIntroduction(new GymPlanIntroduction());

            List<GymDay> gymDaysList = new ArrayList<>();
            for (int i = 0; i < plan.getNumberOfDays(); i++) {
                GymDay day = new GymDay();
                day.setDayNumber((i + 1));
                day.setProgress(0);
                gymDaysList.add(day);
            }
            newPlan.setDays(gymDaysList);

            return "redirect:/gym/plan/new?step=2";
        } else if (step == 2) {
            newPlan = plan;
            //System.err.println(newPlan);

            GymPlan savedPlan = gymPlanRepository.save(newPlan);

            newPlan.getDays().forEach(d -> {
                List<GymDayWorkout> dayWorkouts = d.getDayWorkouts();

                d.setPlan(savedPlan);
                d.setTotalWorkouts(dayWorkouts.size());
                GymDay savedDay = gymDayRepository.save(d);

                for (int i = 0; i < dayWorkouts.size(); i++) {
                    GymDayWorkout w = dayWorkouts.get(i);
                    w.setWorkout(gymWorkoutRepository.getOne(dayWorkouts.get(i).getWorkout().getId()));
                    w.setDay(savedDay);
                    w.setWorkoutNumber(i);
                    gymDayWorkoutRepository.save(w);
                }
            });

            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("Plan " + newPlan.getTitle() + " with desc : " + newPlan.getGymPlanIntroduction().getMoreInfo(), FlashMessage.Status.success));
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


    @GetMapping(value = "gym/plan/{planId}")
    public String showPlan(ModelMap model, @PathVariable Long planId, RedirectAttributes redirectAttributes) {
        Optional<GymPlan> plan = gymPlanRepository.findById(planId);

        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + planId + " doesn't exist");
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

    //@GetMapping(value = "gym/plan/{planId}/week/{week}/day/{dayId}")
    public String showDays(ModelMap model,
                           @PathVariable Long planId, @PathVariable Long week, @PathVariable Long dayId,
                           RedirectAttributes redirectAttributes) {
        Optional<GymPlan> plan = gymPlanRepository.findById(planId);

        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + planId + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        GymDay dayOfPlan = gymDayRepository.findById(dayId).get();
        List<GymDayWorkout> dayWorkouts = gymDayWorkoutRepository.findAllByDayId(dayId);

        //List<GymWorkoutLog> allLogsByWeekAndDayWorkout = gymWorkoutLogRepository.findAllByWeekAndDayWorkout(week, dayOfPlan);


        model.put("settings", settings);
        model.put("PageTitle", "Gym - Plan: " + foundPlan.getTitle());
        model.put("plan", foundPlan);
        model.put("week", week);
        model.put("day", dayOfPlan);
        model.put("dayWorkouts", dayWorkouts);

        return "gym/day";
    }

    @GetMapping(value = "gym/plan/{planId}/week/{week}/day/{day}")
    public String showDayWorkoutsByDayNumber(ModelMap model,
                           @PathVariable Long planId, @PathVariable Long week, @PathVariable Long day,
                           RedirectAttributes redirectAttributes) {
        Optional<GymPlan> plan = gymPlanRepository.findById(planId);

        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + planId + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        GymDay dayOfPlan = gymDayRepository.findByPlanIdAndDayNumber(planId, day);
        List<GymDayWorkout> dayWorkouts = gymDayWorkoutRepository.findAllByDayId(dayOfPlan.getId());

        //if this is the first time the user opens this plan-week-day, add a new record in DB
        Optional<GymPlanWeekDay> pwd = gymplanWeekDayRepository.findAllByPlanIdAndWeekNumberAndDayNumber(planId, week, day);
        if(!pwd.isPresent()){
            gymplanWeekDayRepository.save(new GymPlanWeekDay(foundPlan, week, day, 0));
            model.put("pwdId", -1);
        } else {
            GymPlanWeekDay foundPWD = pwd.get();
            model.put("pwdId", foundPWD.getId());
        }


        model.put("settings", settings);
        model.put("PageTitle", "Gym - Plan: " + foundPlan.getTitle());
        model.put("plan", foundPlan);
        model.put("week", week);
        model.put("day", dayOfPlan);

        // Each dayWorkout has access to all its own logs
        // On the FE side, i will compare the current `pwd.id` with `log.pwd.id`
        model.put("dayWorkouts", dayWorkouts);

        return "gym/day";
    }

    @GetMapping(value = "gym/plan/{planId}/week/{week}/day/{dayId}/log_workout/{workoutId}")
    public String addWorkoutLog(ModelMap model,
                                @PathVariable Long planId, @PathVariable Long week,
                                @PathVariable Long dayId, @PathVariable Long workoutId, RedirectAttributes redirectAttributes) {
        Optional<GymPlan> plan = gymPlanRepository.findById(planId);

        GymDayWorkout workout = gymDayWorkoutRepository.findById(workoutId).get();
        workout.setProgress(100);
        gymDayWorkoutRepository.save(workout);

        GymDay day = gymDayRepository.findById(dayId).get();
        day.setProgress(Math.min(100, day.getProgress() + (int) Math.ceil(100.0 / day.getTotalWorkouts())));
        gymDayRepository.save(day);


        return "redirect:/gym/plan/" + planId + "/week/" + week + "/day/" + dayId;
    }


    //@PostMapping("gym/plan/{planId}/week/{week}/day/{dayId}/workout/{workoutId}/add-workout-log")
    public String addSingleWorkoutLogXXXX(@Valid GymWorkoutLog workoutLog,
                                  @PathVariable Long planId, @PathVariable Long week,
                                  @PathVariable Long dayId, @PathVariable Long workoutId,
                                  BindingResult result, RedirectAttributes redirectAttributes, ModelMap model) {

        if (result.hasErrors()) {
            model.put("error", result.getAllErrors());
            return "/gym";
        }

        GymDayWorkout dayWorkout = gymDayWorkoutRepository.findById(workoutId).get();

        //add the new workout log
        workoutLog.setDayWorkout(dayWorkout);
        workoutLog.setLogDate(new Date());
        gymWorkoutLogRepository.save(workoutLog);

        //update the progress only when the first set is logged
        if(workoutLog.getSetNumber() == 1){
            //set the progress of that workout of the day to 100%
            dayWorkout.setProgress(100);
            gymDayWorkoutRepository.save(dayWorkout);

            //update the overall progress of that day
            GymDay day = gymDayRepository.findById(dayId).get();
            day.setProgress(Math.min(100, day.getProgress() + (int) Math.ceil(100.0 / day.getTotalWorkouts())));
            gymDayRepository.save(day);
        }


        return "redirect:/gym/plan/" + planId + "/week/" + week + "/day/" + dayId;
    }

    @PostMapping("gym/plan/{planId}/week/{week}/day/{day}/workout/{workoutId}/add-workout-log")
    public String addSingleWorkoutLog(@Valid GymWorkoutLog workoutLog,
                                      @PathVariable Long planId, @PathVariable Long week,
                                      @PathVariable Long day, @PathVariable Long workoutId,
                                      BindingResult result, RedirectAttributes redirectAttributes, ModelMap model) {

        if (result.hasErrors()) {
            model.put("error", result.getAllErrors());
            return "/gym";
        }

        GymDayWorkout dayWorkout = gymDayWorkoutRepository.findById(workoutId).get();
        GymPlanWeekDay pwd = gymplanWeekDayRepository.findAllByPlanIdAndWeekNumberAndDayNumber(planId, week, day).get();

        //add the new workout log
        workoutLog.setPwd(pwd);
        workoutLog.setDayWorkout(dayWorkout);
        workoutLog.setLogDate(new Date());
        gymWorkoutLogRepository.save(workoutLog);

        /*
        //update the progress only when the first set is logged
        if(workoutLog.getSetNumber() == 1){
            //set the progress of that workout of the day to 100%
            dayWorkout.setProgress(100);
            gymDayWorkoutRepository.save(dayWorkout);

            //update the overall progress of that day
            GymDay day = gymDayRepository.findById(dayId).get();
            day.setProgress(Math.min(100, day.getProgress() + (int) Math.ceil(100.0 / day.getTotalWorkouts())));
            gymDayRepository.save(day);
        }
        */

        return "redirect:/gym/plan/" + planId + "/week/" + week + "/day/" + day;
    }

}