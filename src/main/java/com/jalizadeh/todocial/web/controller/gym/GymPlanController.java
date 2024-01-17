package com.jalizadeh.todocial.web.controller.gym;


import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.FlashMessage;
import com.jalizadeh.todocial.web.model.gym.*;
import com.jalizadeh.todocial.web.model.gym.dto.GymWorkoutLogSetRep_DTO;
import com.jalizadeh.todocial.web.model.gym.types.GymMainGoal;
import com.jalizadeh.todocial.web.model.gym.types.GymTrainingLevel;
import com.jalizadeh.todocial.web.model.gym.types.GymWorkoutType;
import com.jalizadeh.todocial.web.repository.*;
import com.jalizadeh.todocial.web.utils.GymUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class GymPlanController {

    @Autowired
    private UserService userService;

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

    @GetMapping(value = "/gym/plan/new", params = {"step"})
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
            newPlan.setUser(userService.GetAuthenticatedUser());
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

            //reset already saved plan
            newPlan = new GymPlan();

            return "redirect:/gym";
        }

        return "redirect:/gym";
    }

    @GetMapping("/gym/plan/{id}/edit")
    public String editPlan(@PathVariable Long id, RedirectAttributes redirectAttributes, ModelMap model) {
        Optional<GymPlan> plan = gymPlanRepository.findById(id);

        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + id + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        if(foundPlan.getUser().getId() != userService.GetAuthenticatedUser().getId()){
            redirectAttributes.addFlashAttribute("exception", "This plan doesnt belong to you");
            return "redirect:/error";
        }

        if(foundPlan.isActive()){
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("The workout is active and can not be edited. First end it and try again", FlashMessage.Status.danger));
            return "redirect:/gym";
        }

        model.put("weeks", WEEKS);
        model.put("mainGoals", GymMainGoal.values());
        model.put("workoutTypes", GymWorkoutType.values());
        model.put("trainingLevels", GymTrainingLevel.values());
        model.put("workoutsList", gymWorkoutRepository.findAll());
        model.addAttribute("plan", foundPlan);

        return "gym/plan-edit";
    }

    @PostMapping("/gym/plan/{id}/edit")
    public String editPlan(@Valid GymPlan plan, @PathVariable Long id,
                                  BindingResult result, RedirectAttributes redirectAttributes, ModelMap model) {
        model.put("settings", settings);
        model.put("PageTitle", "Gym");

        if (result.hasErrors()) {
            model.put("error", result.getAllErrors());
            return "/gym";
        }

        GymPlan foundPlan = gymPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found with id: " + id));

        foundPlan.setUser(userService.GetAuthenticatedUser());
        foundPlan.setTitle(plan.getTitle());
        foundPlan.setGymPlanIntroduction(plan.getGymPlanIntroduction());
        //TODO: the rest
        foundPlan.setIsPublic(plan.getIsPublic());
        foundPlan.setIsForSale(plan.getIsForSale());
        foundPlan.setPrice(plan.getPrice());
        foundPlan.setUpdatedAt(Date.valueOf(LocalDate.now()));

        gymPlanRepository.save(foundPlan);

        return "redirect:/gym/plan/" + foundPlan.getId();
    }

    @GetMapping("/gym/plan/{id}/delete")
    public String deletePlan(@PathVariable Long id, RedirectAttributes redirectAttributes, ModelMap model) {
        Optional<GymPlan> plan = gymPlanRepository.findById(id);

        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + id + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        if(foundPlan.isActive()){
            redirectAttributes.addFlashAttribute("flash",
                    new FlashMessage("The workout is active and can not be deleted. First end it and try again", FlashMessage.Status.danger));
            return "redirect:/gym";
        }

        //at first, remove its associated day_workouts
        List<GymDay> plansDays = foundPlan.getDays();
        plansDays.stream()
                .forEach(day -> {
                    List<GymDayWorkout> allDayWorkouts = gymDayWorkoutRepository.findAllByDayId(day.getId());
                    gymDayWorkoutRepository.deleteInBatch(allDayWorkouts);
                });

        gymPlanRepository.deleteById(id);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("The workout deleted successfully", FlashMessage.Status.warning));
        return "redirect:/gym";
    }


    @GetMapping("/gym/plan/{id}/start-plan")
    public String startPlan(@PathVariable Long id, RedirectAttributes redirectAttributes, ModelMap model) {
        Optional<GymPlan> plan = gymPlanRepository.findById(id);
        if(!plan.isPresent())
            return "redirect:/gym";

        GymPlan foundPlan = plan.get();
        if(foundPlan.isActive())
            return "redirect:/gym";

        foundPlan.setActive(true);
        foundPlan.setStartDate(Date.valueOf(LocalDate.now()));
        gymPlanRepository.save(foundPlan);

        return "redirect:/gym";
    }


    //mark plan as completed
    @GetMapping("/gym/plan/{id}/end-plan")
    public String endPlan(@PathVariable Long id, RedirectAttributes redirectAttributes, ModelMap model) {
        Optional<GymPlan> plan = gymPlanRepository.findById(id);
        if(!plan.isPresent())
            return "redirect:/gym";

        GymPlan foundPlan = plan.get();
        if(!foundPlan.isActive())
            return "redirect:/gym";

        foundPlan.setActive(false);
        foundPlan.setCompleteDate(Date.valueOf(LocalDate.now()));
        gymPlanRepository.save(foundPlan);

        return "redirect:/gym";
    }


    @GetMapping(value = "gym/plan/{id}")
    public String showPlan(ModelMap model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<GymPlan> plan = gymPlanRepository.findById(id);

        if (!plan.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested plan with id " + id + " doesn't exist");
            return "redirect:/error";
        }

        GymPlan foundPlan = plan.get();
        List<GymDay> daysOfPlan = gymDayRepository.findAllByPlanIdOrderByDayNumber(id);

        List<GymPlanWeekDay> pwd = gymplanWeekDayRepository.findAllByPlanId(id);

        List<GymWorkoutLog> allSessionsForPlan = gymWorkoutLogRepository.findAllSessionsForPlan(foundPlan.getId());

        List<Object[]> workoutsByMuscleGroup = gymWorkoutLogRepository.workoutsByMuscleGroup(foundPlan.getId());

        List<Object[]> muscleGroupsInPlan = gymPlanRepository.muscleGroupsInPlan(foundPlan.getId());
        long countOfAllMuscleWorkouts = muscleGroupsInPlan.stream()
                .mapToLong(o -> ((Number) o[1]).longValue())
                .sum();

        model.put("settings", settings);
        model.put("PageTitle", "Gym - Plan: " + foundPlan.getTitle());
        model.put("plan", foundPlan);
        model.put("allSessionsForPlan", allSessionsForPlan);
        model.put("workoutsByMuscleGroup", workoutsByMuscleGroup);
        model.put("muscleGroupsInPlan", muscleGroupsInPlan);
        model.put("countOfAllMuscleWorkouts", countOfAllMuscleWorkouts);
        model.put("days", daysOfPlan);
        model.put("pwd", pwd);

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
            model.put("pwd", null);
        } else {
            GymPlanWeekDay foundPWD = pwd.get();
            model.put("pwdId", foundPWD.getId());
            model.put("pwd", foundPWD);
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


    @PostMapping("gym/plan/{planId}/week/{week}/day/{day}/workout/{workoutId}/add-single-workout-log")
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

        Date logDate = Date.valueOf(LocalDate.now());

        //add the new workout log
        workoutLog.setPwd(pwd);
        workoutLog.setDayWorkout(dayWorkout);
        workoutLog.setLogDate(logDate);
        gymWorkoutLogRepository.save(workoutLog);

        //set the PWD workoutDate only once
        if(pwd.getWorkoutDate() == null) {
            pwd.setWorkoutDate(logDate);
            gymplanWeekDayRepository.save(pwd);
        }

        updateProgessInDB(planId, day, pwd, dayWorkout, workoutLog);

        return "redirect:/gym/plan/" + planId + "/week/" + week + "/day/" + day;
    }


    @PostMapping("gym/plan/{planId}/week/{week}/day/{day}/workout/{workoutId}/add-workout-log-note")
    public String addSingleWorkoutLogByNote(@RequestBody String lognote,
                                      @PathVariable Long planId, @PathVariable Long week,
                                      @PathVariable Long day, @PathVariable Long workoutId,
                                      BindingResult result, RedirectAttributes redirectAttributes, ModelMap model) {

        if (result.hasErrors()) {
            model.put("error", result.getAllErrors());
            return "/gym";
        }

        GymDayWorkout dayWorkout = gymDayWorkoutRepository.findById(workoutId).get();
        GymPlanWeekDay pwd = gymplanWeekDayRepository.findAllByPlanIdAndWeekNumberAndDayNumber(planId, week, day).get();

        String note = GymUtils.parseRawInput(lognote.split("=")[1]);
        List<GymWorkoutLogSetRep_DTO> listWorkoutLogs = GymUtils.workoutLogNoteParser(note);

        Date logDate = Date.valueOf(LocalDate.now());

        for (int i = 0; i < listWorkoutLogs.size(); i++) {
            GymWorkoutLog workoutLog = new GymWorkoutLog();
            workoutLog.setPwd(pwd);
            workoutLog.setDayWorkout(dayWorkout);
            workoutLog.setSetNumber(i + 1);
            workoutLog.setWeight(listWorkoutLogs.get(i).getWeight());
            workoutLog.setReps(listWorkoutLogs.get(i).getRep());
            workoutLog.setLogDate(logDate);
            gymWorkoutLogRepository.save(workoutLog);

            updateProgessInDB(planId, day, pwd, dayWorkout, workoutLog);
        }

        //set the PWD workoutDate only once
        if(pwd.getWorkoutDate() == null) {
            pwd.setWorkoutDate(logDate);
            gymplanWeekDayRepository.save(pwd);
        }

        return "redirect:/gym/plan/" + planId + "/week/" + week + "/day/" + day;
    }

    private void updateProgessInDB(Long planId, Long day, GymPlanWeekDay pwd, GymDayWorkout dayWorkout, GymWorkoutLog workoutLog) {
        //update the progress only when the first set is logged, the other sets wont count
        if(workoutLog.getSetNumber() == 1){
            //set the progress of that workout of the day to 100%
            dayWorkout.setProgress(100);
            gymDayWorkoutRepository.save(dayWorkout);

            //update the overall progress of that PWD
            long totalWorkoutsOfDay = gymDayRepository.findByPlanIdAndDayNumber(planId, day).getTotalWorkouts();
            int weekProgress = Math.min(100, pwd.getProgress() + (int) Math.ceil(100.0 / totalWorkoutsOfDay));
            pwd.setProgress(weekProgress);
            gymplanWeekDayRepository.save(pwd);

            if(weekProgress == 100){
                GymPlan plan = pwd.getPlan();
                plan.setCompletedDays(plan.getCompletedDays() + 1);
                if(plan.getCompletedDays() == plan.getNumberOfWeeks() * plan.getNumberOfDays())
                    plan.setProgress(100);
                else
                    plan.setProgress((int)((plan.getCompletedDays() * 100) / (plan.getNumberOfWeeks() * plan.getNumberOfDays())));
                gymPlanRepository.save(plan);
            }
        }
    }


    @GetMapping("/gym/filter-plan")
    public String filterPlanByLevel(ModelMap model, @RequestParam String level){
        GymTrainingLevel trainingLevel = GymTrainingLevel.valueOf(level.toUpperCase());
        if(trainingLevel == null)
            return "/gym";

        model.put("plans", gymPlanRepository.findAllByTrainingLevel(trainingLevel));

        return "gym/filtered";
    }

}