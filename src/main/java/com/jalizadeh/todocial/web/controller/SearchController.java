package com.jalizadeh.todocial.web.controller;


import com.jalizadeh.todocial.system.repository.TodoRepository;
import com.jalizadeh.todocial.web.controller.admin.model.SettingsGeneralConfig;
import com.jalizadeh.todocial.web.model.Todo;
import com.jalizadeh.todocial.web.model.gym.GymPlan;
import com.jalizadeh.todocial.web.repository.GymPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private SettingsGeneralConfig settings;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private GymPlanRepository gymPlanRepository;


    @GetMapping("/search")
    public String SearchTodo(ModelMap model, RedirectAttributes redirectAttributes,
                             @RequestParam(name="target", defaultValue="todo") String target,
                             @RequestParam(name="q", defaultValue="") String q) {

        model.put("settings", settings);
        model.put("PageTitle", "Search in " + target.toUpperCase());
        model.put("target", target.toUpperCase());
        model.put("query", q);

        switch (target){
            case "todo":
                List<Todo> todos = todoRepository.searchAllByLoggedinUser(q);
                model.put("todos", todos);
                model.put("result_size", todos.size());
                break;
            case "gym":
                List<GymPlan> gymPlans = gymPlanRepository.searchAll(q);
                model.put("plans", gymPlans);
                model.put("result_size", gymPlans.size());
                break;
            default:
                redirectAttributes.addFlashAttribute("exception", "The search criteria is not correct");
                return "redirect:/error";
        }

        return "search/" + target;
    }

}
