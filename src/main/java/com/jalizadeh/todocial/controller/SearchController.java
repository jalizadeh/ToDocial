package com.jalizadeh.todocial.controller;


import com.jalizadeh.todocial.repository.TodoRepository;
import com.jalizadeh.todocial.utils.DataUtils;
import com.jalizadeh.todocial.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.model.Todo;
import com.jalizadeh.todocial.model.gym.GymPlan;
import com.jalizadeh.todocial.repository.GymPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private GymPlanRepository gymPlanRepository;


    @GetMapping("/search")
    public String search(ModelMap model, RedirectAttributes redirectAttributes,
                             @RequestParam(name="target", defaultValue="todo") String target,
                             @RequestParam(name="q", defaultValue="") String query) {

        String q = DataUtils.sanitizeQuery(query);

        model.put("settings", settings);
        model.put("PageTitle", "Search in " + target.toUpperCase());
        model.put("target", target.toUpperCase());
        model.put("query", q);

        switch (target){
            case "todo":
                List<Todo> todos = todoRepository.searchAllByLoggedinUser(q);
                model.put("items", todos);
                break;
            case "gym":
                List<GymPlan> gymPlans = gymPlanRepository.searchAll(q);
                model.put("items", gymPlans);
                break;
            default:
                redirectAttributes.addFlashAttribute("exception", "The search criteria is not correct");
                return "redirect:/error";
        }

        return "search/" + target;
    }

}
