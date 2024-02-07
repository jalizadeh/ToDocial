package com.jalizadeh.todocial.controller.gym;


import com.jalizadeh.todocial.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.repository.GymPlanRepository;
import com.jalizadeh.todocial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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