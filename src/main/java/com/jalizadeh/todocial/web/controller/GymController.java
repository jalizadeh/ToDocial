package com.jalizadeh.todocial.web.controller;


import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.repository.GymRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GymController {

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private GymRepository gymRepositor;

    @RequestMapping(value = "/gym", method = RequestMethod.GET)
    public String homepage(ModelMap model){
        model.put("settings", settings);
        model.put("PageTitle", "Gym");
        model.put("plans", gymRepositor.findAll());

        return "gym/home";
    }

}
