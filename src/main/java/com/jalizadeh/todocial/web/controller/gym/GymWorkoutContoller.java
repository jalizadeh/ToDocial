package com.jalizadeh.todocial.web.controller.gym;

import com.jalizadeh.todocial.utils.DataUtils;
import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import com.jalizadeh.todocial.web.model.gym.types.GymMuscleCategory;
import com.jalizadeh.todocial.web.repository.GymDayWorkoutRepository;
import com.jalizadeh.todocial.web.repository.GymWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class GymWorkoutContoller {

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private GymWorkoutRepository gymWorkoutRepository;

    @Autowired
    private GymDayWorkoutRepository gymDayWorkoutRepository;

    @GetMapping(value = "/gym/workouts")
    public String showWorkouts(ModelMap model) {
        model.put("workouts", gymWorkoutRepository.findAll());
        model.put("muscleCategories", GymMuscleCategory.values());
        return "gym/workouts";
    }

    @GetMapping(value = "/gym/workouts", params = {"filter", "value"})
    public String filterWorkouts(ModelMap model, RedirectAttributes redirectAttributes,
                                 @RequestParam(name="filter", defaultValue="muscle") String filter,
                                 @RequestParam(name="value", defaultValue="chest") String value) {
        filter = DataUtils.sanitizeQuery(filter);
        value = DataUtils.sanitizeQuery(value);

        model.put("settings", settings);
        model.put("PageTitle", "Filter workout by " + filter.toUpperCase());
        model.put("target", filter.toUpperCase());
        model.put("value", value);
        model.put("muscleCategories", GymMuscleCategory.values());

        switch (filter){
            case "muscle":
                List<GymWorkout> workouts = gymWorkoutRepository.findAllByMuscleCategory(GymMuscleCategory.valueOf(value));
                model.put("workouts", workouts);
                break;
            default:
                redirectAttributes.addFlashAttribute("exception", "The search criteria is not correct");
                return "redirect:/error";
        }

        return "gym/workouts";
    }

    @GetMapping(value = "/gym/workouts/{id}")
    public String showWorkout(ModelMap model, RedirectAttributes redirectAttributes, @PathVariable Long id) {
        Optional<GymWorkout> workout = gymWorkoutRepository.findById(id);

        model.put("settings", settings);

        if (!workout.isPresent()) {
            redirectAttributes.addFlashAttribute("exception", "The requested workout with id " + id + " doesn't exist");
            return "redirect:/error";
        }

        GymWorkout foundWorkout = workout.get();
        List<GymPlan> plansByWorkoutId = gymDayWorkoutRepository.findPlansByWorkoutId(foundWorkout.getId());

        model.put("PageTitle", "Gym - Workout: " + foundWorkout.getName());
        model.put("workout", foundWorkout);
        model.put("plans", plansByWorkoutId);

        return "gym/workout";
    }
}
