package com.jalizadeh.todocial.web.controller;


import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GymController {

    @Autowired
    private SettingsGeneralConfig settings;

    @RequestMapping(value = "/gym", method = RequestMethod.GET)
    public String gymHomepage(ModelMap model){
        model.put("settings", settings);
        model.put("PageTitle", "Gym");
        return "gym/home";
    }

}
