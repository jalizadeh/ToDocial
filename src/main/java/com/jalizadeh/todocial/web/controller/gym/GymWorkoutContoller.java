package com.jalizadeh.todocial.web.controller.gym;

import com.jalizadeh.todocial.system.service.ServiceTypes;
import com.jalizadeh.todocial.utils.DataUtils;
import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.FlashMessage;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.model.gym.GymWorkout;
import com.jalizadeh.todocial.web.model.gym.types.GymEquipment;
import com.jalizadeh.todocial.web.model.gym.types.GymMuscleCategory;
import com.jalizadeh.todocial.web.repository.GymDayWorkoutRepository;
import com.jalizadeh.todocial.web.repository.GymWorkoutRepository;
import com.jalizadeh.todocial.web.service.storage.StorageFileSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.xml.crypto.Data;
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

    @Autowired
    private StorageFileSystemService storageService;

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


    @GetMapping("/gym/workouts/new")
    public String newWorkout(ModelMap model){
        model.put("settings", settings);
        model.put("PageTitle", "Add new workout");

        model.addAttribute("workout",new GymWorkout());
        model.addAttribute("equipments",GymEquipment.values());
        model.addAttribute("muscleCategories",GymMuscleCategory.values());
        return "gym/workouts-new";
    }


    @PostMapping("/gym/workouts/new")
    public String addNewWorkout(@Valid GymWorkout workout,
                                ModelMap model, BindingResult result, RedirectAttributes redirectAttributes,
                                @RequestParam(value="file", required=false) MultipartFile file){

        model.put("settings", settings);

        if(result.hasErrors()) {
            model.put("error", result.getAllErrors());
            model.addAttribute("equipments",GymEquipment.values());
            model.addAttribute("muscleCategories",GymMuscleCategory.values());
            return "gym/workouts-new";
        }

        if (!file.isEmpty()) {
            String photoname = preparePhotoName(workout.getName(), file.getOriginalFilename());
            storageService.store(file, ServiceTypes.gym, photoname);
            workout.setPhoto(photoname);
        }

        workout.setName(DataUtils.sanitizeQuery(workout.getName()));
        workout.setDescription(DataUtils.sanitizeQuery(workout.getDescription()));
        gymWorkoutRepository.save(workout);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Workout created successfully", FlashMessage.Status.success));
        return "redirect:/gym/workouts";
    }

    private String preparePhotoName(String name, String originalFilename) {
        String wName = DataUtils.sanitizeQuery(name);
        wName = wName.replace(" ", "_");

        String fileExtension = "";
        // If the originalFilename is null or doesn't contain a dot, there's no extension
        if (originalFilename == null || !originalFilename.contains(".")) {
            return wName + ".jpg";
        }

        // Get the substring after the last dot in the filename
        return wName + "." + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }

}
