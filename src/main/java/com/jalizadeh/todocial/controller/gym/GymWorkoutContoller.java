package com.jalizadeh.todocial.controller.gym;

import com.jalizadeh.todocial.service.ServiceTypes;
import com.jalizadeh.todocial.service.UserService;
import com.jalizadeh.todocial.utils.DataUtils;
import com.jalizadeh.todocial.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.model.FlashMessage;
import com.jalizadeh.todocial.model.gym.GymPlan;
import com.jalizadeh.todocial.model.gym.GymWorkout;
import com.jalizadeh.todocial.model.gym.GymWorkoutLog;
import com.jalizadeh.todocial.model.gym.dto.GymWorkoutStats;
import com.jalizadeh.todocial.model.gym.types.GymEquipment;
import com.jalizadeh.todocial.model.gym.types.GymMuscleCategory;
import com.jalizadeh.todocial.repository.GymDayWorkoutRepository;
import com.jalizadeh.todocial.repository.GymWorkoutLogRepository;
import com.jalizadeh.todocial.repository.GymWorkoutRepository;
import com.jalizadeh.todocial.service.storage.StorageFileSystemService;
import com.jalizadeh.todocial.utils.Pager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class GymWorkoutContoller {

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private UserService userService;

    @Autowired
    private GymWorkoutRepository gymWorkoutRepository;

    @Autowired
    private GymDayWorkoutRepository gymDayWorkoutRepository;

    @Autowired
    private GymWorkoutLogRepository gymWorkoutLogRepository;

    @Autowired
    private StorageFileSystemService storageService;

    @GetMapping(value = "/gym/workouts")
    public String showWorkouts(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, ModelMap model) {
        model.put("settings", settings);
        model.put("PageTitle", "Workouts");

        // Evaluate page. If requested parameter is null or less than 0 (to
        // prevent exception), return initial size. Otherwise, return value of
        // param. decreased by 1.
        int evalPage = (page.orElse(0) < 1) ? 0 : page.get() - 1;
        int evalSize = size.orElse(20);


        Pageable pageable = PageRequest.of(evalPage, evalSize);
        Page<GymWorkout> workouts = gymWorkoutRepository.findAll(pageable);
        Pager pager = new Pager(workouts);

        model.put("workouts", workouts.toList());
        model.put("pager", pager);
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
                List<GymWorkout> workouts = gymWorkoutRepository.findAllByMuscleCategoryOrderByName(GymMuscleCategory.valueOf(value));
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
        model.put("PageTitle", "Gym - Workout: " + foundWorkout.getName());
        model.put("workout", foundWorkout);


        // the rest is only visible to the owner, not anyone else
        List<GymPlan> plansByWorkoutId = gymDayWorkoutRepository
                .findDistinctPlansByUserIdAndWorkoutId(userService.GetAuthenticatedUser().getId(), foundWorkout.getId());

        if(!plansByWorkoutId.isEmpty()) {
            List<GymWorkoutLog> allLogsForWorkout = gymWorkoutLogRepository.findAllLogsForWorkout(id);

            Map<Date, List<GymWorkoutLog>> logsByDate = groupLogsByDate(allLogsForWorkout);
            List<GymWorkoutStats> logStats = logsByDate.entrySet().stream()
                    .map(e -> calculateAverage(e.getValue()))
                    .collect(Collectors.toList());

            Map<GymPlan, List<Integer>> plansTimeline = planTimelineExtractor(logsByDate);

            model.put("plans", plansByWorkoutId);
            model.put("plansTimeline", plansTimeline);
            model.put("history", allLogsForWorkout);
            model.put("logsByDate", logsByDate);
            model.put("logStats", logStats);
            model.put("viewerIsOwner", true);
        } else {
            model.put("viewerIsOwner", false);
        }

        return "gym/workout";
    }


    @GetMapping(value = "/gym/workouts/{id}/edit")
    @PreAuthorize("hasAuthority('PRIVILEGE_UPDATE')")
    public String editWorkout(ModelMap model, RedirectAttributes redirectAttributes, @PathVariable Long id) {
        Optional<GymWorkout> workout = gymWorkoutRepository.findById(id);

        //if requested to-do id doesnt exists
        if(!workout.isPresent()){
            redirectAttributes.addFlashAttribute("exception", "The requested workout with id "+ id + " doesn't exist");
            return "redirect:/error";
        }

        GymWorkout foundWorkout = workout.get();
        model.put("PageTitle", foundWorkout.getName());
        model.put("settings", settings);
        model.put("workout", foundWorkout);
        model.addAttribute("equipments",GymEquipment.values());
        model.addAttribute("muscleCategories",GymMuscleCategory.values());

        return "gym/workouts-new";
    }

    @PostMapping(value = "/gym/workouts/{id}/edit")
    @PreAuthorize("hasAuthority('PRIVILEGE_UPDATE')")
    public String editWorkout(@Valid GymWorkout workout,
                              ModelMap model, BindingResult result, RedirectAttributes redirectAttributes,
                              @RequestParam(value="file", required=false) MultipartFile file){

        Optional<GymWorkout> w = gymWorkoutRepository.findById(workout.getId());

        if(!w.isPresent() || w.get().getId() != workout.getId()){
            redirectAttributes.addFlashAttribute("exception", "The requested workout with id "+ workout.getId() + " doesn't exist");
            return "redirect:/error";
        }


        GymWorkout foundWorkout = w.get();
        foundWorkout.setName(workout.getName());
        foundWorkout.setDescription(workout.getDescription());
        //TODO: impl new photo
        foundWorkout.setEquipment(workout.getEquipment());
        foundWorkout.setMuscleCategory(workout.getMuscleCategory());

        gymWorkoutRepository.save(foundWorkout);

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Workout updated successfully", FlashMessage.Status.success));
        return "redirect:/gym/workouts";
    }


    @GetMapping(value = "/gym/workouts/{id}/delete")
    @PreAuthorize("hasAuthority('PRIVILEGE_DELETE')")
    public String deleteWorkout(ModelMap model, RedirectAttributes redirectAttributes, @PathVariable Long id) {
        Optional<GymWorkout> workout = gymWorkoutRepository.findById(id);

        if(workout.isPresent()){
           gymWorkoutRepository.delete(workout.get());
        }

        redirectAttributes.addFlashAttribute("flash",
                new FlashMessage("Workout deleted successfully", FlashMessage.Status.warning));
        return "redirect:/gym/workouts";
    }

    @GetMapping("/gym/workouts/new")
    @PreAuthorize("hasAuthority('PRIVILEGE_WRITE')")
    public String newWorkout(ModelMap model){
        model.put("settings", settings);
        model.put("PageTitle", "Add new workout");

        model.addAttribute("workout",new GymWorkout());
        model.addAttribute("equipments",GymEquipment.values());
        model.addAttribute("muscleCategories",GymMuscleCategory.values());
        return "gym/workouts-new";
    }


    @PostMapping("/gym/workouts/new")
    @PreAuthorize("hasAuthority('PRIVILEGE_WRITE')")
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


    public Map<Date, List<GymWorkoutLog>> groupLogsByDate(List<GymWorkoutLog> logs) {
        return logs.stream()
                .collect(Collectors.groupingBy(GymWorkoutLog::getLogDate, LinkedHashMap::new, Collectors.toList()));
    }

    private GymWorkoutStats calculateAverage(List<GymWorkoutLog> logs) {
        if (logs.isEmpty()) {
            return new GymWorkoutStats(0.0, 0.0, 0.0);
        }

        // Format the average with a maximum of 2 decimal places
        DecimalFormat df = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.ENGLISH));


        // Calculate the average, maximum, and minimum of log values
        double sum = logs.stream()
                .mapToDouble(log -> log.getWeight())
                .sum();

        double average = sum / logs.size();
        average = Double.valueOf(df.format(average));

        double max = logs.stream().mapToDouble(l -> l.getWeight()).max().orElse(0.0);
        double min = logs.stream().mapToDouble(l -> l.getWeight()).min().orElse(0.0);

        return new GymWorkoutStats(average, max, min);
    }


    /**
     * Extracts plans as timeline to be shown in the specific chart.
     * The data will be "Map<Plan, <from index, to index>>"
     */
    private Map<GymPlan, List<Integer>> planTimelineExtractor(Map<Date, List<GymWorkoutLog>> logsByDate) {
        List<GymPlan> planList = logsByDate.entrySet().stream()
                .map(e -> e.getValue().get(0).getPwd().getPlan())
                .collect(Collectors.toList());

        Map<GymPlan, List<Integer>> plansTimeline = new HashMap<>();

        int planCounter = 0;
        int from = 0;
        for(GymPlan p : planList){
            List<Integer> fromTo;
            if(!plansTimeline.containsKey(p)){
                fromTo = new ArrayList<>();
                from += planCounter;
                fromTo.add(from);
                fromTo.add(from++);
                plansTimeline.put(p, fromTo);
            } else {
                fromTo = plansTimeline.get(p);
                int f = fromTo.get(0);
                int t = fromTo.get(1);
                fromTo.clear();
                fromTo.add(f);
                fromTo.add(t+1);
                plansTimeline.put(p, fromTo);
                planCounter++;
            }
        }

        return plansTimeline;
    }
}
