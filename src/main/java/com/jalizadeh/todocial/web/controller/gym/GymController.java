package com.jalizadeh.todocial.web.controller.gym;


import com.jalizadeh.todocial.system.service.UserService;
import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.FlashMessage;
import com.jalizadeh.todocial.web.model.User;
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
public class GymController {

    @Autowired
    private UserService userService;

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private GymPlanRepository gymPlanRepository;

    @RequestMapping(value = "/gym", method = RequestMethod.GET)
    public String homepage(ModelMap model) {
        Long userId = userService.GetAuthenticatedUser().getId();

        model.put("settings", settings);
        model.put("PageTitle", "Gym");
        model.put("activePlans", gymPlanRepository.findAllByUserIdAndActiveTrue(userId));
        model.put("completedPlans", gymPlanRepository.findAllByUserIdAndCompletedPlans(userId));
        model.put("allPlans", gymPlanRepository.findAllByUserId(userId));

        return "gym/home";
    }

}